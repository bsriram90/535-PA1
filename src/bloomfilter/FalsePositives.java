package bloomfilter;

public class FalsePositives {
	
	private BloomFilter bloomFilter;
	
	public FalsePositives(BloomFilter bloomFilter) {
		this.bloomFilter = bloomFilter;
	}
	
	public float getFalsePositiveRate(){
		return 0;
	}
	
	public static void main(String[] args) {
		FalsePositives fPositiveFNV = new FalsePositives(new BloomFilterDet(100, 64));
		System.out.println(fPositiveFNV.getFalsePositiveRate());
		
		FalsePositives fPositiveRND = new FalsePositives(new BloomFilterRan());
		System.out.println(fPositiveRND.getFalsePositiveRate());
	}

}
