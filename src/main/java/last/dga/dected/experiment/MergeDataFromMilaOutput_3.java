package last.dga.dected.experiment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tool.function.ReadWrite;

public class MergeDataFromMilaOutput_3 {
/**
 * @define 計算mila的惡意軟體產生之網域名稱數量計算，作為過濾條件
 * 
 * **/
	String absolutePath = "experimentData/milaOutput/";	
	String output_path = "experimentData/milaOutput.malwareAndCount";	

	ReadWrite rw = new ReadWrite();
	
	public static void main(String[] args) throws IOException {
		MergeDataFromMilaOutput_3 mainClass = new MergeDataFromMilaOutput_3();
		ArrayList<String> mList = new ArrayList<String>();
		mList = mainClass.malwareList();
		int stopCount = 0;
		mainClass.rw.cleanFile(mainClass.output_path);
		for(String malware:mList){
			mainClass.readMalwareData(malware);
		}
	}

	int readMalwareData(String malware) throws IOException{
		String malwaePath = this.absolutePath+malware;
		int count = 0;
		BufferedReader br = new BufferedReader(new FileReader(malwaePath));
		while(br.ready()){
			String line = br.readLine();
			count++;
		}
//		System.out.println(malware+":"+count);
		this.rw.appendWrite(output_path, malware+":"+count);
		br.close();
		return count;
	}
	
	ArrayList<String> malwareList(){
		ArrayList<String> mList = new ArrayList<String>();
		mList = rw.readFileName(this.absolutePath);
//		System.out.println(mList);
		return mList;
	}
}
