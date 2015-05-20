package last.dga.dected.experiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import tool.function.ReadWrite;

public class MergeDataFromTotally_4 {

	final String referRouteToPath = "experimentData/designExperimentData.csv"; 
	final String outputPath = "experimentData/type_1234/"; 

	HashMap<String,ArrayList<String>> typeToDNSListMap = new HashMap<String,ArrayList<String>>();

	ReadWrite rw = new ReadWrite();
	
	public static void main(String[] args)  throws IOException{
		MergeDataFromTotally_4 mainClass = new MergeDataFromTotally_4();
		DistributionCharacters distribute = new DistributionCharacters();
		
		ArrayList<HashMap<String,String>> pathList = mainClass.pathList();
		for(HashMap<String,String> pathMap:pathList){
			String path = pathMap.get("path");
			String label = pathMap.get("label");
			if(mainClass.typeToDNSListMap.containsKey(label)){		//這個map裡面包含label的key
				ArrayList<String> dnsList = new ArrayList<String>();
				dnsList = mainClass.typeToDNSListMap.get(label);
				dnsList = mainClass.readDomainList(path, dnsList);
				mainClass.typeToDNSListMap.put(label, dnsList);
			}else{		//之前沒有這個key
				ArrayList<String> dnsList = new ArrayList<String>();
				dnsList = mainClass.readDomainList(path,new ArrayList<String>());
				mainClass.typeToDNSListMap.put(label, dnsList);
			}
		}
		
		//1:正常網域名稱,2:非動態產生之網域名稱,3:動態產生之長字串網域名稱,4:動態產生之短字串網域名稱
//		String p="1";	
		HashMap<String,Integer> uni1map = distribute.unigram(mainClass.typeToDNSListMap.get("1"));
		HashMap<String,Integer> uni2map = distribute.unigram(mainClass.typeToDNSListMap.get("2"));
		HashMap<String,Integer> uni3map = distribute.unigram(mainClass.typeToDNSListMap.get("3"));
		HashMap<String,Integer> uni4map = distribute.unigram(mainClass.typeToDNSListMap.get("4"));
		mainClass.mergefromMap(uni1map, uni2map, uni3map, uni4map, "uni");
//		mainClass.givenPathOutputMap(uni1map,"uni1map"+1);
		
		HashMap<String,Integer> bi1map = distribute.bigram(mainClass.typeToDNSListMap.get("1"));
		HashMap<String,Integer> bi2map = distribute.bigram(mainClass.typeToDNSListMap.get("2"));
		HashMap<String,Integer> bi3map = distribute.bigram(mainClass.typeToDNSListMap.get("3"));
		HashMap<String,Integer> bi4map = distribute.bigram(mainClass.typeToDNSListMap.get("4"));
		mainClass.mergefromMap(bi1map, bi2map, bi3map, bi4map, "bi");
//		mainClass.givenPathOutputMap(bi1map,"bi1map"+1);
		
		System.out.println("===finish===");
	}
	
	void mergefromMap(HashMap<String,Integer> map1,HashMap<String,Integer> map2,HashMap<String,Integer> map3,HashMap<String,Integer> map4
			,String path){
		
		path = this.outputPath+path;
		this.rw.cleanFile(path);
		
		ArrayList<String> elementList = new ArrayList<String>();

		for(String key:map1.keySet()){
			if(!elementList.contains(key)){
				elementList.add(key);
			}
		}
		for(String key:map2.keySet()){
			if(!elementList.contains(key)){
				elementList.add(key);
			}
		}
		for(String key:map3.keySet()){
			if(!elementList.contains(key)){
				elementList.add(key);
			}
		}
		for(String key:map4.keySet()){
			if(!elementList.contains(key)){
				elementList.add(key);
			}
		}
		
		for(String element:elementList){
			String outputData = element+",";
			if(map1.containsKey(element)){
				outputData=outputData+map1.get(element)+",";
			}else{	outputData=outputData+0+",";	}
			if(map2.containsKey(element)){
				outputData=outputData+map2.get(element)+",";
			}else{	outputData=outputData+0+",";	}
			if(map3.containsKey(element)){
				outputData=outputData+map3.get(element)+",";
			}else{	outputData=outputData+0+",";	}
			if(map4.containsKey(element)){
				outputData=outputData+map4.get(element);
			}else{	outputData=outputData+0;	}
			
			this.rw.appendWrite(path, outputData);
		}
		
	}
	
	void givenPathOutputMap(HashMap<String,Integer> map,String p){
		String path = this.outputPath+p;
		this.rw.cleanFile(path);
		for(String key:map.keySet()){
			String data = key+","+ map.get(key);
			this.rw.appendWrite(path, data);
		}
	}
	
	ArrayList<String> readDomainList(String path,ArrayList<String> domainList) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(path));
		while(br.ready() ){
			String line = br.readLine();
			domainList.add(line);
		}
		return domainList;
	}
	
	ArrayList<HashMap<String,String>> pathList() throws IOException{
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		BufferedReader br = new BufferedReader(new FileReader(this.referRouteToPath));
		int lineCount = 0;
		while(br.ready() ){
			String line = br.readLine();
			if(lineCount>0){
				String path = line.split(",")[0]+line.split(",")[1];
				String malware = line.split(",")[1];
				String label = line.split(",")[2];

				HashMap<String,String> map = new HashMap<String,String>();
				map.put("path", path);
				map.put("malware", malware);
				map.put("label", label);
				
				list.add(map);
			}
			lineCount++;
		}
		
		return list;
	}

}
