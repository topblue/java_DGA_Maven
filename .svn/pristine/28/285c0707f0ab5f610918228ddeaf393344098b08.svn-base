package tool.function;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import temp.TestMarkov;

public class MarkovModelBigramDomainList {

	ArrayList<String> domainList = new ArrayList<String>();
/**
 * 第一次：Bi-gram的
 * @throws JSONException 
 * **/	
//	public static void main(String[] args) {
//		MarkovModelBigramDomainList mm = new MarkovModelBigramDomainList();
//		mm.markovModel();
//	}
	public JSONObject markovModel(ArrayList<String> domainList) throws JSONException {
		TestMarkov mainclass = new TestMarkov();
		ArrayList<String> uniqueList = new ArrayList<String>();
		JSONObject result = new JSONObject();
//		settingDomainList();	//假資料塞入
		this.domainList = domainList;
		for(String domain:this.domainList){
			String[] dl = domain.split("");
			for(int i=0;i<(dl.length-1);i++){
				String element = dl[i]+dl[i+1];
//				System.out.println(element+"="+uniqueList);
				if(!uniqueList.contains(element)){
					uniqueList.add(element);
				}
			}
		}
//		System.out.println(uniqueList);
		
//		JSONArray jsarr = new JSONArray(uniqueList);
//		System.out.println(jsarr.get(0));
//		for(int i=0;i<jsarr.length();i++){
//			if(jsarr.getString(i).equals("no")){
//				System.out.println("--n--o--："+i);
//			}
//		}
		
//		計算總數
		int[][] countBinaryArray = new int[uniqueList.size()][uniqueList.size()];
		for(String domain:this.domainList){
			String[] dl = domain.split("");
			for(int i=0;i<(dl.length-2);i++){
//				String element = dl[i]+dl[i+1];
				String row = dl[i] +dl[i+1];
				String colum = dl[i+1]+dl[i+2];
				try{
					int val = countBinaryArray[uniqueList.indexOf(row)][uniqueList.indexOf(colum)];
					countBinaryArray[uniqueList.indexOf(row)][uniqueList.indexOf(colum)] = (1+val);
				}catch(ArrayIndexOutOfBoundsException e){
					countBinaryArray[uniqueList.indexOf(row)][uniqueList.indexOf(colum)] = 1;
				}
			}
		}
		
//		計算Markov Model的機率值
		double[][] markovModel = new double[uniqueList.size()][uniqueList.size()];
        for (int i = 0; i < countBinaryArray.length; i++) {
        	int rowCount =0;
//        	JSONArray resultArr = new JSONArray();
            for (int j = 0; j < countBinaryArray[i].length; j++) {
            	rowCount = rowCount+countBinaryArray[i][j];
//            	System.out.print(countBinaryArray[i][j]+"\t");
            }
            for (int j = 0; j < countBinaryArray[i].length; j++) {
            	double row = countBinaryArray[i][j];
            	if(row >0){
            		String markovChain = uniqueList.get(i)+">"+uniqueList.get(j);
            		double markovChainWeigh = row/rowCount;
            		markovModel[i][j] = markovChainWeigh;
            		result.put(markovChain, markovChainWeigh);
            	}else{
            		markovModel[i][j] = row;
            	}
            }
        }
        
        /***
        for(int i=0 ; i<markovModel.length; i++ ){
        	for(int j=0 ; j< markovModel[i].length ; j++){
        		System.out.print(markovModel[i][j]+"\t");
        	}
        	System.out.println();
        }
        **/
//        System.out.println(result);
        
        /**
        System.out.println(result.getDouble("o->-f")+"\t"+result.getDouble("lo>og")+"\t"+result.getDouble("x4>4."));
        Iterator<String> keys = result.keys();
        System.out.println(result);
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            System.out.println(key+"==>"+result.getDouble(key));
            if ( result.get(key) instanceof JSONObject ) {
            	System.out.println(key+"\t"+result.get(key));
            	System.out.println(key);
            }
        }
        ***/
        return result;
	}
	
	void settingDomainList(){
		this.domainList.add("nologo1093.com");
		this.domainList.add("nologo0094.com");
		this.domainList.add("nologo0091.com");
		this.domainList.add("valeriemesedahl.com");
	}
}
