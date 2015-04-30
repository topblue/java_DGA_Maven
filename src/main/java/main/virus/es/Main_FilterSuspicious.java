package main.virus.es;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main_FilterSuspicious {
	
	String file = "virusData/top-1m-onlyDomain.txt";

	public static void main(String[] args) {
		Main_FilterSuspicious mainFilter = new Main_FilterSuspicious();
		mainFilter.readfile();
	}
	
	void readfile(){
		try {
			int count = 0,unc=0;
			FileReader fr = new FileReader(this.file);
			BufferedReader br = new BufferedReader(fr);
//			ArrayList<String> uniqueList = new ArrayList<String>();
			Set<String> uniqueList = new HashSet<String>();
			while(br.ready()){
				String line = br.readLine();
				String domian = line;
				int i = line.split("/").length;
				if(i>1){
//					System.out.println(i+"\t"+line+"\t"+line.split("/")[0]);
					domian = line.split("/")[0];
				}
				
				uniqueList.add(domian);
				if(!uniqueList.contains(domian)){
					uniqueList.add(domian);
					unc++;
				}
				count++;
			}
			
			System.out.println("count:"+count);
			System.out.println("uniqueList:"+uniqueList.size());
			System.out.println("unc:"+unc);
			for(String domain:uniqueList){
				int i = domain.split("/").length;
				if(i>1){
					System.out.println(domain);
				}
				
			}
		} catch (IOException e) {	e.printStackTrace();	}
		
	}

}
