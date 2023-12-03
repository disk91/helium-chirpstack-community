/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
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
package eu.heliumiot.console.jpa.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "helium_messages",
        indexes = {
                @Index(name="uniqueHeliumMessageIndex", columnList = "index", unique = true)
        }
)
public class HeliumMessages {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // message Index (user store this index to record the seen messages)
    // basically Now
    @Column(name = "index")
    private long index;

    // Type of message, can be a modal (in the middle of the screen) or a toaster
    @Column(name = "type")
    private int type;

    // category of message - info / warning / error
    @Column(name = "category")
    private int category;

    // content of the message to be displayed
    @Column(name = "content", columnDefinition = "text")
    private String content;

    // Final date to display this message
    @Column(name = "until")
    private long until;

    // When true the message is printed only one time otherwise, until the end-date
    @Column(name = "onlyone")
    private boolean onlyone;

    // True when a user message vs a front page message
    @Column(name = "user_mess")
    private boolean userMess;

    // ---


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUntil() {
        return until;
    }

    public void setUntil(long until) {
        this.until = until;
    }

    public boolean isOnlyone() {
        return onlyone;
    }

    public void setOnlyone(boolean onlyone) {
        this.onlyone = onlyone;
    }

    public boolean isUserMess() {
        return userMess;
    }

    public void setUserMess(boolean userMess) {
        this.userMess = userMess;
    }
}
