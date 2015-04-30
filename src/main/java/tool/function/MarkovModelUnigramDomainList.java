package tool.function;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import temp.TestMarkov;

public class MarkovModelUnigramDomainList {

	ArrayList<String> domainList = new ArrayList<String>();
/**
 * 第一次：uni-gram的
 * @throws JSONException 
 * **/
	public JSONObject markovModel(ArrayList<String> domainList) throws JSONException {
		TestMarkov mainclass = new TestMarkov();
		ArrayList<String> uniqueList = new ArrayList<String>();
		JSONObject result = new JSONObject();
//		settingDomainList();	//假資料塞入
		this.domainList = domainList;
		for(String domain:this.domainList){
			String[] dl = domain.split("");
			for(int i=0;i<(dl.length-1);i++){
				String element = dl[i];
//				System.out.println(element+"="+uniqueList);
				if(!uniqueList.contains(element)){
					uniqueList.add(element);
				}
			}
		}
		
//		計算總數
		int[][] countBinaryArray = new int[uniqueList.size()][uniqueList.size()];
		for(String domain:this.domainList){
			String[] dl = domain.split("");
			for(int i=0;i<(dl.length-1);i++){
//				String element = dl[i]+dl[i+1];
				String row = dl[i];
				String colum = dl[i+1];
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
        
        return result;
	}
	
}
