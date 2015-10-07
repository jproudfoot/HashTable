/*
 * Jack Proudfoot
 * Version 0.0.0.0.a
 * September 28, 2015
 * 
 * A data structure used for storing and retrieving values based on their hashcodes. Runs in 
 * constant time.
 */


public class HashTable <K, V> {
	
	private static final double loadFactor = .6;
	private Entry<K, V> [] table;
	private double population = 0.0;
	
	
	@SuppressWarnings("unchecked")
	
	public HashTable () {
		this.table = new Entry [generatePrime(100)];
	}
	
	@SuppressWarnings("unchecked")
	public HashTable (int capacity) {
		this.table = new Entry[capacity];
	}
	
	/**
	 * Stores the Object obj in the hashtable using Object's hashcode method.
	 * Uses quadratic probing to deal with collisions.
	 * 
	 * @param obj An object to store in the hashtable.
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
	 * Searches the hash table for the entry that has the same key as the parameter. Returns
	 * the value that corresponds to that key stored in the hashtable and removes that value
	 * from the table. 
	 * 
	 * @param K The key that corresponds to the value.
	 * @return V The value with key K
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
		
		
		/*Rehashes the objects */
		location += 1;
		while(true) {
			HashTable<K, V>.Entry<K, V> e = table[location];
			
			if (e == null) break;
			
			population--;
			table[location] = null;
			put(e.getKey(), e.getValue());
			
			location *= 2;
		}
		
		return value;
	}
	
	
	/**
	 * Searches the hash table for the entry that has the same key as the parameter. Returns
	 * the value that corresponds to that key stored in the hashtable. 
	 * @param K The key that corresponds to the value.
	 * @return V Value with the given key.
	 * 
	 */
	public V get (K key) {
		int location = key.hashCode() % table.length;
		if (location < 0) location += table.length;
		
		int i = 1;
		while (!(table[location].getKey().equals(key))) {
			location += i;
			i*=2;
			location %= table.length;
		}
		
		V value = table[location].getValue();
		
		return value;
	}
	
	/**
	 * Returns whether or not the key is contained in the hashtable
	 * @return boolean Key contained in table.
	 */
	private boolean containsKey (K key) {
		for (Entry<K, V> e: table) {
			if (e.getKey().equals(key)) return true;
		}
		
		return false;
	}
	

	/**
	 * 
	 * Returns whether or not the value is contained in the hashtable
	 * 
	 * @return boolean Value contained in table.
	 */
	private boolean containsValue (V value) {
		for (Entry<K, V> e: table) {
			if (e.getValue().equals(value)) return true;
		}
		
		return false;
	}
	
	/**
	 * Doubles the table size and rehashes all of the objects contained in the Hashtable
	 * @return void
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void rehash () {
		
		/* Creates a new table to store all of the old data */
		Entry<K, V>[] oldTable = new Entry[table.length];
		for (int x = 0; x < table.length; x++) {
			oldTable[x] = table[x];
		}
		
		/*Doubles the table size */
		table = new Entry[generatePrime(table.length * 2)];
		
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
		 * Returns the key for this entry.
		 * @return K
		 * 
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * Returns the value for this entry.
		 * @return V
		 */
		public V getValue() {
			return this.value;
		}
	}
	
	/**
	 * Converts the hashtable to a string.
	 * 
	 * @return String Returns a string that represents the Hashtable
	 * 
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
	
	/**
	 * A method to generate the next greatest prime number
	 * 
	 * @return int Returns the next greatest prime number
	 * @param int Number to start search
	 */
	private static int generatePrime (int num) {
		
		for(int x = 2; x * 2 < num; x++) {
	        if(num % x == 0) {
				return generatePrime(++num);
			}
		}
		
		return num;
	}
 }
