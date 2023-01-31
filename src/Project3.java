import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Hashtable;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Map.Entry;

public class Project3 {

    public static void main(String[] args) {
        Hashtable<String, ACC> Acctable = new Hashtable<String, ACC>();

        try {
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);
            String first = myReader.nextLine();
            String[] depo = first.split(" ");

            int line1= Integer.parseInt(depo[0]);
            int line2= Integer.parseInt(depo[1]);

            for(int i = 0; i <line1 ; i++){
                String[] depo2=myReader.nextLine().split(" ");
                HashTable table = new HashTable();
                ACC ACC = new ACC(depo2[0] ,table);
                Acctable.put(depo2[0], ACC);


                for (int j = 1; j < depo2.length; j++) {
                    table.insert(depo2[j]);
                    ATC ATC = new ATC(depo2[j],table.Atc_code(depo2[j]));
                    ACC.listofatc.add(ATC);

                }

            }

            for (int i = 0; i < line2; i++) {
                ArrayList<Integer> operations = new ArrayList<Integer>();
                String[] depo3=myReader.nextLine().split(" ");
                int addmission = Integer.parseInt(depo3[0]);
                String flightname = depo3[1];
                ATC departure = null;
                ATC arrival = null;
                ACC acc = Acctable.get(depo3[2]);

                for(ATC atc : acc.listofatc){

                    if(atc.atccode.equals(depo3[3])){
                        departure = atc;
                    }
                    if(atc.atccode.equals(depo3[4])){
                        arrival = atc;
                    }
                }
                operations.add(addmission);
                for (int j=5; j<26;j++) {
                    int operation_i =Integer.parseInt(depo3[j]);
                    operations.add(operation_i);
                }

                Flight flight = new Flight( flightname, departure, arrival, operations);
                acc.listofflight.add(flight);
                acc.add(flight);

            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));

            for (Entry<String, ACC> entry : Acctable.entrySet()) {
                ACC acc = entry.getValue();
                String str = acc.accname;

                str = str + " "  + acc.simulate(); // burası acc den gelicek
                for (int i=0 ; i<acc.table.table.length ; i++) {
                    if (acc.table.table[i] != null) {

                        String code = acc.table.Atc_code(acc.table.table[i]);
                        str += " " + code;
                    }
                }
                writer.write(str);
                writer.newLine();

            }
            myReader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



    }
}
