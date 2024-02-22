/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package eu.heliumiot.console.etl.api.sub;

import fr.ingeniousthings.tools.ClonnableObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Hotspot Ownership", description = "Owner of the hotspot")
public class Owner implements ClonnableObject<Owner> {

    private String hntOwner = "";

    private String solOwner = "";

    private long timeMs = 0;

    // --------

    public Owner clone() {
        Owner c = new Owner();
        c.setHntOwner(hntOwner);
        c.setSolOwner(solOwner);
        c.setTimeMs(timeMs);
        return c;
    }


    // ---------


    public String getHntOwner() {
        return hntOwner;
    }

    public void setHntOwner(String hntOwner) {
        this.hntOwner = hntOwner;
    }

    public String getSolOwner() {
        return solOwner;
    }

    public void setSolOwner(String solOwner) {
        this.solOwner = solOwner;
    }

    public long getTimeMs() {
        return timeMs;
    }

    public void setTimeMs(long timeMs) {
        this.timeMs = timeMs;
    }
}
