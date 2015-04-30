package main.virus.es;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import tool.function.AlgorithmString;
import tool.function.DomainStringFormat;
import tool.function.ElasticsearchFunction;
import tool.function.FeatureBeans;

public class Main_characteristic {

	ElasticsearchFunction es = new ElasticsearchFunction();
	FeatureBeans featurebeans = new FeatureBeans();
	final String  esIndex = "dga",esType = "CRIME";
	ArrayList<String> domainList = new ArrayList<String>();
	String tdls = "virusData/ICANN-TLDs.txt";

//	public static void main(String[] args) throws JSONException, IOException {
	public HashMap<String,Double> characteristic(String fileName) throws JSONException, IOException {
//		String fileName = "BIN_CitadelPacked_2012-05";
		Main_characteristic mainClass = new Main_characteristic();
		HashMap<String,Double> featuresMap = new HashMap<String,Double>();
		
		mainClass.setDomainFromES(fileName);	//將dommain name放到ArrayList
//		ArrayList<String> arr = mainClass.es.queryTotalData("dga", "CRIME");
//		ArrayList<String> malwareList = mainClass.es.queryDistinctElement("dga", "CRIME", "fileName");
//		mainClass.es.queryStringByJsonobject("dga", "CRIME", template_params);

		double f1 = mainClass.features1();
//		System.out.println("f1:"+f1);
		featuresMap.put("f1", f1);
		
		ClassF2 f2Class = mainClass.features2();
		double f2 = f2Class.mean;
		double f3 = mainClass.features3(f2Class);
//		System.out.println("f2:"+f2+"\t,\tf3:"+f3);
		featuresMap.put("f2", f2);
		featuresMap.put("f3", f3);

		ClassF4 f4Class = mainClass.features4();
		double f4 = f4Class.mean;
		double f5 = mainClass.features5(f4Class);
//		System.out.println("f4:"+f4+"\t\t,\tf5:"+f5);
		featuresMap.put("f4", f4);
		featuresMap.put("f5", f5);
		
		ClassF6 f6Class = mainClass.features6();
		double f6 = f6Class.mean;
		double f7 = mainClass.features7(f6Class);
//		System.out.println("f6:"+f6+"\t,\tf7:"+f7);
		featuresMap.put("f6", f6);
		featuresMap.put("f7", f7);
		
		ClassF8 f8Class = mainClass.features8();
		double f8 = f8Class.mean;
		double f9 = mainClass.features9(f8Class);
//		System.out.println("f8:"+f8+"\t,\tf9:"+f9);
		featuresMap.put("f8", f8);
		featuresMap.put("f9", f9);
		
		ClassF10 f10Class = mainClass.features10();
		double f10 = f10Class.mean;
		double f11 = mainClass.features11(f10Class);
//		System.out.println("f10:"+f10+"\t,\tf11:"+f11);
		featuresMap.put("f10", f10);
		featuresMap.put("f11", f11);
		
		ClassF12 f12Class = mainClass.features12();
		double f12 = f12Class.mean;
		double f13 = mainClass.features13(f12Class);
//		System.out.println("f12:"+f12+"\t,\tf13:"+f13);
		featuresMap.put("f12", f12);
		featuresMap.put("f13", f13);
		
		ClassF14 f14Class = mainClass.features14();
		double f14 = f14Class.mean;
		double f15 = mainClass.features15(f14Class);
//		System.out.println("f14:"+f14+"\t,\tf15:"+f15);
		featuresMap.put("f14", f14);
		featuresMap.put("f15", f15);
		
		ClassF16 f16Class = mainClass.features16();
		double f16 = f16Class.mean;
		double f17 = mainClass.features17(f16Class);
//		System.out.println("f16:"+f16+"\t,\tf17:"+f17);
		featuresMap.put("16", f16);
		featuresMap.put("f17", f17);
		
		
		
//		mainClass.queryIdToDelete();
		
		return featuresMap;
	}
	
	
	/**
	 * 將Eleasticsearch中的domain name放到 domaArrayList<String> domainListinList
	 * @param String malwareFilename
	 * **/
	void setDomainFromES(String malwareFilename){
		this.domainList = es.queryStringDomain(this.esIndex, this.esType, malwareFilename);
	}
	
	/**
	 * @param 惡意軟體名稱
	 * @see 每個惡意軟體的亂度
	 * @return double:亂度
	 * */
	double features1(){
		DomainStringFormat domainstringformat = new DomainStringFormat();
		AlgorithmString algo = new AlgorithmString();
		String mergeDomain = "";
//		ArrayList<String> list = es.queryStringDomain(this.esIndex, this.esType, fileName);
		for(String domain:this.domainList){
			mergeDomain = mergeDomain+domain;
		}
		double domainSize = new Double(mergeDomain.length()); 
//		System.out.println("domainSize:"+domainSize);
//		System.out.println(mergeDomain);
		
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
//		System.out.println("features1_result："+features1_result+"\tfeatures1_result*1："+features1_result*(-1));
		return features1_result*(-1);
	}
	
	/**
	 * @param 惡意軟體名稱
	 * @see 每個惡意軟體網域名稱亂度平均數
	 * 
	 * **/
	ClassF2 features2(){
		DomainStringFormat domainstringformat = new DomainStringFormat();
		AlgorithmString algo = new AlgorithmString();
//		String mergeDomain = "";
		double features2_total=0.0,features2_size =0.0;
//		ArrayList<String> list = es.queryStringDomain(this.esIndex, this.esType, fileName);
		ArrayList<Double> entropyList = new ArrayList<Double>();
		for(String domain:this.domainList){
//			domainstringformat.checkDomainSize(domain);
			double domainSize = new Double(domain.length()); 
			HashMap<String,Integer> map = domainstringformat.splitChart(domain);	//將字串皆成字元，並計算後放到HashMap之中
			Iterator iter = map.entrySet().iterator();
			double domain_entropy = 0.0;
			while (iter.hasNext()) {
			    Map.Entry entry = (Map.Entry) iter.next();
//			    String key = entry.getKey().toString();
			    double val = Double.parseDouble(entry.getValue().toString());
//			    System.out.println(val+"___"+domainSize);
			    double result = algo.entropy(val, domainSize);	//entropy
			    domain_entropy = algo.double_add(domain_entropy, result);
			}
			domain_entropy = domain_entropy * (-1);
			entropyList.add(domain_entropy);
			features2_size++;
			features2_total =algo.double_add(features2_total, domain_entropy);
		}
		double mean = (features2_total/features2_size);
//		System.out.println(mean);
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
	ClassF4 features4(){
		DomainStringFormat domainstringformat = new DomainStringFormat();
		AlgorithmString algo = new AlgorithmString();
		ArrayList<Double> list_strSize = new ArrayList<Double>();
		ArrayList<Double> entropyList = new ArrayList<Double>();
		double avg=0.0,entropy_total=0.0,result=0.0;
		for(String domain:this.domainList){
			int domainSize = domain.split("").length;	//每個domain name的長度
			list_strSize.add((double)domainSize);	//將domain name長度轉成double放到arraylist之中
		}
//		avg = algo.average_int(list_strSize);	//將網域名稱所有長度，除上此陣列的長度，求出平均數
		HashMap<Double,Double> map = domainstringformat.domainSize(list_strSize);	//key是字串長度，value是出現的次數
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
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
	ClassF6 features6(){
		DomainStringFormat domainstringformat = new DomainStringFormat();
		ArrayList<Double> englishLetterListRatio = new ArrayList<Double>();
		String english="[a-zA-Z]";
		double ratio_count=0.0,result=0.0;
//		String number="[0-9]";
		for(String domain:this.domainList){
//			System.out.print("english letter："+domain+"\t length:"+domain.length());
			double en_count =0.0;
			for(int i=0;i<domain.length();i++){
				String d = domain.split("")[i];
				if(d.matches(english)){
					en_count++;
				}
			}
			double ratio = en_count/domain.length();
//			System.out.println("\tenglish : "+en_count+"\tratio:"+ratio);
			ratio_count = ratio_count+ratio;
			englishLetterListRatio.add(ratio);
		}
		result = ratio_count/domainList.size();
		ClassF6 classF6 = new ClassF6(englishLetterListRatio.size(),result,englishLetterListRatio);
//		System.out.println(result);
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
		double result = algo.variance(f6Class.englishLetterListRatio, f6Class.size, f6Class.mean);
		return result;
	}
	
	/**
	 * @see 每個惡意軟體網域名稱數字比例的平均數
	 * @param null
	 * @return ClassF8
	 * **/
	ClassF8 features8(){
//		DomainStringFormat domainstringformat = new DomainStringFormat();
		ArrayList<Double> numverListRatio = new ArrayList<Double>();
		String number ="[0-9]";
		double ratio_number = 0.0 , result = 0.0;
		for(String domain:this.domainList){
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
//			System.out.println("網址："+domain+"\t字串長度："+domain.length()+"\t數值："+num_count+"\t比例："+ratio);
		}
		result = ratio_number/numverListRatio.size();	//將每個網域名稱數值比例加總當作分子，分母為網域名稱個數
//		System.out.println("比例總數："+ratio_number+"\t網域名稱比數："+numverListRatio.size()+"\t比例平均數："+result);
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
	ClassF10 features10(){
		ArrayList<Double> vowelSoundListRatio = new ArrayList<Double>();
		String en = "[a-zA-Z]";
		String vowel="[aeiouAEIOU]";
		double ratio_count=0.0,result=0.0;
		for(String domain:this.domainList){
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
	ClassF12 features12(){
		ArrayList<Double> consonantSoundListRatio = new ArrayList<Double>();
		String en = "[a-zA-Z]";
		String consonant="[^aeiouAEIOU]";
		double ratio_count=0.0,result=0.0;
		for(String domain:this.domainList){
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
	ClassF14 features14(){
		double layer_count = 0.0,layer_avg = 0.0;
		ArrayList<Double> layerListNum = new ArrayList<Double>();
		for(String domain:this.domainList){
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
	ClassF16 features16() throws IOException{
		ArrayList<String> tdlsList = new ArrayList<String>();	//放的是已經有的ICANN擬定的top level domain name
		ArrayList<Double> tdlsEntropyList = new ArrayList<Double>();
		double tdlsEntropyAvg = 0.0,tdlsEntropyCount = 0.0;
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
		
		for(String domain:this.domainList){
			double e =0.0 ;
			double s = domain.split("\\.").length;
//			System.out.println(domain+"\t"+s);
			for(String l:domain.split("\\.")){
				if(tdlsList.contains(l.toUpperCase())){
					e++;
				}
			}
			double entropy = algo.entropy(e, s);
			tdlsEntropyList.add(entropy);
			tdlsEntropyCount = tdlsEntropyCount + entropy;
		}
		tdlsEntropyAvg  = tdlsEntropyCount / tdlsEntropyList.size();
		tdlsEntropyAvg = tdlsEntropyAvg*(-1);
		ClassF16 classF16 = new ClassF16(tdlsEntropyList.size(),tdlsEntropyAvg,tdlsEntropyList);
		return classF16;
	}
	private static class ClassF16{
		private double size,mean;
		private ArrayList<Double> tdlsEntropyList;
		private ClassF16(double size , double mean , ArrayList<Double> tdlsEntropyList){
			this.size = size;
			this.mean = mean;
			this.tdlsEntropyList = tdlsEntropyList;
		}
	}

	/**
	 * @throws IOException 
	 * @see 每個惡意軟體頂級域名個數亂度變異數
	 * @return double
	 * **/
	double features17(ClassF16 f16Class){
		AlgorithmString algo = new AlgorithmString();
		double result = algo.variance(f16Class.tdlsEntropyList, f16Class.size, f16Class.mean);
		return result;
	}
	
	void queryIdToDelete(){
		String str = "nologo1093.com.hsd1.va.comcast.net";
		String index = "dga" ; String type = "CRIME";
		ArrayList<String> arr = es.queryStringByJsonobject(index, type, str);
		for(String data:arr){
//			es.deleteES(index, type, data);
			System.out.println(data);
		}
		
	}
	
}
