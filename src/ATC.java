import java.util.PriorityQueue;

public class ATC {
    String atcname;
    String atccode;
    Flight flight1 = new Flight( null,null,null,null);
    PriorityQueue<Flight> runningqueueAtc = new PriorityQueue<>(flight1.Comparator_addedtime());
    public ATC(String atccode , String atcname) {
        this.atccode = atccode;
        this.atcname = atcname;
    }

}