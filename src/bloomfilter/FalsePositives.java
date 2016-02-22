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
		randomStrings(50000);
		//Insert into bloom filter only alternative elements
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
		//create input list 
		//permutation("","abcdefghi");
		
		setIterator = inputSet.iterator();
		//Run through the list again and check for presense of all elements
		setIterator = notInsertedSet.iterator();
		while(setIterator.hasNext()){
			if(bloomFilter.appears(setIterator.next())){
				searchPositives++;
			}
		}
		//calculate False Positives
		System.out.println("False Positives - "+searchPositives);
		System.out.println("Input Length - "+notInsertedSet.size());
		fpCount = (float)searchPositives/notInsertedSet.size();
		return fpCount;
	}

	private static void randomStrings(int i) {
		SecureRandom random = new SecureRandom();
		while(inputSet.size() < i) {
			BigInteger rand = new BigInteger(130, random);
			if(!generated.contains(rand)){
				generated.add(rand);
				inputSet.add(rand.toString(32));
			}
		}
	}

	public int getInputListSize(){
		if(inputSet != null){
			return inputSet.size();
		}
		return -1;
	}
	
	public static void main(String[] args) {
		System.out.println("Determinitic Bloom filter:");
		FalsePositives fPositiveFNV = new FalsePositives(new BloomFilterDet(50000,10));
		System.out.println("False Positive rate - " + fPositiveFNV.getFalsePositiveRate());
		
		System.out.println("Random Bloom filter:");
		FalsePositives fPositiveRND = new FalsePositives(new BloomFilterRan(50000,10));
		System.out.println("False Positive rate - " + fPositiveRND.getFalsePositiveRate());
		
		
		
	}
}
