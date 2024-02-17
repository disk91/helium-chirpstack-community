<template>
    <div id="map" :style="{ height: '400px' }">
        
    </div>
</template>

<script lang="ts">
    import Vue from 'vue';
    import L from 'leaflet';
    import { DataContext, FrameEntry } from 'vue/types/context'; 

    interface MarkerContext {
        marker: any,
        id: string,
        name: string,
        icon: any,
    }

    interface context {
        map : any,
        markers : MarkerContext[],
        isDestroyed : boolean,
        greenIcon: any,
        greyIcon: any,
        goldIcon: any,
    }


    export default Vue.extend({
        name: 'LeafletMap',
        data() : context {
            return {
                map : undefined,
                markers : [],
                isDestroyed : true,
                greenIcon : null,
                greyIcon: null,
                goldIcon: null
            }
        },
        methods: {
            drawMap(msg:DataContext) {

                if ( ! this.map ) {
                   //this.map = L.map('map').setView([(minLat+(maxLat-minLat)/2), (minLon+(maxLon-minLon)/2)], 11);
                   this.map = L.map('map').fitBounds([ [msg.latN, msg.lonW], [msg.latS, msg.lonE] ]);
                } else {
                   this.map.flyTo([(msg.latS+(msg.latN-msg.latS)/2), (msg.lonW+(msg.lonE-msg.lonW)/2)], 11);  
                }
                this.isDestroyed = false;
                // L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
                L.tileLayer('https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png'+'?api_key='+this.$config.mapApiKey, {
                    maxZoom: 19,
                    attribution: '&copy; <a href="https://www.stadiamaps.com/" target="_blank">Stadia Maps</a> &copy; <a href="https://openmaptiles.org/" target="_blank">OpenMapTiles</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
                   // attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                }).addTo(this.map!);

                var uniqW : boolean[] = [];
                // print the recently used hotspots if no others
                msg.device.hotspotAround.forEach( h => {
                    let found : boolean = false;
                    for (let i = 0 ; i < msg.hotspots.length ; i++ ) {
                        if ( h.gatewayId === msg.hotspots[i].hotspotId ) {
                            found=true;
                            break;
                        }
                    }
                    // unknown device display (like some dataonly not rewarded)
                    if (!found) {
                        var marker = L.marker([h.lat, h.lng], {icon: this.greenIcon}).addTo(this.map!);
                        marker.bindTooltip(h.gatewayId);
                        this.markers.push({marker:marker,id:h.gatewayId,icon:this.greenIcon,name:h.gatewayId});
                        marker.on('click', () => {
                            this.$root.$emit("message-context-uhotspot-update", h.gatewayId);
                        });
                    }
                });

                // print others
                msg.hotspots.forEach( h => {
                    let found : boolean = false;
                    for (let i = 0 ; i < msg.device.hotspotAround.length ; i++ ) {
                        if ( h.hotspotId === msg.device.hotspotAround[i].gatewayId ) {
                            found = true;
                            var marker = L.marker([msg.device.hotspotAround[i].lat, msg.device.hotspotAround[i].lng], {icon: this.greenIcon}).addTo(this.map!);
                            marker.bindTooltip(h.animalName);
                            this.markers.push({marker:marker,id:h.hotspotId,icon:this.greenIcon,name:h.animalName});
                            marker.on('click', () => {
                                this.$root.$emit("message-context-uhotspot-update", h.hotspotId);
                            });
                            break;
                        }
                    }
                    if ( !found ) {
                        var marker = L.marker([h.position.lat, h.position.lng], {icon: this.greyIcon}).addTo(this.map!);
                        marker.bindTooltip(h.animalName);
                        this.markers.push({marker:marker,id:h.hotspotId,icon:this.greyIcon,name:h.animalName});
                        marker.on('click', () => {
                            this.$root.$emit("message-context-ahotspot-update", h.hotspotId);
                        });
                    }
                });
            }
        },
        mounted() {
            // cf - https://github.com/pointhi/leaflet-color-markers
            this.greenIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [12, 20],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [10, 10]
            });

            this.greyIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-grey.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [12, 20],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [10, 10]
            });

            this.goldIcon = new L.Icon({
                iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-gold.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [16, 26],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [10, 10]
            });


            if (this.map && !this.isDestroyed ) {
               this.map.remove();
               this.map = undefined;
               this.isDestroyed = true;
            }
            // clean if exists
            this.$root.$off("message-context-map-update");
            this.$root.$on("message-context-map-update", (msg:DataContext) => {
                this.drawMap(msg);
            });
            this.$root.$off("message-context-frame-ightlight");
            this.$root.$on("message-context-frame-ightlight", (msg:FrameEntry) => {
                this.markers.forEach( (m) => {
                    m.marker.setIcon(m.icon);
                    m.marker.bindTooltip(m.name);
                    msg.hotspots.forEach( (h) => {
                        if ( h.hotspotId === m.id ) {
                            m.marker.setIcon(this.goldIcon);
                            m.marker.bindTooltip(m.name+"<br/> ( "+h.rssi.toFixed(0)+"dBm / "+h.snr.toFixed(2)+"dBm )");
                        }
                    });
                });
            });
            
        },
        beforeDestroy() {
            if (this.map && !this.isDestroyed ) {
               this.map.remove();
               this.map = undefined;
               this.isDestroyed = true;
            }
        },
    })
</script>