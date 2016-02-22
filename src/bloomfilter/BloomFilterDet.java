package bloomfilter;

import java.math.BigInteger;

public class BloomFilterDet extends BloomFilter{
	
	
	
	BloomFilterDet(int setSize, int bitsPerElement) {
		super(setSize,bitsPerElement);
	}
	
	protected BigInteger[] getKHashValues(String s) {
		Integer k = numHashes();
		BigInteger[] hashValue = new BigInteger[k];
		Long hashCode = fnvHash64Bit(s);
		String binaryHash = Long.toBinaryString(hashCode);
		for (int i=0; i<k; i++) {
			String binaryHashValue = binaryHash.substring(i,31+i);
			BigInteger preHash = new BigInteger(binaryHashValue,2);
			hashValue[i] = preHash.add(BigInteger.valueOf(1000 * i));
		}
		return hashValue;
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
