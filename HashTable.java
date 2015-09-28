
public class HashTable {
	
	private static final double loadFactor = .6;
	private Object [] table;
	private double population = 0.0;
	
	
	public HashTable () {
		this.table = new Object[100];
	}
	
	public HashTable (int capacity) {
		this.table = new Object[capacity];
	}
	
	public void put (Object obj) {
		int hashcode = obj.hashCode();
		int location = hashcode % table.length;
		if (location < 0) location += table.length;
		
		int i = 1;
		while (table[location] != null) {
			location += i;
			i*=2;
			location %= table.length;
		}
		
		table[location] = obj;
		population++;
		
		if (population/table.length > loadFactor) rehash();
	}
	
	private void rehash () {
		
		/* Creates a new table to store all of the old data */
		Object[] oldTable = new Object[table.length];
		for (int x = 0; x < table.length; x++) {
			oldTable[x] = table[x];
		}
		
		/*Doubles the table size */
		table = new Object[table.length * 2];
		
		/*Rehashes the objects */
		population = 0;
		for (Object obj : oldTable) {
			if (obj != null) put(obj);
		}
	}
	
	public String toString () {
		String tablestring = "[";
		
		for (int x = 0; x < table.length; x++) {
			if (table[x] != null) tablestring += ", " + table[x].hashCode();
			else tablestring += ", null";
		}
		
		tablestring += "]";
		
		return tablestring;
	}
	
	public static void main (String [] args) {
		HashTable table = new HashTable(10);
		
		Object a = new Object();
		
		table.put("jack");
		System.out.println(table.toString());
		
		table.put("Hello World");
		System.out.println(table.toString());
		
		System.out.println(table.toString());
	}
 }
