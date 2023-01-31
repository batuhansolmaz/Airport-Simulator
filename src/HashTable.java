public class HashTable {
    String[] table;

    public HashTable(){
        table = new String[1000];
    }

    public int hash(String key) {
        int val = 0;
        for (int i = 0; i < key.length(); i++) {
            val = val + (int)Math.pow(31, i)*key.charAt(i);
        }
        return val;
    }

    public void insert(String key) {
        int hash = hash(key);
        int index = hash % table.length;

        while (table[index] != null) {
            index = (index + 1) % table.length;
        }
        table[index] = key;
    }

    public int get(String key) {
        int hash = hash(key);
        int index = hash % table.length;
        while (table[index] != null) {
            if (table[index] == key) {
                return index;
            }
            index = (index + 1) % table.length;
        }
        return -1;
    }

    public String Atc_code(String key){
        if (get(key) != -1){
            String str = String.format("%03d", get(key));

            return key+str;
        }
        return "-1";
    }

    public void printHashTable() {
        System.out.println("\nHash Table: ");
        int i;
        for (i = 0; i < 1000; i++) {
            if (table[i] != null){
                System.out.println(table[i] + " " + table[i]);
                System.out.println(i);
            }
        }
    }

}


