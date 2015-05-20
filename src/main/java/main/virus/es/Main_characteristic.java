package main.virus.es;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import tool.function.AlgorithmString;
import tool.function.DomainStringFormat;
//import tool.function.ElasticsearchFunction;
import tool.function.FeatureBeans;

public class Main_characteristic {

//	ElasticsearchFunction es = new ElasticsearchFunction();
//	final String  esIndex = "dga",esType = "CRIME";
	
	FeatureBeans featurebeans = new FeatureBeans();
//	ArrayList<String> domainList = new ArrayList<String>();
	String tdls = "virusData/ICANN-TLDs.txt";

	public HashMap<String,Double> characteristic(ArrayList<String> domainList) throws JSONException, IOException {
		Main_characteristic mainClass = new Main_characteristic();
		HashMap<String,Double> featuresMap = new HashMap<String,Double>();

//		ArrayList<String> domainList = new ArrayList<String>();
		
//		mainClass.setDomainFromFile(malwareMap.get("path"));	//將dommain name放到ArrayList);

		
		
		domainList.removeAll(Collections.singleton(""));  //去除重複
//		System.out.println(",size:"+domainList.size() +",裡面："+domainList );
		/*****/
		double f1 = mainClass.features1(domainList);
		featuresMap.put("f1", f1);
		
		ClassF2 f2Class = mainClass.features2(domainList);
		double f2 = f2Class.mean;
		featuresMap.put("f2", f2);
		
		double f3 = mainClass.features3(f2Class);
		featuresMap.put("f3", f3);
		
		ClassF4 f4Class = mainClass.features4(domainList);
		double f4 = f4Class.mean;
		featuresMap.put("f4", f4);
		
		double f5 = mainClass.features5(f4Class);
		featuresMap.put("f5", f5);
		
		ClassF6 f6Class = mainClass.features6(domainList);
		double f6 = f6Class.mean;
		featuresMap.put("f6", f6);
		
		double f7 = mainClass.features7(f6Class);
		featuresMap.put("f7", f7);
		
		ClassF8 f8Class = mainClass.features8(domainList);
		double f8 = f8Class.mean;
		featuresMap.put("f8", f8);
		
		double f9 = mainClass.features9(f8Class);
		featuresMap.put("f9", f9);
		
		ClassF10 f10Class = mainClass.features10(domainList);
		double f10 = f10Class.mean;
		featuresMap.put("f10", f10);
		
		double f11 = mainClass.features11(f10Class);
		featuresMap.put("f11", f11);
		
		ClassF12 f12Class = mainClass.features12(domainList);
		double f12 = f12Class.mean;
		featuresMap.put("f12", f12);
		
		double f13 = mainClass.features13(f12Class);
		featuresMap.put("f13", f13);
		
		ClassF14 f14Class = mainClass.features14(domainList);
		double f14 = f14Class.mean;
		featuresMap.put("f14", f14);
		
		double f15 = mainClass.features15(f14Class);
		featuresMap.put("f15", f15);
		
		double f16 = mainClass.features16(domainList);
//		double f16 = f16Class.mean;
//		System.out.println("f16:"+f16);
		featuresMap.put("f16", f16);
		/***
		double f17 = mainClass.features17(f16Class);
		featuresMap.put("f17", f17);
		**/
//		mainClass.queryIdToDelete();
		return featuresMap;
	}
	
	/**
	 * @param 惡意軟體名稱
	 * @see 每個惡意軟體的亂度
	 * @return double:亂度
	 * */
	double features1(ArrayList<String> domainList){
		DomainStringFormat domainstringformat = new DomainStringFormat();
		AlgorithmString algo = new AlgorithmString();
		String mergeDomain = "";
		for(String domain:domainList){
			mergeDomain = mergeDomain+domain;
		}
		double domainSize = new Double(mergeDomain.length()); 
		
		HashMap<String,Integer> map = domainstringformat.splitChart(mergeDomain);	//將字串皆成字元，並計算後放到HashMap之中
		Iterator iter = map.entrySet().iterator();
		double features1_result = 0.0;
		while (iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    String key = entry.getKey().toString();
		    double val = Double.parseDouble(entry.getValue().toString());
		    double result = algo.entropy(val, domainSize);	//entropy
		    features1_result = algo.double_add(features1_result, result);
		}
		return features1_result*(-1);
	}
	
	/**
	 * @param 惡意軟體名稱
	 * @see 每個惡意軟體網域名稱亂度平均數
	 * 
	 * **/
	ClassF2 features2(ArrayList<String> domainList){
		DomainStringFormat domainstringformat = new DomainStringFormat();
		AlgorithmString algo = new AlgorithmString();
		double features2_total=0.0,features2_size =0.0;
		ArrayList<Double> entropyList = new ArrayList<Double>();
		for(String domain:domainList){
			double domainSize = new Double(domain.length()); 
			HashMap<String,Integer> map = domainstringformat.splitChart(domain);	//將字串皆成字元，並計算後放到HashMap之中
//			System.out.println(domain+"__"+map);
			Iterator iter = map.entrySet().iterator();
			double domain_entropy = 0.0;
			while (iter.hasNext()) {
			    Map.Entry entry = (Map.Entry) iter.next();
			    double val = Double.parseDouble(entry.getValue().toString());
			    double result = algo.entropy(val, domainSize);	//entropy
			    domain_entropy = algo.double_add(domain_entropy, result);
//			    System.out.println(val +"___"+ domainSize +"="+ domain_entropy);
			}
			domain_entropy = domain_entropy * (-1);
//			System.out.println(domain +"_domain_entropy * -1 = "+ domain_entropy);
			entropyList.add(domain_entropy);
			features2_size++;
			features2_total =algo.double_add(features2_total, domain_entropy);
		}
		double mean = (features2_total/features2_size);
		ClassF2 classf2 = new ClassF2(features2_size, mean, entropyList);
		return classf2;
		
	}
	/**@category double size 
	 * @category double mean 
	 * @category ArrayList<Double> entropyList
	 * **/
	private static class ClassF2 { 
		private double size, mean;
		private ArrayList<Double> entropyList;
		public ClassF2(double size, double mean,ArrayList<Double> entropyList) { 
			this.size = size; 
			this.mean = mean;
			this.entropyList = entropyList;
		}
	} 
	
	/**
	 * @see 每個惡意軟體網域名稱亂度變異數
	 * @param eatures2計算好的：亂數陣列(entropy)，陣列大小數值，平均數
	 * **/
	double features3(ClassF2 f2Class){
		AlgorithmString algo = new AlgorithmString();
		double result = algo.variance(f2Class.entropyList, f2Class.size, f2Class.mean);
		return result;
	}
	
	/**
	 * @see 每個惡意軟體網域名稱長度的平均數的亂度
	 * @param 惡意軟體名稱
	 * **/
	ClassF4 features4(ArrayList<String> domainList){
		DomainStringFormat domainstringformat = new DomainStringFormat();
		AlgorithmString algo = new AlgorithmString();
		ArrayList<Double> list_strSize = new ArrayList<Double>();
		ArrayList<Double> entropyList = new ArrayList<Double>();
		double avg=0.0,entropy_total=0.0,result=0.0;
		for(String domain:domainList){
			int domainSize = domain.split("").length;	//每個domain name的長度
			list_strSize.add((double)domainSize);	//將domain name長度轉成double放到arraylist之中
		}
//		avg = algo.average_int(list_strSize);	//將網域名稱所有長度，除上此陣列的長度，求出平均數
		HashMap<Double,Double> map = domainstringformat.domainSize(list_strSize);	//key是字串長度，value是出現的次數
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
//			System.out.println(entry.getKey());
			double val = Double.parseDouble(entry.getValue().toString());
			double entropy=algo.entropy(val, list_strSize.size())*(-1);	//entropy(字串長度出現的次數 , 將所有次數放在分母)，將亂數值得-值
//			System.out.println("entropy:"+entropy);
			entropy_total = entropy_total + entropy;
			entropyList.add(entropy);
		}
		result = algo.div(entropy_total,map.size());	//entropy_total為分子，map大小為分母
//		System.out.println("result:"+result);
		ClassF4 classf4 = new ClassF4(map.size(),result,entropyList);
//		System.out.println("features4:"+result);
		return classf4;
	}
	/**@category double size 
	 * @category double mean 
	 * @category ArrayList<Double> entropyList
	 * **/
	private static class ClassF4 { 
		private double size, mean;
		private ArrayList<Double> entropyList;
		public ClassF4(double size, double mean,ArrayList<Double> entropyList) { 
			this.size = size; 
			this.mean = mean;
			this.entropyList = entropyList;
		}
	} 
	
	/**
	 * @see 每個惡意軟體網域名稱長度的亂度變異數
	 * @param ClassF4
	 * **/
	double features5(ClassF4 f4Class){
		AlgorithmString algo = new AlgorithmString();
		double result = algo.variance(f4Class.entropyList, f4Class.size, f4Class.mean);
		return result;
	}

	/**
	 * @see 每個惡意軟體網域名稱英文字母比例的平均數
	 * @param null
	 * **/
	ClassF6 features6(ArrayList<String> domainList){
		ArrayList<Double> englishLetterListRatio = new ArrayList<Double>();
		String english="[a-zA-Z]";
		double ratio_count=0.0,result=0.0;
		for(String domain:domainList){
			double en_count =0.0;
			for(int i=0;i<domain.length();i++){
				String d = domain.split("")[i];
				if(d.matches(english)){
					en_count++;
				}
			}
			double ratio = en_count/domain.length();
			ratio_count = ratio_count+ratio;
			englishLetterListRatio.add(ratio);
//			System.out.println(en_count+"/"+domain.length()+"==="+domain);
		}
		result = ratio_count/domainList.size();
		ClassF6 classF6 = new ClassF6(englishLetterListRatio.size(),result,englishLetterListRatio);
		return classF6;
	}
	private static class ClassF6{
		private double size,mean;
		private ArrayList<Double> englishLetterListRatio;
		private ClassF6(double size,double mean,ArrayList<Double> englishLetterListRatio){
			this.size = size; 
			this.mean = mean;
			this.englishLetterListRatio = englishLetterListRatio;
		}
	}
	/**
	 * @see 每個惡意軟體網域名稱英文字母比例的變異數
	 * @param ClassF6
	 * **/
	double features7(ClassF6 f6Class){
		AlgorithmString algo = new AlgorithmString();
//		System.out.println(f6Class.englishLetterListRatio +" _,_ "+ f6Class.size +" _,_ "+ f6Class.mean);
		double result = algo.variance(f6Class.englishLetterListRatio, f6Class.size, f6Class.mean);
//		System.out.println("F7:"+result);
		return result;
	}
	
	/**
	 * @see 每個惡意軟體網域名稱數字比例的平均數
	 * @param null
	 * @return ClassF8
	 * **/
	ClassF8 features8(ArrayList<String> domainList){
		ArrayList<Double> numverListRatio = new ArrayList<Double>();
		String number ="[0-9]";
		double ratio_number = 0.0 , result = 0.0;
		for(String domain:domainList){
			double num_count = 0.0;
			for(int i=0;i<domain.length();i++){
				String d = domain.split("")[i];
				if(d.matches(number)){
					num_count++;
				}
			}
			double ratio = num_count/domain.length();
			numverListRatio.add(ratio);	//每個domain的數值的比例
			ratio_number = ratio_number + ratio;
		}
		result = ratio_number/numverListRatio.size();	//將每個網域名稱數值比例加總當作分子，分母為網域名稱個數
		ClassF8 classF8 = new ClassF8(numverListRatio.size(),result,numverListRatio);
		return classF8;
	}
	private static class ClassF8{
		private double size,mean;
		private ArrayList<Double> numberListRatio;
		private ClassF8(double size,double mean,ArrayList<Double> numberListRatio){
			this.size = size;
			this.mean = mean;
			this.numberListRatio = numberListRatio;
		}
	}

	/**
	 * @see 每個惡意軟體網域名稱數字比例的變異數
	 * @param ClassF8
	 * @return double
	 * **/
	double features9(ClassF8 f8Class){
		AlgorithmString algo = new AlgorithmString();
		double result = algo.variance(f8Class.numberListRatio, f8Class.size, f8Class.mean);
		return result;
	}
	
	/**
	 * @see 每個惡意軟體網域名稱母音(vowel sound)比例的平均數
	 * @param null
	 * **/
	ClassF10 features10(ArrayList<String> domainList){
		ArrayList<Double> vowelSoundListRatio = new ArrayList<Double>();
		String en = "[a-zA-Z]";
		String vowel="[aeiouAEIOU]";
		double ratio_count=0.0,result=0.0;
		for(String domain:domainList){
			double vowel_count = 0.0;
			for(int i=0;i<domain.length();i++){
				String d = domain.split("")[i];
				if(d.matches(vowel) && d.matches(en)){
					vowel_count++;
				}
			}
			double ratio = vowel_count / domain.length();	//字母的比率
			ratio_count = ratio_count + ratio;	//累計比率
			vowelSoundListRatio.add(ratio);
//			System.out.println("名稱："+domain+"字串蟲長度："+domain.length()+"\t母音："+vowel_count +"，比例："+ratio);
		}
		result = ratio_count / vowelSoundListRatio.size();
		ClassF10 classF10 = new ClassF10(vowelSoundListRatio.size(),result,vowelSoundListRatio);
		return classF10;
	}
	private static class ClassF10{
		private double size,mean;
		private ArrayList<Double> vowelSoundListRatio;
		private ClassF10(double size,double mean,ArrayList<Double> vowelSoundListRatio){
			this.size = size; 
			this.mean = mean;
			this.vowelSoundListRatio = vowelSoundListRatio;
		}
	}
	/**
	 * @see 每個惡意軟體網域名稱英文字母的母音比例的變異數
	 * @param ClassF6
	 * **/
	double features11(ClassF10 f10Class){
		AlgorithmString algo = new AlgorithmString();
		double result = algo.variance(f10Class.vowelSoundListRatio, f10Class.size, f10Class.mean);
		return result;
	}
	
	
	/**
	 * @see 每個惡意軟體網域名稱"子音"(consonant sounds)比例的平均數
	 * @param null
	 * **/
	ClassF12 features12(ArrayList<String> domainList){
		ArrayList<Double> consonantSoundListRatio = new ArrayList<Double>();
		String en = "[a-zA-Z]";
		String consonant="[^aeiouAEIOU]";
		double ratio_count=0.0,result=0.0;
		for(String domain:domainList){
			double consonant_count = 0.0;
			for(int i=0;i<domain.length();i++){
				String d = domain.split("")[i];
				if(d.matches(consonant) && d.matches(en)){
					consonant_count++;
				}
			}
			double ratio = consonant_count / domain.length();	//字母的比率
			ratio_count = ratio_count + ratio;	//累計比率
			consonantSoundListRatio.add(ratio);
//			System.out.println("名稱："+domain+"字串蟲長度："+domain.length()+"\t母音："+consonant_count +"，比例："+ratio);
		}
		result = ratio_count / consonantSoundListRatio.size();
		ClassF12 classF12 = new ClassF12(consonantSoundListRatio.size(),result,consonantSoundListRatio);
		return classF12;
	}
	private static class ClassF12{
		private double size,mean;
		private ArrayList<Double> consonantSoundListRatio;
		private ClassF12(double size,double mean,ArrayList<Double> consonantSoundListRatio){
			this.size = size; 
			this.mean = mean;
			this.consonantSoundListRatio = consonantSoundListRatio;
		}
	}
	/**
	 * @see 每個惡意軟體網域名稱英文字母的"子音"比例的變異數
	 * @param ClassF13
	 * **/
	double features13(ClassF12 f12Class){
		AlgorithmString algo = new AlgorithmString();
		double result = algo.variance(f12Class.consonantSoundListRatio, f12Class.size, f12Class.mean);
		return result;
	}
	
	/**
	 * @see 每個惡意軟體網域名稱組合階層的平均數
	 * @param null
	 * **/
	ClassF14 features14(ArrayList<String> domainList){
		double layer_count = 0.0,layer_avg = 0.0;
		ArrayList<Double> layerListNum = new ArrayList<Double>();
		for(String domain:domainList){
//			System.out.println("網域名稱："+domain+"\t階層："+domain.split("\\.").length);
			layer_count = layer_count + domain.split("\\.").length;
			layerListNum.add((double)domain.split("\\.").length);	//將階層的數量放在double的陣列中
		}
		layer_avg = layer_count / (double)layerListNum.size();	//計算出此惡意軟體階層的平均數量
		ClassF14 classF14 = new ClassF14( layerListNum.size() , layer_avg , layerListNum);
		return classF14;
	}
	private static class ClassF14{
		private double size,mean;
		private ArrayList<Double> layerListNum;
		private ClassF14(double size , double mean , ArrayList<Double> layerListNum){
			this.size = size;
			this.mean = mean;
			this.layerListNum = layerListNum;
		}
	}
	double features15(ClassF14 f14Class){
		AlgorithmString algo = new AlgorithmString();
		double result = algo.variance(f14Class.layerListNum, f14Class.size, f14Class.mean);
		return result;
	}
	
	/**
	 * @throws IOException 
	 * @see 每個惡意軟體頂級域名個數亂度平均數
	 * @return ClassF16
	 * **/
	double features16(ArrayList<String> domainList) throws IOException{
		ArrayList<String> tdlsList = new ArrayList<String>();	//放的是已經有的ICANN擬定的top level domain name
		HashMap<String, Double> tdlsContainMap = new HashMap<String, Double>();
		double tdlsEntropyCount = 0.0;
		int tdlCount =0;
		
		AlgorithmString algo = new AlgorithmString();
		
		FileReader fr = new FileReader(this.tdls);
		BufferedReader br = new BufferedReader(fr);
		int i=0;
		while (br.ready()) {
			String str = br.readLine();
			if(i!=0){
				tdlsList.add(str);
			}
			i++;
		}
		fr.close();

		for(String domain:domainList){
			for(String l:domain.split("\\.")){
				if(tdlsList.contains(l.toUpperCase())){	//檢查此網域名稱中沒有沒有頂級域名，有這if的條件成立
					tdlCount++;	//累積計算
					String tdlUpperCase = l.toUpperCase();
					if(tdlsContainMap.containsKey(tdlUpperCase)){
						double t = tdlsContainMap.get(tdlUpperCase);
						t++;
						tdlsContainMap.put(tdlUpperCase, t);
					}else{
						tdlsContainMap.put(tdlUpperCase, 1.0);
					}
				}
			}
		}
//		System.out.println("頂級域名數量："+tdlCount+" ### "+tdlsContainMap);
		for(String key:tdlsContainMap.keySet()){
			double e = tdlsContainMap.get(key);
			double s = Double.valueOf(tdlCount);
			double entropy = algo.entropy(e, s);
			entropy = entropy * (-1);
			tdlsEntropyCount = tdlsEntropyCount + entropy;
//			System.out.println(entropy +"  @  "+ tdlsEntropyCount);
		}
//		System.out.println("return  ==> "+ tdlsEntropyCount);
		return tdlsEntropyCount;
	}
//	private static class ClassF16{
//		private double size,mean;
//		private ArrayList<Double> tdlsEntropyList;
//		private ClassF16(double size , double mean , ArrayList<Double> tdlsEntropyList){
//			this.size = size;
//			this.mean = mean;
//			this.tdlsEntropyList = tdlsEntropyList;
//		}
//	}

	/**
	 * @throws IOException 
	 * @see 每個惡意軟體頂級域名個數亂度變異數
	 * @return double
	 * **/
//	double features17(ClassF16 f16Class){
//		AlgorithmString algo = new AlgorithmString();
//		double result = algo.variance(f16Class.tdlsEntropyList, f16Class.size, f16Class.mean);
//		return result;
//	}
	
	
}
