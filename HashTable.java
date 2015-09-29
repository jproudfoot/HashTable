/*
 * Jack Proudfoot
 * Version 0.0.0.0.a
 * September 28, 2015
 * 
 * A data structure used for storing values based on their hashcodes.
 */


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
	
	/**
	 * @param obj An object to store in the hashtable.
	 * 
	 * Stores the Object obj in the hashtable using Object's hashcode method.
	 * Uses quadratic probing to deal with collisions.
	 */
	public void put (Object obj) {
		int hashcode = obj.hashCode();
		int location = hashcode % table.length;
		if (location < 0) location += table.length;
		
		//Search for an empty space in the hashtable
		int i = 1;
		while (table[location] != null) {
			location += i;
			i*=2;
			location %= table.length;
		}
		
		table[location] = obj;
		population++;
		
		//If the population is greater than the load factor it rehashes.
		if (population/table.length > loadFactor) rehash();
	}
	
	/**
	 * @return void
	 * 
	 * Doubles the table size and rehashes all of the objects contained in the Hashtable
	 */
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
	
	/**
	 * @return String Returns a string that represents the Hashtable
	 */
	public String toString () {
		String tablestring = "[";
		
		for (int x = 0; x < table.length; x++) {
			if (table[x] != null) tablestring += ", " + table[x].hashCode();
			else tablestring += ", null";
		}
		
		tablestring += "]";
		
		return tablestring;
	}
	
 }
