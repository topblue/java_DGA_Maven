package main.virus.es;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import tool.function.ElasticsearchFunction;
import tool.function.ReadWrite;

public class Main_DetectDomain {

	ArrayList<String> domainList = new ArrayList<String>();
	ArrayList<String> markovChainsList = new ArrayList<String>();
	ElasticsearchFunction es = new ElasticsearchFunction();
//	ReadWrite rw = new ReadWrite();

	String detectToFile = "data/dgaTest.txt";
	String detectResult = "data/dgaMarkovResult.txt";
	
	public static void main(String[] args) throws Exception {
		Main_DetectDomain mainClass = new Main_DetectDomain();
		
		mainClass.setDomainList();
		mainClass.markovChain();
		for(String domain : mainClass.domainList){
			JSONObject js = mainClass.detectedDomain(domain);
			mainClass.es.insert_defineIndexTypeIdJson("dga", "domainDetectResult", domain, js);
		}
	}
	
	public void setDomainList() throws IOException {
		FileReader fr = new FileReader(this.detectToFile);
		BufferedReader br = new BufferedReader(fr);
		while(br.ready()){
			String line = br.readLine();
			this.domainList.add(line);
		}
	}
	public void markovChain() throws JSONException{
		this.markovChainsList = this.es.queryMarkovModel("dga", "markov");
	}
	public JSONObject detectedDomain(String domain) throws JSONException{
		JSONObject result = new JSONObject();
		JSONObject jsResult = new JSONObject();
		int intResult = 0 ;	//記錄 markovChainsList 的 index值
		double markovNum = 0.0;	//相乘後的最大值
		for(int i=0;i<this.markovChainsList.size();i++){
			double markovNumCompare=1.0;
			JSONObject js = new JSONObject(this.markovChainsList.get(i));
			JSONObject markovjs = new JSONObject(js.getString("markovModel"));
			String[] domainSplit = domain.split("");
			for (int j = 0; j < domainSplit.length-1; j++) {
				String d = domainSplit[j]+">"+domainSplit[j+1];
				try {	//因為找不到map 之中的key的時候就會出錯，找得到就沒有問題
					markovNumCompare = markovNumCompare * markovjs.getDouble(d);
				} catch (Exception e) {	}	
			}
			if (markovNumCompare>markovNum) {
				markovNum = markovNumCompare;	//這次的domain的機率值，比上次的值來的大
				intResult =i;
			}
		}
		jsResult = new JSONObject(this.markovChainsList.get(intResult));
		result.put("malwares", jsResult.get("malwares"));
		result.put("clusterNum", jsResult.get("clusterNum"));
		result.put("domain", domain);
		return result;
	}
	

}
