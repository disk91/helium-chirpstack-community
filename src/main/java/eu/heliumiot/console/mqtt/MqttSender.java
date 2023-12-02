/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2022.
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 *    and associated documentation files (the "Software"), to deal in the Software without restriction,
 *    including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *    sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies or
 *    substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eu.heliumiot.console.mqtt;

import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.service.PrometeusService;
import fr.ingeniousthings.tools.*;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
public class MqttSender implements MqttCallback {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected static final int MQTT_QOS = 2;

    @Autowired
    private ConsoleConfig mqttConfig;

    @Autowired
    protected PrometeusService prometeusService;

    private MqttConnectOptions connectionOptions;
    private MemoryPersistence persistence;
    private MqttClient mqttClient;
    private String clientId;

    protected boolean requestToStop = false;
    protected volatile int scheduleRunning = 0;
    protected boolean stopped = false;

    public void stopMqttListener() {
        this.requestToStop = true;
        this.stop();
        long start = Now.NowUtcMs();
        while ( this.scheduleRunning > 0 && (Now.NowUtcMs() - start) < 10_000 ) {
            Tools.sleep(100);
        }
        this.stopped = true;
    }

    @PostConstruct
    public MqttClient initMqtt() {

        this.clientId = mqttConfig.getMqttId() + RandomString.getRandomString(4);
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            log.info("MQTT S Url :" + mqttConfig.getMqttServer());
            log.info("MQTT S User :" + mqttConfig.getMqttLogin());
            //log.info("Password :"+mqttConfig.getPassword());
            log.info("MQTT S Id : "+clientId);
            this.connectionOptions.setCleanSession(true);
            this.connectionOptions.setAutomaticReconnect(false); // managed manually
            this.connectionOptions.setConnectionTimeout(5);
            this.connectionOptions.setKeepAliveInterval(60);
            this.connectionOptions.setUserName(mqttConfig.getMqttLogin());
            this.connectionOptions.setPassword(mqttConfig.getMqttPassword().toCharArray());
            this.connect();
            log.info("MQTT S Starting Mqtt listener");
        } catch (MqttException me) {
            log.error("MQTT S ERROR : "+me.getMessage());
        }
        return this.mqttClient;
    }

    private boolean inConnect = false;
    public void connect() throws MqttException {
        inConnect = true;
        try {
            log.debug("MQTT S - Connect");
            if (this.mqttClient != null) this.mqttClient.close();
            this.mqttClient = new MqttClient(mqttConfig.getMqttServer(), this.clientId, persistence);
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } finally {
            inConnect = false;
        }
    }


    public void stop() {
        try {
            this.mqttClient.disconnect();
            this.mqttClient.disconnect();
            this.mqttClient.close();
        } catch (MqttException me) {
            log.error("MQTT S ERROR :"+me.getMessage());
        }
    }


    private boolean pendingReconnection = false;

    @Override
    public void connectionLost(Throwable arg0) {
        log.error("MQTT S - Connection Lost");
        try {
            // immediate retry, then will be async
            log.error("MQTT S - Direct reconnecting");
            this.connect();
            log.error("MQTT S - Direct reconnected");
            this.sendPending();
        } catch (MqttException me) {
            log.warn("MQTT S - direct reconnection failed - "+me.getMessage());
            pendingReconnection = true;
        }
        prometeusService.addMqttConnectionLoss();
    }

    @Scheduled(fixedDelayString = "${helium.mqtt.reconnect.scanPeriod}", initialDelay = 30_000) // default 10s
    protected void autoReconnect() {
        if ( ! pendingReconnection || inConnect ) return;
        try {
            if ( mqttClient.isConnected() ) {
                log.info("MQTT S - reconnected");
            } else {
                log.info("MQTT S - reconnecting");
                this.connect();
                log.info("MQTT S - reconnected");
                this.sendPending();
            }
            pendingReconnection = false;
        } catch (MqttException me) {
            log.warn("MQTT S - reconnection failed - "+me.getMessage());
        }
    }

    protected static class MqttBackupMessage {
        public String topic;
        public String message;
        public int qos;
    }

    ArrayList<MqttBackupMessage> messageBackupQueue = new ArrayList<>();
    public void publishMessage(String topic, String message, int qos) {
        if ( this.mqttClient.isConnected() ) {
            try {
                MqttMessage mqttmessage = new MqttMessage(message.getBytes());
                mqttmessage.setQos(qos);
                //log.info("MQTT : Topic("+topic+") Mess("+mqttmessage+")");
                this.mqttClient.publish(topic, mqttmessage);
            } catch (MqttException me) {
                log.error("MQTT S - MessagePublish Error", me);
            }
        } else {
            // buffer the message for later
            if ( messageBackupQueue.size() < 1000 ) {
                MqttBackupMessage m = new MqttBackupMessage();
                m.topic = topic;
                m.message = message;
                m.qos = qos;
                messageBackupQueue.add(m);
                log.info("MQTT S - Delayed messages for later as broker is disconnected");
            }
        }
    }

    protected void sendPending() {
        // publish all the pending messages
        if (this.mqttClient.isConnected() && messageBackupQueue != null && messageBackupQueue.size() > 0) {
            ArrayList<MqttBackupMessage> toSend = new ArrayList<>(messageBackupQueue);
            messageBackupQueue.clear();
            for ( MqttBackupMessage m : toSend ) {
                publishMessage(m.topic,m.message,m.qos);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        // log.info("MQTT - delivery completed");

    }

    @Override
    public void messageArrived(String topicName, MqttMessage message) throws Exception {

    }

}