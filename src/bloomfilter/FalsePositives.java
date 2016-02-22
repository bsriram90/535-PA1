package bloomfilter;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class FalsePositives {
	
	private BloomFilter bloomFilter;
	private Set<String> inputSet;
	private Set<String> insertedSet;
	private Set<String> notInsertedSet;
	
	
	public FalsePositives(BloomFilter bloomFilter) {
		this.bloomFilter = bloomFilter;
		this.inputSet = new TreeSet<String>();
		this.insertedSet = new TreeSet<String>();
		this.notInsertedSet = new TreeSet<String>();
	}
	
	public float getFalsePositiveRate(){
		
		float fpCount = 0;
		int searchPositives = 0;
		//create input list 
		//permutation("","abcdefghi");
		randomStrings(50000);
		//Insert into bloom filter only alternative elements
		Iterator<String> setIterator = inputSet.iterator();
		int i=0;
		while(setIterator.hasNext()){
			String input = setIterator.next();
			if(i%2 == 0){
				insertedSet.add(input);
				bloomFilter.add(input);
			} else {
				notInsertedSet.add(input);
			}
			i++;
		}
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

	private void randomStrings(int i) {
		SecureRandom random = new SecureRandom();
		while(inputSet.size() < i) {
			inputSet.add(new BigInteger(130, random).toString(32));
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
		FalsePositives fPositiveFNV = new FalsePositives(new BloomFilterDet(50000,1));
		System.out.println("False Positive rate - " + fPositiveFNV.getFalsePositiveRate());
		
		System.out.println("Random Bloom filter:");
		FalsePositives fPositiveRND = new FalsePositives(new BloomFilterRan(50000,1));
		System.out.println("False Positive rate - " + fPositiveRND.getFalsePositiveRate());
		
		
		
	}

}
