import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import main.virus.es.Main_characteristic;

import org.json.JSONException;

import tool.function.ElasticsearchFunction;
import tool.function.ReadWrite;


public class Main_FileTo17Feature {
	
	final String featuresOutput = "experimentData/tagDataset/type1234.16f";
//	final String sourcePath = "experimentData/tagDataset/50Traindga"; 
	final String sourcePath = "experimentData/tagDataset/type.1234"; 
	
	public static void main(String[] args) throws JSONException, IOException {
		Main_FileTo17Feature mainClass = new Main_FileTo17Feature();
		ReadWrite rw = new ReadWrite();
		rw.cleanFile(mainClass.featuresOutput);
		HashMap<String,ArrayList<String>> malwareToDomain = mainClass.setMalwareToDomain();
		String totalFeatures = "name,f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16";
		rw.appendWrite(mainClass.featuresOutput, totalFeatures);
		Main_characteristic mainCharacter = new Main_characteristic();
		int i=0;
		for(String malware:malwareToDomain.keySet()){
			System.out.println(malware +":"+ malwareToDomain.get(malware).size());
			ArrayList<String> domainList = new ArrayList<String>();
			domainList = malwareToDomain.get(malware);
			if(domainList.size()>1 && !malware.equals("top-1m-onlyDomain.txt")){//&& domainList.size()>4 && domainList.size()==4  ){
//143,4
				HashMap<String,Double> feature = mainCharacter.characteristic(domainList);
				String features = feature.get("f1")+","+feature.get("f2")+","+feature.get("f3")+","+feature.get("f4")+","+
					feature.get("f5")+","+feature.get("f6")+","+feature.get("f7")+","+feature.get("f8")+","+
					feature.get("f9")+","+feature.get("f10")+","+feature.get("f11")+","+feature.get("f12")+","+
					feature.get("f13")+","+feature.get("f14")+","+feature.get("f15")+","+
					feature.get("f16");
				features=malware+","+features;
				rw.appendWrite(mainClass.featuresOutput, features);
//				System.out.println(features +"\n\r");
//				if(i==1){break;}else{i++;}
			}
		}
		System.out.println("____________Main_FileTo17Feature_________Finish________");
	}

//	{"path":"experimentData/milaOutput/","malware":"Alurewo","label":"2"}
//	time.windows.com , Alurewo , 2 , false
//	f5f5dc.com,Alurewo,2,false
	HashMap<String,ArrayList<String>> setMalwareToDomain() throws IOException{
		HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
		
		BufferedReader br = new BufferedReader(new FileReader(this.sourcePath));
		int lineCount = 0;
		while(br.ready() ){
			String line = br.readLine();
			if(lineCount>0){
				String domain = line.split(",")[0];
				String tag = line.split(",")[1];

				if(map.containsKey(tag)){
					ArrayList<String> arr = new ArrayList<String>();
					arr = map.get(tag);
					arr.add(domain);
					map.put(tag, arr);
				}else{
					ArrayList<String> arr = new ArrayList<String>();
					arr.add(domain);
					map.put(tag, arr);
				}
			}
			lineCount++;
		}
		
		return map;
	}
}
