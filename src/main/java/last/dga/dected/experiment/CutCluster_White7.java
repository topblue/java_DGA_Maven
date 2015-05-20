package last.dga.dected.experiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import tool.function.ReadWrite;
import weka.clusterers.SimpleKMeans;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class CutCluster_White7 {

	String sourcePath = "";
	String outputPath = "";
	int cluster = 0;
	
	ReadWrite rw = new ReadWrite();
	
	public void setPath(String sourcePath , String outputPath , int cluster ){
		this.sourcePath = sourcePath;
		this.outputPath = outputPath;
		this.cluster = cluster;
	}
	
	public void enterPoint() throws Exception {
//		CutCluster_White7 mainClass = new CutCluster_White7();
		ArrayList<String> domainList = this.setDomainList();
		ArrayList<String> domainToEntropyList = this.computeDNSToEntropy(domainList);
		int k_value = this.cluster;
		HashMap<Integer, ArrayList<String>>  clusterResult = this.clusterToEntropy(domainToEntropyList,k_value);
		this.outputClusterToFile(clusterResult);
		System.out.println("__CutCluster_White7__Finish___");
	}
	
	void outputClusterToFile(HashMap<Integer, ArrayList<String>>  clusterResult){
		for(int key:clusterResult.keySet()){
			String path = this.outputPath+key;
			this.rw.cleanFile(path);
			ArrayList<String> dnsList= clusterResult.get(key);
			for(String dns:dnsList){
				dns = dns.split(",")[0];
				this.rw.appendWrite(path, dns);
			}
		}
	}
	
	HashMap<Integer, ArrayList<String>> clusterToEntropy(ArrayList<String> dataList,int k) throws Exception {
		
		int domainAmount = dataList.size();	//網域名稱數量
		int vectorAmount = 2;	//欄位大小，name,domain
//		int k = 4;	//分群的數量

		Attribute Attribute1 = new Attribute("entropy");	//只有一個entropy

		FastVector fvWekaAttributes = new FastVector(2);	//有:name,domain
		fvWekaAttributes.addElement(Attribute1);

//		int domainAmount_sub = domainAmount ;
		FastVector fvNominalVal = new FastVector(domainAmount);
		for (int i = 0; i < dataList.size(); i++) {
			 fvNominalVal.addElement(dataList.get(i).split(",")[0]);
		}
		Attribute ClassAttribute = new Attribute("domainName", fvNominalVal);
		fvWekaAttributes.addElement(ClassAttribute);

		// Create an empty Instances set
		Instances isTrainingSet = new Instances("dga_result", fvWekaAttributes, domainAmount); 
		// Create the instance
		Instance iExample = new DenseInstance(vectorAmount); 
		
		//將值全部塞入
		for (int i = 0; i < dataList.size(); i++) {
			String[] AttributeList = dataList.get(i).split(",");
			String n = AttributeList[1];
			double value = Double.parseDouble( n );

			iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), value );

			iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), AttributeList[0] );
			isTrainingSet.add(iExample);
		}
		
		//使用K-means
		SimpleKMeans cluster = new SimpleKMeans();
		
		cluster.setPreserveInstancesOrder(true);
		cluster.setNumClusters(k);
		cluster.buildClusterer(isTrainingSet);

//		System.out.println(isTrainingSet);
		
		HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
		for(int i = 0;i<isTrainingSet.numInstances();i++){
			int key = cluster.clusterInstance(isTrainingSet.instance(i));
			String dns = dataList.get(i);
//			System.out.println(key+","+dns);
			if(map.containsKey(key)){
				ArrayList<String> arr = new ArrayList<String>();
				arr = map.get(key);
				arr.add(dns);
				map.put(key, arr);
			}else{
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(dns);
				map.put(key, arr);
			}
		}
		return map;
	}
	
	
	ArrayList<String> computeDNSToEntropy(ArrayList<String> domainList){
		ArrayList<String> domainToEntropyList = new ArrayList<String>();
		for(String dns:domainList){
			double entropy = this.givenStringEntropy(dns);
			String data = dns +","+ entropy;
			domainToEntropyList.add(data);
		}
		return domainToEntropyList;
	}
	
	double givenStringEntropy(String dns){
		double entropyTotal = 0.0;
		String[] wordList = dns.split("");
		HashMap<String, Double> wordMap = new HashMap<String, Double>();
		for(String word:wordList ){
			if(wordMap.containsKey(word)){
				double i = wordMap.get(word);
				i++;
				wordMap.put(word, i);
			}else{
				wordMap.put(word, 1.0);
			}
		}
		for(String key:wordMap.keySet()){
			double e=wordMap.get(key);
			double s = wordList.length;
			double entropy =e/s*Math.log10(e/s);
			entropy = entropy * (-1);
			entropyTotal = entropyTotal +entropy;
//			System.out.println(entropy +","+ entropyTotal);
			
		}
//		System.out.println(dns+"__結論："+ entropyTotal);
		return entropyTotal;
	}
	
	ArrayList<String> setDomainList() throws IOException{
		ArrayList<String> domainList = new ArrayList<String>();
		Set<String> dnsSet = new HashSet<String>();
		BufferedReader br = new BufferedReader(new FileReader(this.sourcePath));
		int i=0;
		while(br.ready()){
			String line = br.readLine();
			String dns = line.split("/")[0];
			dnsSet.add(dns);
//			if(i==50){break;} else {i++;}
		}
		br.close();
		domainList = new ArrayList<String>(dnsSet);
		return domainList;
	}

}
