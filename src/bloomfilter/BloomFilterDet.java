package bloomfilter;

import java.util.BitSet;

public class BloomFilterDet extends BloomFilter{
	
	BloomFilterDet(int setSize, int bitsPerElement) {
		super(setSize,bitsPerElement);
	}
	
	private Integer[] getKHashValues(String s) {
		Integer[] hashValue = new Integer[numHashes()];
		Long hashCode = fnvHash64Bit(s);
		Byte hash = hashCode.byteValue();
		
		return null;
	}
	
	public long fnvHash64Bit(String s) {
		long FNV_64_INIT = 0xcbf29ce484222325L;
	    long FNV_64_PRIME = 0x100000001b3L;
        long rv = FNV_64_INIT;
        int len = s.length();
        for(int i = 0; i < len; i++) {
            rv ^= s.charAt(i);
            rv *= FNV_64_PRIME;
        }
        return rv;
	}
}
