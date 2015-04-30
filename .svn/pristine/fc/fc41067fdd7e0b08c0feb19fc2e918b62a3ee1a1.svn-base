package main.virus.es;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import java_cup.internal_error;

import org.json.JSONException;
import org.json.JSONObject;

import tool.function.ElasticsearchFunction;
import tool.function.KmeansF17;
import tool.function.MarkovModelBigramDomainList;
import tool.function.MarkovModelUnigramDomainList;

public class Main_markov {

	ElasticsearchFunction es = new ElasticsearchFunction();
	ArrayList<String> domainList = new ArrayList<String>();
	final String  esIndex = "dga",esType = "CRIME";
	
	public static void main(String[] args) throws Exception {

		Main_markov mainClass = new Main_markov();

//		MarkovModelBigramDomainList markovModel = new MarkovModelBigramDomainList();
		MarkovModelUnigramDomainList markovModel = new MarkovModelUnigramDomainList();		//MarkovModelUnigramDomainList

		KmeansF17 kmeans = new KmeansF17();
		
		HashMap<Integer, ArrayList<String>> kMap = kmeans.Kmeans();
		Iterator it = kMap.entrySet().iterator();
		while(it.hasNext()){
			mainClass.domainList.clear();
			Map.Entry entry = (Map.Entry)it.next();
			String key = entry.getKey().toString();
			ArrayList<String> vlist = (ArrayList<String>) entry.getValue();
			mainClass.getKmeansMalwareGroupTodomainList(vlist);	//將群聚好的malware到es中查詢，並且放到domainList之中
			System.out.println("mainClass.domainList 大小 :"+mainClass.domainList.size());

			JSONObject markovModelResult = markovModel.markovModel(mainClass.domainList);
			JSONObject toESjs = new JSONObject();
			System.out.println("key :"+key+"\tmarkovModelResult.toString() : "+markovModelResult.toString()+"\tvlist : "+vlist);
			toESjs.put("clusterNum", key);
			toESjs.put("markovModel", markovModelResult.toString());
			toESjs.put("malwares", vlist);
//			mainClass.es.insertMarkovModelToES_defineID(toESjs, key);
			
		}
		
		/**
		String malwareFilename = "BIN_CitadelPacked_2012-05";
		mainClass.setDomainFromES(malwareFilename);
		JSONObject markovModelResult = markovModel.markovModel(mainClass.domainList);
		JSONObject toESjs = new JSONObject();
		toESjs.put("malware", malwareFilename);
		toESjs.put("markovModel", markovModelResult.toString());
		mainClass.es.insertMarkovModelToES_defineID(toESjs, malwareFilename);
		**/
//		mainClass.es.insertDomainToES(markovModelResult);
//		for(String domain:mainClass.domainList){
//			System.out.println(domain);
//		}
	}

	/**
	 * @see 將k-means群聚好的惡意軟體名稱放在同一群list放入到arrylist，並且利用惡意軟體名稱在elasticsearch中查詢
	 * */
	void getKmeansMalwareGroupTodomainList(ArrayList<String> kMalwareList){
		for(String malware:kMalwareList){
			setDomainFromES(malware);
		}
	}
	
	
	/**
	 * 將Eleasticsearch中的domain name放到 domaArrayList<String> domainListinList
	 * @param String malwareFilename
	 * **/
	void setDomainFromES(String malwareFilename){
		ArrayList<String> fromES =  es.queryStringDomain(this.esIndex, this.esType, malwareFilename);
		for (String d:fromES) {
			this.domainList.add(d);
		}
//		this.domainList = es.queryStringDomain(this.esIndex, this.esType, malwareFilename);
	}
}
