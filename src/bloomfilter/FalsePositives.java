package bloomfilter;

import java.util.ArrayList;
import java.util.List;

public class FalsePositives {
	
	private BloomFilter bloomFilter;
	private List<String> inputList;
	
	
	public FalsePositives(BloomFilter bloomFilter) {
		this.bloomFilter = bloomFilter;
		this.inputList = new ArrayList<String>();
	}
	
	public float getFalsePositiveRate(){
		
		float fpCount = 0;
		int bFilterLength = 0;
		int searchPositives = 0;
		//create input list 
		permutation("","abcdefg");
		//Insert into bloom filter only alternative elements
		for(int i=0;i<getInputListSize();i=i+2){
			bloomFilter.add(inputList.get(i));
			bFilterLength++;
		}
		//Run through the list again and check for presense of all elements
		for(int i=0;i<getInputListSize();i++){
			if(bloomFilter.appears(inputList.get(i))){
				searchPositives++;
			}
		}
		//calculate False Positives
		fpCount = searchPositives - bFilterLength;
		fpCount = fpCount/searchPositives;
		return fpCount;
	}

	public int getInputListSize(){
		if(inputList != null){
			return inputList.size();
		}
		return -1;
	}

	private void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0) inputList.add(prefix);
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}
	
	public static void main(String[] args) {
		FalsePositives fPositiveFNV = new FalsePositives(new BloomFilterDet(10000, 16));
		System.out.println(fPositiveFNV.getFalsePositiveRate());
		
		FalsePositives fPositiveRND = new FalsePositives(new BloomFilterRan(10000,16));
		System.out.println(fPositiveRND.getFalsePositiveRate());
		
	}

}
