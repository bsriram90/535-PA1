package bloomfilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FalsePositives {
	
	private BloomFilter bloomFilter;
	private Set<String> inputSet;
	
	
	public FalsePositives(BloomFilter bloomFilter) {
		this.bloomFilter = bloomFilter;
		this.inputSet = new TreeSet<String>();
	}
	
	public float getFalsePositiveRate(){
		
		float fpCount = 0;
		int bFilterLength = 0;
		int searchPositives = 0;
		//create input list 
		permutation("","abcdefg");
		System.out.println("Input Set Size - "+inputSet.size());
		//Insert into bloom filter only alternative elements
		Iterator<String> setIterator = inputSet.iterator();
		int i=0;
		while(setIterator.hasNext()){
			String input = setIterator.next();
			if(i%2 == 0){
				bloomFilter.add(input);
				bFilterLength++;
			}
			i++;
		}
		//Run through the list again and check for presense of all elements
		setIterator = inputSet.iterator();
		while(setIterator.hasNext()){
			if(bloomFilter.appears(setIterator.next())){
				searchPositives++;
			}
		}
		//calculate False Positives
		System.out.println("Search Positives - "+searchPositives);
		System.out.println("Filter Length - "+bFilterLength);
		fpCount = searchPositives - bFilterLength;
		fpCount = fpCount/searchPositives;
		return fpCount;
	}

	public int getInputListSize(){
		if(inputSet != null){
			return inputSet.size();
		}
		return -1;
	}

	private void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0) inputSet.add(prefix);
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}
	
	public static void main(String[] args) {
		FalsePositives fPositiveFNV = new FalsePositives(new BloomFilterDet(6000,8));
		System.out.println(fPositiveFNV.getFalsePositiveRate());
		
		FalsePositives fPositiveRND = new FalsePositives(new BloomFilterRan(6000,4));
		System.out.println(fPositiveRND.getFalsePositiveRate());
		
	}

}
