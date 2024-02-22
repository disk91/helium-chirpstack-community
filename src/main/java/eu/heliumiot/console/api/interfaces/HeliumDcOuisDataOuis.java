/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
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

package eu.heliumiot.console.api.interfaces;

import java.util.ArrayList;

public class HeliumDcOuisDataOuis {

    /*
        "oui": 1,
        "owner": "13tyMLKRFYURNBQqLSqNJg9k41maP1A7Bh8QYxR13oWv7EnFooc",
        "payer": "112qB3YaH5bZkCnKA5uRH7tBtGNv2Y5B4smv1jsmvGUzgKT71QpE",
        "delegateKeys": [
            "112qB3YaH5bZkCnKA5uRH7tBtGNv2Y5B4smv1jsmvGUzgKT71QpE"
        ]
     */

    protected int oui;
    protected String owner;
    protected String payer;
    protected ArrayList<String> delegateKeys;

    // ---


    public int getOui() {
        return oui;
    }

    public void setOui(int oui) {
        this.oui = oui;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public ArrayList<String> getDelegateKeys() {
        return delegateKeys;
    }

    public void setDelegateKeys(ArrayList<String> delegateKeys) {
        this.delegateKeys = delegateKeys;
    }
}
