package bloomfilter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public class BloomJoin {
	
	private BloomFilter createBloomFilter(Set<String> relation){
		BloomFilter bFilter = new BloomFilterRan(relation.size()/4, 8);

		Iterator<String> colIterator = relation.iterator();
		while(colIterator.hasNext()){
			bFilter.add(colIterator.next());
		}
		return bFilter;
	}
	
	private HashMap<String,List<String>> getReducedMap(HashMap<String,List<String>> relation, BloomFilter bFilter){
		HashMap<String,List<String>> reducedMap = new HashMap<>();
		Iterator<String> colIterator = relation.keySet().iterator();
		while(colIterator.hasNext()){
			String key = colIterator.next();
			if(bFilter.appears(key)){
				reducedMap.put(key, relation.get(key));
			}
		}
		return reducedMap;
	}
	
	private HashMap<String,List<String>> getRelationMap(String fileName){
		HashMap<String,List<String>> relation = new HashMap<>();
		try {
			FileReader fir = new FileReader(fileName);
			BufferedReader bufReader = new BufferedReader(fir);
			while(bufReader.ready()){
				String line = bufReader.readLine();
				String [] tuple = new String[2];
				StringTokenizer tokenizer = new StringTokenizer(line);
				if(fileName.contains(".csv") ){
					tokenizer = new StringTokenizer(line, ",");
				}
				if(tokenizer.countTokens() == 2){
					tuple[0] = tokenizer.nextToken();
					tuple[1] = tokenizer.nextToken();
					if(relation.containsKey(tuple[0])){
						List<String> valueList = relation.get(tuple[0]);
						valueList.add(tuple[1]);
					}else{
						List<String> valueList =  new ArrayList<>();
						valueList.add(tuple[1]);
						relation.put(tuple[0],valueList);
					}
				}
				
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return relation;
	}
	
	private void joinRelations(HashMap<String,List<String>> relation1, 
			HashMap<String,List<String>> relation2, String outputFileName){
		try {
			FileWriter fWriter = new FileWriter(outputFileName);
			Set<String> relation1Keys = relation1.keySet();
			Iterator<String> iter = relation1Keys.iterator();
			while(iter.hasNext()){
				String key = iter.next();
				if(relation2.containsKey(key)){
					//for each element in inputList of relation, merge with each item in list of relation2
					List<String> list1Values = relation1.get(key);
					for(String list1Value : list1Values){
						for(String list2Value: relation2.get(key)){
							fWriter.write(list1Value+"\t"+key+"\t"+list2Value+"\n");
						}
					}
				}
			}
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void joinRelations(String file1, String file2, String outputFileName){
		HashMap<String,List<String>> relation1 = getRelationMap(file1);
		HashMap<String,List<String>> relation2 = getRelationMap(file2);
		
		BloomFilter bFilter = createBloomFilter(relation1.keySet());
		HashMap<String,List<String>> relation3 = getReducedMap(relation2, bFilter);
		
		joinRelations(relation1, relation3, outputFileName);
	}
	
	public static void main(String[] args) {
		BloomJoin bJoin = new BloomJoin();
		bJoin.joinRelations("/Users/nishanthsivakumar/Desktop/Relation1.txt", 
				"/Users/nishanthsivakumar/Desktop/Relation2.txt", 
				"/Users/nishanthsivakumar/Desktop/Relation3.txt");
		
	}

}
