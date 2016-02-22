package bloomfilter;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class FalsePositives {
	
	private BloomFilter bloomFilter;
	private static Set<String> inputSet = new TreeSet<String>();
	private static Set<String> insertedSet = new TreeSet<String>();
	private static Set<String> notInsertedSet = new TreeSet<String>();
	private static Set<BigInteger> generated  = new HashSet<BigInteger>();
	
	static{
		generateInputs();
	}
	
	public FalsePositives(BloomFilter bloomFilter) {
		this.bloomFilter = bloomFilter;
	}

	private static void generateInputs() {
		SecureRandom random = new SecureRandom();
		while(inputSet.size() < 50000) {
			BigInteger rand = new BigInteger(130, random);
			if(!generated.contains(rand)){
				generated.add(rand);
				inputSet.add(rand.toString(32));
			}
		}
		Iterator<String> setIterator = inputSet.iterator();
		int i=0;
		while(setIterator.hasNext()){
			String input = setIterator.next();
			if(i%2 == 0){
				insertedSet.add(input);
			} else {
				notInsertedSet.add(input);
			}
			i++;
		}
	}
	
	public float getFalsePositiveRate(){
		Iterator<String> setIterator = insertedSet.iterator();
		while(setIterator.hasNext()){
			bloomFilter.add(setIterator.next());
		}
		float fpCount = 0;
		int searchPositives = 0;
		
		setIterator = notInsertedSet.iterator();
		while(setIterator.hasNext()){
			String s = setIterator.next();
			if(bloomFilter.appears(s)){
				searchPositives++;
			}
		}
		fpCount = (float)searchPositives/notInsertedSet.size();
		return fpCount;
	}
	
	public static void main(String[] args) {
		Integer setSize = 10000;
		Integer[] bitSizes = new Integer[]{4,8,10};
		for(Integer elementsPerBit : bitSizes) {
			System.out.println("Determinitic Bloom filter with "+ elementsPerBit + " bits per element and " + setSize + " set size:");
			FalsePositives fPositiveFNV = new FalsePositives(new BloomFilterDet(setSize,elementsPerBit));
			System.out.println("False Positive rate - " + fPositiveFNV.getFalsePositiveRate());
			
			System.out.println("Random Bloom filter with "+ elementsPerBit + " bits per element and " + setSize + " set size:");
			FalsePositives fPositiveRND = new FalsePositives(new BloomFilterRan(setSize,elementsPerBit));
			System.out.println("False Positive rate - " + fPositiveRND.getFalsePositiveRate());
		}
		
		
	}
}
