package bloomfilter;

import java.math.BigInteger;
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
		BigInteger[] hashValues = getKHashValues(s);
		for (BigInteger hash : hashValues) {
			filter.set((hash.mod(BigInteger.valueOf(filterSize()))).intValue());
		}
		counter++;
		
	}
	
	protected BigInteger[] getKHashValues(String s) {
		return null;
	}

	public int filterSize() {
		return (bitsPerElement * setSize);
	}
	
	public boolean appears(String s) {
		BigInteger[] hashValues = getKHashValues(s);
		for (BigInteger hash : hashValues) {
			if ( !filter.get((hash.mod(BigInteger.valueOf(filterSize()))).intValue())) {
				return false;
			}
		}
		return true;
	}
	
	public int numHashes() {
		int numHashes = (int) Math.ceil((Math.log(2)*(new Double(filterSize())/new Double(this.setSize))));
		return numHashes;
	}
	
	public int dataSize() {
		return counter;
	}

}
