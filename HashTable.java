/*
 * Jack Proudfoot
 * Version 0.0.0.0.a
 * September 28, 2015
 * 
 * A data structure used for storing values based on their hashcodes.
 */


public class HashTable <K, V> {
	
	private static final double loadFactor = .6;
	private Entry<K, V> [] table;
	private double population = 0.0;
	
	
	@SuppressWarnings("unchecked")
	public HashTable () {
		this.table = new Entry [100];
	}
	
	@SuppressWarnings("unchecked")
	public HashTable (int capacity) {
		this.table = new Entry[capacity];
	}
	
	/**
	 * @param obj An object to store in the hashtable.
	 * 
	 * Stores the Object obj in the hashtable using Object's hashcode method.
	 * Uses quadratic probing to deal with collisions.
	 */
	@SuppressWarnings("unchecked")
	public void put (K key, V value) {
		int hashcode = key.hashCode();
		int location = hashcode % table.length;
		if (location < 0) location += table.length;
		
		//Search for an empty space in the hashtable
		int i = 1;
		while (table[location] != null) {
			location += i;
			i*=2;
			location %= table.length;
		}
		
		table[location] = new Entry<K, V> (key, value);
		population++;
		
		//If the population is greater than the load factor it rehashes.
		if (population/table.length > loadFactor) rehash();
	}
	
	/**
	 * @param K The key that corresponds to the value.
	 * @return V
	 * 
	 * Searches the hash table for the entry that has the same key as the parameter. Returns
	 * the value that corresponds to that key stored in the hashtable. 
	 */
	public V remove (K key) {
		int location = key.hashCode() % table.length;
		if (location < 0) location += table.length;
		
		int i = 1;
		while (!(table[location].getKey().equals(key))) {
			location += i;
			i*=2;
			location %= table.length;
		}
		
		V value = table[location].getValue();
		table[location] = null;
		population--;
		return value;
	}
	
	/**
	 * @return void
	 * 
	 * Doubles the table size and rehashes all of the objects contained in the Hashtable
	 */
	@SuppressWarnings("unchecked")
	private void rehash () {
		
		/* Creates a new table to store all of the old data */
		Entry<K, V>[] oldTable = new Entry[table.length];
		for (int x = 0; x < table.length; x++) {
			oldTable[x] = table[x];
		}
		
		/*Doubles the table size */
		table = new Entry[table.length * 2];
		
		/*Rehashes the objects */
		population = 0;
		for (Entry<K, V> ent : oldTable) {
			if (ent != null) put(ent.getKey(), ent.getValue());
		}
	}
	
	/**
	 * Entry class to be used for storing data in our hashmap
	 */
	private class Entry <K, V> {
		private K key;
		private V value;
		
		public Entry (K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * @return K
		 * 
		 * Returns the key for this entry.
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * @return V
		 * 
		 * Returns the value for this entry.
		 */
		public V getValue() {
			return this.value;
		}
	}
	
	/**
	 * @return String Returns a string that represents the Hashtable
	 */
	public String toString () {
		String tablestring = "[";
		
		for (int x = 0; x < table.length; x++) {
			if (table[x] != null) tablestring += ", " + table[x].getValue();
			else tablestring += ", null";
		}
		
		tablestring += "]";
		
		return tablestring;
	}
	
 }
