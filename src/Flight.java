import java.util.ArrayList;
import java.util.Comparator;

public class Flight {
    int adding_time ; //queueye eklenme zamanı
    ATC arrival;
    ATC departure;
    String flightname;
    ArrayList<Integer> operations;

    int execution_time = execution_time();

    int operation_number =0;
    boolean New = true;

    public Flight( String flightname, ATC departure, ATC arrival , ArrayList<Integer> operations2) {
        this.flightname = flightname;
        this.departure = departure;
        this.arrival = arrival;
        this.operations = operations2;
        adding_time = 0;
    }
    public void update_execution_time(){
        this.execution_time=execution_time();
    }

    public int execution_time(){

        if (operation_number == 0) {
            return left_time();
        }
        else if (operation_number == 1 || operation_number == 11 || operation_number == 3 || operation_number == 13 || operation_number == 21) {

            return 30;
        }

        else if (operation_number == 4 || operation_number == 6 || operation_number==8 || operation_number == 10|| operation_number == 12 || operation_number == 16 || operation_number == 18 || operation_number == 20 ) {

            return left_time();
        }

        else {

            return left_time();
        }
    }

    public int left_time(){
        if (operations==null) {
            return 0;
        }
        return operations.get(operation_number);
    }
    public void update_operations(int time){
        operations.set(operation_number, operations.get(operation_number)-time);

    }

    public Comparator<Flight> Comparator_lefttime(){
        return new Comparator_lefttime();
    }
    public Comparator<Flight> Comparator_addedtime (){
        return new Comparator_addedtime();
    }

    public class Comparator_addedtime implements Comparator<Flight> {
        @Override
        public int compare(Flight o1, Flight o2) {
            if (o1.adding_time > o2.adding_time) {
                return 1;
            } else if (o1.adding_time < o2.adding_time) {
                return -1;
            } else if (o1.adding_time == o2.adding_time) {
                //TODO: compare addmission description
                if (o1.New & o2.New) {
                    return o1.flightname.compareTo(o2.flightname);
                }
                else if (o1.New) {
                    return -1;
                }
                else if (o2.New) {
                    return 1;
                }
                else {
                    return 1;
                }

            }
            return 0;
        }
    }

    public class Comparator_lefttime implements Comparator<Flight> {
        @Override
        public int compare(Flight o1, Flight o2) {
            if (o1.left_time() > o2.left_time()) {
                return 1;
            } else if (o1.left_time() < o2.left_time()) {
                return -1;
            } else if (o1.left_time() == o2.left_time()) {
                return -0;

            }
            return 0;
        }
    }




}
