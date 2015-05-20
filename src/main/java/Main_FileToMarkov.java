import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import tool.function.MarkovModelBigramDomainList;
import tool.function.MarkovModelUnigramDomainList;
import tool.function.ReadWrite;


public class Main_FileToMarkov {

	final String sourcePath_absolute = "experimentData/markov/";
	final String outputPath = "experimentData/markov/markov.model";

	ReadWrite rw = new ReadWrite();
	
	public static void main(String[] args) throws IOException, JSONException{
		Main_FileToMarkov mainClass = new Main_FileToMarkov();
//		MarkovModelBigramDomainList morkovModel = new MarkovModelBigramDomainList();
		MarkovModelUnigramDomainList morkovModel = new MarkovModelUnigramDomainList();
		mainClass.rw.cleanFile(mainClass.outputPath);
		for(String name:mainClass.getTrainingFileName()){
//			System.out.println("-+--+_+"+name);
			ArrayList<String> domainList = mainClass.domainList(name);
			JSONObject js =  morkovModel.markovModel(domainList);
			String outputData = name+"@"+js;
			System.out.println(name+"@"+domainList.size());
			mainClass.rw.appendWrite(mainClass.outputPath, outputData);
		}
		
		System.out.println("__Main_FileToMarkov___Finish____");
		
	}
	
	
	ArrayList<String> domainList(String name) throws IOException{
		String path = sourcePath_absolute + name;
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		while(br.ready()){
			String line = br.readLine();
			list.add(line);
		}
		return list;
	}
	
	ArrayList<String> getTrainingFileName(){
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> totalPath = this.rw.readFileName(this.sourcePath_absolute);
		for(String file:totalPath){
			if(file.split("\\.")[1].equals("training")){
				list.add(file);
			}
		}
		return list;
	} 
	
}
