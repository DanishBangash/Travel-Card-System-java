/**
 *
 * @author Danish Bangash
 */
package Controller;

import java.util.Random;

public class Zone {

    public static int num = 0;
    Random random = new Random();
    Zone prev = null;
    Zone next = null;
    public int index;
    String name;
    public int distance;
    

    public Zone() {
        int index= num;
        this.index=index ;
        this.name = "zone" + num;
        num += 1;
        this.distance = random.nextInt(20);

        Zone prev = this.prev;
        Zone next = this.next;

    }

    public Zone getNext(Zone z) {
        Zone next = z.next;
        return next;
    }

    public Zone getPrev(Zone z) {
        Zone prev = z.prev;
        return prev;
    }
}
