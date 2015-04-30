package tool.function;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.management.AttributeList;

import java_cup.internal_error;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class KmeansF17 {

	ArrayList<String> dataList = new ArrayList<String>();
	public HashMap<Integer, ArrayList<String>> Kmeans() throws Exception {
		// TODO Auto-generated method stub
//		KmeansF17 mainclass = new KmeansF17();
		addToList();
//		System.out.println(mainclass.dataList.get(0));
//		System.out.println(mainclass.dataList.get(1));
		HashMap<Integer, ArrayList<String>> result = feastVector(dataList);
		return result;
	}
	
	void addToList() throws IOException{
		String filename = "virusData/featuresOutput.csv";
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			this.dataList.add(br.readLine());
		}
	}
	HashMap<Integer, ArrayList<String>> feastVector(ArrayList<String> dataList) throws Exception{
		
		int domainAmount = dataList.size();
		int vectorAmount = 18;	//有多少
		int groupSize = 5;	//群樹
		
		Attribute Attribute1 = new Attribute("f1");
		Attribute Attribute2 = new Attribute("f2");
		Attribute Attribute3 = new Attribute("f3");
		Attribute Attribute4 = new Attribute("f4");
		Attribute Attribute5 = new Attribute("f5");
		Attribute Attribute6 = new Attribute("f6");
		Attribute Attribute7 = new Attribute("f7");
		Attribute Attribute8 = new Attribute("f8");
		Attribute Attribute9 = new Attribute("f9");
		Attribute Attribute10 = new Attribute("f10");
		Attribute Attribute11 = new Attribute("f11");
		Attribute Attribute12 = new Attribute("f12");
		Attribute Attribute13 = new Attribute("f13");
		Attribute Attribute14 = new Attribute("f14");
		Attribute Attribute15 = new Attribute("f15");
		Attribute Attribute16 = new Attribute("f16");
		Attribute Attribute17 = new Attribute("f17");
		
		FastVector fvWekaAttributes = new FastVector(18);
		fvWekaAttributes.addElement(Attribute1);    
		fvWekaAttributes.addElement(Attribute2);    
		fvWekaAttributes.addElement(Attribute3); 
		fvWekaAttributes.addElement(Attribute4);    
		fvWekaAttributes.addElement(Attribute5);
		fvWekaAttributes.addElement(Attribute6);    
		fvWekaAttributes.addElement(Attribute7);
		fvWekaAttributes.addElement(Attribute8);
		fvWekaAttributes.addElement(Attribute9);
		fvWekaAttributes.addElement(Attribute10);
		fvWekaAttributes.addElement(Attribute11);
		fvWekaAttributes.addElement(Attribute12);
		fvWekaAttributes.addElement(Attribute13);
		fvWekaAttributes.addElement(Attribute14);
		fvWekaAttributes.addElement(Attribute15);
		fvWekaAttributes.addElement(Attribute16);
		fvWekaAttributes.addElement(Attribute17);
		
//		塞入類惡意軟體名稱
		int domainAmount_sub = domainAmount ;
		FastVector fvNominalVal = new FastVector(domainAmount_sub);
		for (int i = 0; i < dataList.size(); i++) {
			 fvNominalVal.addElement(dataList.get(i).split(",")[0]);
		}
		Attribute ClassAttribute = new Attribute("malwareName", fvNominalVal);
		fvWekaAttributes.addElement(ClassAttribute);
		
		// Create an empty Instances set
		Instances isTrainingSet = new Instances("dga_result", fvWekaAttributes, domainAmount_sub);  
		
		// Create the instance
		Instance iExample = new DenseInstance(vectorAmount); 
		
		//將值全部塞入
		for (int i = 1; i < dataList.size(); i++) {
			String[] AttributeList = dataList.get(i).split(",");
			for (int j = 1; j < AttributeList.length; j++) {
				String n ="";
				if((AttributeList[j]).equals("null")){
					n="0";
				}else{
					n=AttributeList[j];
				}
				double value = Double.parseDouble( n );
				iExample.setValue((Attribute)fvWekaAttributes.elementAt(j), value );
			}
			iExample.setValue((Attribute)fvWekaAttributes.elementAt(17), AttributeList[0] );
			isTrainingSet.add(iExample);
		}
		
		//使用K-means
		SimpleKMeans cluster = new SimpleKMeans();
		
		cluster.setPreserveInstancesOrder(true);
		cluster.setNumClusters(groupSize);
		cluster.buildClusterer(isTrainingSet);
		
		HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
		for(int i = 0;i<isTrainingSet.numInstances();i++){
			int key = cluster.clusterInstance(isTrainingSet.instance(i));
			ArrayList<String> arrlist = new ArrayList<String>();
//    	   System.out.println(dataList.get(i+1).split(",")[0]+":"+cluster.clusterInstance(isTrainingSet.instance(i)));
//	    	   System.out.println(cluster.clusterInstance(isTrainingSet.instance(i)));
    	   if(map.containsKey(key)){
    		   arrlist = map.get(key);
    		   arrlist.add(dataList.get(i+1).split(",")[0]);
    		   map.put(key, arrlist);
    	   }else {
    		   arrlist.add(dataList.get(i+1).split(",")[0]);
    		   map.put(key, arrlist);
    	   }
		}
		return map;
	}
}







