import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import tool.function.ReadWrite;


public class Main_Detection {

	final String sourcePath_absolute = "experimentData/markov/";
	final String markovDataPath = "experimentData/markov/markov.model";
	final String outputPath = "experimentData/markov/testingData.result";
	
	HashMap<String,JSONObject> markovMap =new HashMap<String,JSONObject>();
	
	ReadWrite rw = new ReadWrite();
	double markovModelInitialProbability = 0.00001;
	
	public static void main(String[] args) throws IOException, JSONException {
		Main_Detection mainClass = new Main_Detection();
		ArrayList<String> nameList = mainClass.nameList();
		mainClass.setMarkovModel();
		mainClass.rw.cleanFile(mainClass.outputPath);
		for(String fileName:nameList){
			mainClass.givenTestingDataToResult(fileName);
		}
		
//		System.out.println("yahoo.com:"+mainClass.domainDetection("yahoo.com"));
//		System.out.println("google.com:"+mainClass.domainDetection("google.com"));
//		System.out.println("nologo1093.com:"+mainClass.domainDetection("nologo1093.com"));
		System.out.println("__Main_Detection___Finish_____");
	}
	

	void givenTestingDataToResult(String fileName) throws IOException {
		String sourcePath = this.sourcePath_absolute + fileName;
		FileReader fr = new FileReader(sourcePath);
		BufferedReader br = new BufferedReader(fr);
		while(br.ready()){
			String domain = br.readLine();
			String rawTage = fileName.split("\\.")[0];
			String markovResultTage = this.domainDetection(domain);
			markovResultTage = markovResultTage.split("\\.")[0];
			String data = domain +","+ rawTage +","+ markovResultTage;
			this.rw.appendWrite(this.outputPath, data);
		}
		br.close();
	}
	
	
	
	String domainDetection(String domain){
		String[] dList = domain.split("");
		ArrayList<String> domainLink = new ArrayList<String>();
		for(int i=1;i<dList.length;i++){
			String link = dList[i-1] +">"+ dList[i];
			domainLink.add(link);
		}
		String markovProbabilityMap = this.givenLinkListTakeMarkovValue(domainLink);
//		System.out.println(markovProbabilityMap);
		return markovProbabilityMap;
	}
	String givenLinkListTakeMarkovValue(ArrayList<String> domainLink){
		Map<String, Double> map = new HashMap<String, Double>();
		for(String key:this.markovMap.keySet()){	//有幾個markov model就跑幾次回圈
			double markovProbability=1.0;
			JSONObject js = new JSONObject();
			js = this.markovMap.get(key);
			for(String link:domainLink){
				try {	//因為找不到map 之中的key的時候就會出錯，找得到就沒有問題
					markovProbability = markovProbability * js.getDouble(link);
				} catch (Exception e) {	
					markovProbability = markovProbability * this.markovModelInitialProbability;
				}	
			}
			markovProbability = markovProbability * 10000000;
			map.put(key, markovProbability);
		}

//		System.out.println("_map__:"+map);
		String topClusterName = this.sortByValues(map);
		return topClusterName;
	}
//	這是排序依照map裡面的values,是依照大=>小，所以機率值最大的那一群檔案名稱回傳
	String sortByValues(Map<String, Double> map){
		String result = "";
	    Set<Entry<String, Double>> set = map.entrySet();
        List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Double>>(){
            public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 ){
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        for(Map.Entry<String, Double> entry:list){
//        	resultMap.put(entry.getKey(), entry.getValue());
//            System.out.println(entry.getKey()+" => "+entry.getValue());
            result = entry.getKey();
            break;
        }
        return result;
	}
	
	
	void setMarkovModel() throws IOException, JSONException{
//		HashMap<String,JSONObject> map = new HashMap<String,JSONObject>();
		FileReader fr = new FileReader(this.markovDataPath);
		BufferedReader br = new BufferedReader(fr);
		while(br.ready()){
			String line = br.readLine();
			String name = line.split("@")[0];
			String data = line.split("@")[1];
			JSONObject js = new JSONObject(data);
			this.markovMap.put(name, js);
		}
//		return map;
	}
	
	
	ArrayList<String> nameList(){
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> list = this.rw.readFileName(this.sourcePath_absolute);
		for(String n:list){
			if(n.split("\\.")[1].equals("testing")){
				nameList.add(n);
			}
		}
		return nameList;
	}
}
