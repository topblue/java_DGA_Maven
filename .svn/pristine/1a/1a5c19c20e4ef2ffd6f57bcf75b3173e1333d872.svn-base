package temp;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TestSortMap {

	public static void main(String[] args) {
		TestSortMap testMain = new TestSortMap();
		testMain.putAndGetMap();

	}


	void putAndGetMap(){
		Map<String, String> map = new TreeMap<String, String>();  
		map.put("12345", "kfc");  
		map.put("123457", "wnba");  
		map.put("12344", "nba");  
		map.put("123455", "cba"); 
        Map<String, String> resultMap = sortMapByKey(map);    //按Key进行排序  
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {  
        	System.out.println(entry.getKey() + " " + entry.getValue());  
        }
	}
    /** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
	public Map<String, String> sortMapByKey(Map<String, String> map) {  
		if (map == null || map.isEmpty()) {  
			return null;  
		}
		Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
		sortMap.putAll(map); 
	return sortMap;
	} 
}
class MapKeyComparator implements Comparator<String>{
	@Override
	public int compare(String str1, String str2) { 
		return str1.compareTo(str2);  
	}  
}  
