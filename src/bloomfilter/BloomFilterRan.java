package bloomfilter;

import java.math.BigInteger;

public class BloomFilterRan extends BloomFilter {
	
	private Integer prime = null;
	private Integer[] a = null;
	private Integer[] b = null;
	
	BloomFilterRan(int setSize, int bitsPerElement) {
		super(setSize,bitsPerElement);
		BigInteger filterSize = BigInteger.valueOf(filterSize());
		prime = filterSize.nextProbablePrime().intValue();
		if(prime <0){
			prime = prime*(-1);
		}
		Integer k = numHashes();
		a = new Integer[k];
		b = new Integer[k];
		for(int i=0; i<k ; i++){
			a[i] = (int) (Math.random() * prime);
			b[i] = (int) (Math.random() * prime);
		}
	}
	
	protected Integer[] getKHashValues(String s) {
		Integer[] hashValue = new Integer[numHashes()];
		Integer hash = s.hashCode();
		for (int i=0;i<numHashes();i++){
			hashValue[i] = (a[i] + b[i]*hash) % prime;
			if(hashValue[i] < 0){
				hashValue[i] *= (-1);
			}
		}
		return hashValue;
	}
}
