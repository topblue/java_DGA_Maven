package last.dga.dected.experiment;

import java.util.ArrayList;
import java.util.HashMap;

public class DistributionCharacters {

	HashMap<String,Integer> unigram(ArrayList<String> list){
//		System.out.println(list);
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		for(String domain:list){
			String[] d = domain.split("");
			for(String d2:d){
				if(map.containsKey(d2)){
					int count = map.get(d2);
					count++;
					map.put(d2, count);
				}else{
					map.put(d2, 1);
				}
			}
		}
//		System.out.println(map);
		return map;
	}
	
	HashMap<String,Integer> bigram(ArrayList<String> list){
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		for(String domain:list){
			String[] d = domain.split("");
			for(int i=1;i<d.length;i++){
				String d2 = d[i-1]+d[i];
				if(map.containsKey(d2)){
					int count = map.get(d2);
					count++;
					map.put(d2, count);
				}else{
					map.put(d2, 1);
				}
			}
		}
		return map;
	}

}
