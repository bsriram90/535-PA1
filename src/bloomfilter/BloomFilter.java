package bloomfilter;

import java.util.BitSet;

public class BloomFilter {
	private int bitsPerElement;
	private int setSize;
	private BitSet filter;
	private int counter = 0;
	
	public BloomFilter() {}
	
	public BloomFilter(int setSize, int bitsPerElement) {
		this.bitsPerElement = bitsPerElement;
		this.setSize = setSize;
		filter = new BitSet(bitsPerElement * setSize);
	}
	
	public void add(String s) {
		Integer[] hashValues = getKHashValues(s);
		for (Integer hash : hashValues) {
			filter.set((int) (hash%filterSize()));
		}
		counter++;
	}
	
	protected Integer[] getKHashValues(String s) {
		return null;
	}

	public int filterSize() {
		return (bitsPerElement * setSize);
	}
	
	public boolean appears(String s) {
		Integer[] hashValues = getKHashValues(s);
		for (Integer hash : hashValues) {
			if ( !filter.get(hash%filterSize())) {
				return false;
			}
		}
		return true;
	}
	
	public int numHashes() {
		int numHashes = (int) (Math.log(2)*(new Double(filterSize())/new Double(this.setSize)));
		return numHashes;
	}
	
	public int dataSize() {
		return counter;
	}

}
