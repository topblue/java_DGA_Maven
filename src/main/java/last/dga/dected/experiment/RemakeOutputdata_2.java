package last.dga.dected.experiment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import tool.function.ReadWrite;

public class RemakeOutputdata_2 {

	String absolutePath = "experimentData/mila/";
	String absolutePath_output = "experimentData/milaOutput/";	
	
	ArrayList<String> resultList = new ArrayList<String>();
	ArrayList<String> whitelist  = new ArrayList<String>();

	ReadWrite rw = new ReadWrite();
	
	public static void main(String[] args) throws IOException {
		RemakeOutputdata_2 mainClass = new RemakeOutputdata_2();
		
		ArrayList<String> malwarelist = mainClass.setMalwareList();
		mainClass.setWhiteList();
		for(String malware:malwarelist){
//			String malwareName = mainClass.absolutePath+malware;
			System.out.println("-----"+malware+"-----");
			mainClass.output(malware);
			
		}
		System.out.println("======\tRESULT\t======");
		
	}

	/**
	 * @define 
	 * ***/
	void output(String malware) throws IOException{
		String malwarePath = this.absolutePath+malware;
		String outputPath = this.absolutePath_output+malware;
		BufferedReader br = new BufferedReader(new FileReader(malwarePath));
		this.rw.cleanFile(outputPath);
		while(br.ready()){
			String line = br.readLine();
//			System.out.println("DNS:"+line+"\t:\t"+this.confirm(line));
			boolean b = this.confirm(line);
			if(!b){		//b回傳的是true表示，此網域名稱是在white list裡面有，所以false才是沒有在白名單中
				this.rw.appendWrite(outputPath, line);
			}
//			this.resultList.add(line);
		}
	}
	
	Boolean confirm(String dns){
		boolean result=false;
		for(String domain:this.whitelist){
			if(domain.equals(dns) || dns.contains("."+domain)){
//				System.out.println("1:"+dns+",2:"+domain);
				result=true;
				break;
			}
		}
		return result;
	}
	ArrayList<String> setMalwareList() throws IOException{
		ArrayList<String> malwareList = new ArrayList<String>();
		String thisPath = "experimentData/milaOutput.malware";
		BufferedReader br = new BufferedReader(new FileReader(thisPath));
		while(br.ready()){
			String line = br.readLine();
//			System.out.println(line);
			malwareList.add(line);
		}
		return malwareList;
	}
	void setWhiteList() throws IOException{
//		ArrayList<String> whitelist = new ArrayList<String>();
		String thisPath = "virusData/top-1m-onlyDomain.txt";
		BufferedReader br = new BufferedReader(new FileReader(thisPath));
		while(br.ready()){
			String line = br.readLine();
			line = line.split("/")[0];
//			System.out.println(line);
			this.whitelist.add(line);
		}
//		System.out.println(whitelist.size());
		HashSet h = new HashSet(this.whitelist);
		this.whitelist.clear();
		this.whitelist.addAll(h);
		System.out.println("whitelist size:"+whitelist.size());
//		return whitelist;
	}
}
