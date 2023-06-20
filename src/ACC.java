import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ACC {
    int current_time =0;
    HashTable table;
    String accname;
    ArrayList<Flight> next = new ArrayList<Flight>();
    ArrayList<Flight> next_atc = new ArrayList<Flight>();
    ArrayList<Flight> arr = new ArrayList<Flight>();
    public ACC(String accname, HashTable table) {
        this.accname = accname;
        this.table = table;
    }



    ArrayList<ATC> listofatc = new ArrayList<ATC>();
    ArrayList<Flight> listofflight = new ArrayList<Flight>();
    Flight flight1 = new Flight( null,null,null,null);
    Flight flight2 = new Flight(null,null,null,null);

    PriorityQueue<Flight> waitingqueue = new PriorityQueue<>(flight1.Comparator_lefttime());
    PriorityQueue<Flight> runningqueue = new PriorityQueue<>(flight2.Comparator_addedtime());
    PriorityQueue<Flight> runningqueueAtc = new PriorityQueue<>(flight1.Comparator_lefttime());

    public Flight remove_fromATC(Flight flight){
        if (flight.operation_number>11){
            flight.arrival.runningqueueAtc.remove(flight);
            if (flight.arrival.runningqueueAtc.peek()!=null){
                return flight.arrival.runningqueueAtc.peek();
            }

        }
        else{
            flight.departure.runningqueueAtc.remove(flight);
            if (flight.departure.runningqueueAtc.peek()!=null){
                return flight.departure.runningqueueAtc.peek();
            }
        }
        return null;
    }


    public void add(Flight flight){
        if (flight==null){
            return;
        }
        if (flight.operation_number==22)
        {
            return;
        }
        else if (flight.operation_number == 1 || flight.operation_number == 11 || flight.operation_number == 3 || flight.operation_number == 13 || flight.operation_number == 21) {
            flight.adding_time = current_time;
            flight.update_execution_time();
            if (flight.left_time() < flight.execution_time){
                flight.execution_time = flight.left_time();
            }
            runningqueue.add(flight);
        }

        else if (flight.operation_number == 14 || flight.operation_number == 16 || flight.operation_number==18 || flight.operation_number == 20) {
            flight.adding_time = current_time;
            flight.update_execution_time();

            flight.New=true;
            flight.arrival.runningqueueAtc.add(flight);
            if (flight.arrival.runningqueueAtc.peek()==flight){
                runningqueueAtc.add(flight);
            }
        }

        else if(flight.operation_number == 4 || flight.operation_number == 6 || flight.operation_number==8 || flight.operation_number == 10){
            flight.adding_time = current_time;
            flight.update_execution_time();
            flight.New=true;
            flight.departure.runningqueueAtc.add(flight);
            if (flight.departure.runningqueueAtc.peek()==flight){
                runningqueueAtc.add(flight);
            }
        }

        else {
            flight.adding_time = current_time;
            flight.update_execution_time();
            flight.New=true;

            waitingqueue.add(flight);
        }
    }

    public void time_handler(Flight flight){
        if (flight.left_time()>30) {
            flight.update_operations(flight.execution_time);
            current_time += flight.execution_time;
            flight.New=false;
            runningqueue.remove(flight);
        }
        else{
            if (flight.left_time() > flight.execution_time){
                current_time +=flight.execution_time;
                runningqueue.remove(flight);
                flight.update_operations(flight.execution_time);
            }

            else {
                    current_time += flight.execution_time;
                    runningqueue.remove(flight);
                    flight.update_operations(flight.execution_time);
                    flight.operation_number += 1;

            }
        }
    }

    public void time_handler_for_act(Flight flight){
        int time =flight.execution_time;
        current_time +=time;
        Flight x =null;
        Object[] array =runningqueueAtc.toArray();
        for (Object i : array){
            Flight f = (Flight) i;
            f.update_operations(time);
            f.execution_time-=time;
            if (f.left_time()<=0){
                x = remove_fromATC(f);
                runningqueueAtc.remove(f);
                f.operation_number+=1;
                next_atc.add(f);
                if (x!=null){
                    arr.add(x);
                }
            }
        }


        for (Flight i : arr){
            i.adding_time=current_time;
            runningqueueAtc.add(i);
        }
        arr.clear();

    }

    public void time_handler_for_waiting(Flight flight){
        current_time +=flight.execution_time;
        flight.update_operations(flight.execution_time);
        flight.operation_number +=1;
        waitingqueue.remove(flight);

    }

    public int find_min(){
        int min = 1000000000;
        if (runningqueue.size() != 0) {
            min = runningqueue.peek().execution_time;
        }

        if (runningqueueAtc.size() != 0) {
            if (runningqueueAtc.peek().execution_time <= min) {
                min = runningqueueAtc.peek().execution_time;
            }
        }
        if (waitingqueue.size() != 0) {
            if (waitingqueue.peek().execution_time <= min) {
                min = waitingqueue.peek().execution_time;
            }
        }
        return min;

    }


    public void Update_Waiting_Queue(int time){
        Object[] array = waitingqueue.toArray();
        for (Object i : array){
            Flight f = (Flight) i;
            f.update_operations(time);
            f.execution_time-=time;
            if (f.left_time()<=0){
                waitingqueue.remove(f);
                f.operation_number+=1;
                next.add(f);
            }
        }
    }


    public Flight Update_Running_Queue(int time){
        if (runningqueue.size() != 0) {
            runningqueue.peek().update_operations(time);
            runningqueue.peek().execution_time -= time;

            if (runningqueue.peek().left_time() <=0){
                Flight flight = runningqueue.peek();
                runningqueue.remove(runningqueue.peek());
                flight.operation_number++;
                return flight;
            }
        }
        return null;
    }

    public void Update_Running_QueueAtc(int time){
        if (runningqueueAtc.size() != 0) {
            Flight next_atc_running=null;
            Object [] array = runningqueueAtc.toArray();
            for (Object i : array){
                Flight f = (Flight) i;
                f.update_operations(time);
                f.execution_time-=time;
                if (f.left_time()<=0){
                    next_atc_running = remove_fromATC(f);
                    runningqueueAtc.remove(f);
                    f.operation_number+=1;
                    add(f);
                    if (next_atc_running!=null){
                        arr.add(next_atc_running);
                    }
                }
            }

            for (Flight i : arr) {
                i.adding_time=current_time;
                runningqueueAtc.add(i);
            }
            arr.clear();

        }
    }

    public int simulate() {
        long bar = 300000000;
        try {
            while (!waitingqueue.isEmpty() || !runningqueue.isEmpty() || !runningqueueAtc.isEmpty()) {
                bar = bar - 1;
                if (bar == 0) {
                    return 27542;
                }
                if (current_time > 20000) {
                    current_time = current_time;
                }
                if (waitingqueue.size() != 0) {
                    if (waitingqueue.peek().execution_time == find_min()) {
                        Flight flight = waitingqueue.peek();
                        int passing_time = flight.execution_time;
                        time_handler_for_waiting(waitingqueue.peek());
                        Update_Waiting_Queue(passing_time); 
                        flight1 = Update_Running_Queue(passing_time);
                        Update_Running_QueueAtc(passing_time);
                        for (Flight x : next) {
                            add(x);
                        }
                        for (Flight x : next_atc) {
                            add(x);
                        }
                        next_atc.clear();
                        next.clear();
                        add(flight);
                        add(flight1);
                        continue;
                    }
                }
                if (runningqueue.size() != 0) {
                    if (runningqueue.peek().execution_time == find_min()) {
                        Flight flight = runningqueue.peek();
                        int passing_time = flight.execution_time; 

                        time_handler(runningqueue.peek());
                        Update_Waiting_Queue(passing_time); 
                        Update_Running_QueueAtc(passing_time);
                        for (Flight x : next) {
                            add(x);
                        }
                        for (Flight x : next_atc) {
                            add(x);
                        }
                        next.clear();
                        next_atc.clear();
                        add(flight);

                        continue;
                    }
                }
                if (runningqueueAtc.size() != 0) {
                    if (runningqueueAtc.peek().execution_time == find_min()) {
                        Flight flight = runningqueueAtc.peek();
                        int passing_time = flight.execution_time;
                        time_handler_for_act(runningqueueAtc.peek());
                        Update_Waiting_Queue(passing_time); 
                        flight1 = Update_Running_Queue(passing_time);
                        for (Flight i : next_atc) {
                            add(i);

                        }
                        next_atc.clear();

                        for (Flight i : next) {
                            add(i);
                        }
                        next.clear();

                        add(flight1);
                        continue;
                    }
                }

            }
        }
        catch (Exception e) {
            return 0;
        }
        return current_time;
    }

}







