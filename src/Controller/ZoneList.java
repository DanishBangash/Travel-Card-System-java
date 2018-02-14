/**
 *
 * @author Danish Bangash
 */
package Controller;
import java.util.LinkedList;

public class ZoneList {
    public LinkedList list;
    Zone head ;
    Zone tail;
    Zone Current;
    Zone Prev = null;
    Zone Next = null;
    public String[] zoneList = new String[10];

    public ZoneList() {
        LinkedList zList=new LinkedList();
        this.list=zList;
         for (int i = 0; i < 10; i++) {
            
            Zone z = new Zone(); 
            zoneList[i] = z.name;
         zList.addLast(z);
         
         }
     /*   Zone head = null;
        this.head=head;
        Zone tail = null;
        this.tail=tail;
        Zone Current=null;
        this.Current=Current;
        for (int i = 0; i < 10; i++) {
            Zone z = new Zone();
            Current = z;
            
            zoneList[i] = Current.name;
            if (i != 0&&i!=1 && i!=9) {
               Current.prev=Prev;
               Prev.next=Current;
               Prev=Current;
            }
            else if (i == 0) {
                head = Current;
                Prev=head;
                
            }
            else if(i==1){
            Current.prev =head;  
            head.next=Current;
            Prev = Current;
            }
            else if (i == 9) {
                tail = Current;
                tail.prev=Prev;
            }       
            
        }
        */
    }
        
    public Zone getNext(Zone zone){
    return zone.next;
    }
     public Zone getPrev(Zone zone){
    return zone.prev;
    }

    public Zone getZone(int index) {
        
        Zone cur = null;
        int i=0;
        while (index!=i) {
           cur=(Zone) list.get(list.indexOf(i));
           i++;
        }
        return cur;

    }

    public int price(ZoneList zone, Zone sZone, Zone eZone) {
        int total = 0;
        Zone current=(Zone)list.getFirst();
        int i=0;
        while(!current.name.equals(eZone.name)){
        current=(Zone) list.get(list.indexOf(sZone)+i);
         total += current.distance;
        i++;
        }
        if (current.next == null) {
        while(!current.name.equals(eZone.name)){
        current=(Zone) list.get(list.indexOf(sZone)-i);
         total += current.distance;
        i--;
        }
        
        }
        
        return total;
    }
    
}
 /* current = zone.getZone(zone, sZone.name);
        while (current != eZone && current.next != null) {
            total += current.distance;
            current = current.next;

        }
        if (current.next == null) {
            current = sZone;
            total=0;
            while (current != eZone && current.next != null) {
                total += current.distance;
                current = current.prev;
            }
        }

* */