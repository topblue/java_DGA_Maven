package last.dga.dected.experiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tool.function.ReadWrite;


public class FilterMilaBlogPcap_1 {

	/**
	 * @define 網域名稱從mali開放資料
	 * **/
	
	String milaRowPath = "virusData/unzipFileTotal.dns";
	String selectMalwareListPath ="experimentData/malware.select";
	String absolutePath = "experimentData/mila/";

	ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
	ArrayList<String> malwareList = new ArrayList<String>();

	ReadWrite rw = new ReadWrite();
	
	public static void main(String[] args) throws Exception {
		FilterMilaBlogPcap_1 mainClass = new FilterMilaBlogPcap_1();
		mainClass.setData();	//是將所有mila的網域名稱放入二維陣列，dataList
		mainClass.printDataList();	 //列印出在mila中的惡意軟體產生之網域名稱
		mainClass.setMalwareList();	// 想要挑選的網域名稱，malwareList
		mainClass.filterMalware();	//挑選給訂的惡意軟體名稱，只要mila的開放流量中有這資料就會挑選出來
	}
	
	/**
	 * @define 依照malwareList挑選的惡意軟體在，所有dataList中的資料
	 * **/
	void filterMalware(){
		for(String malware:this.malwareList){
			String outputMilaFile = this.absolutePath+malware;	//output的檔案路徑
			rw.cleanFile(outputMilaFile);
			for(int i=0;i<this.dataList.size();i++){
				String filename = this.dataList.get(i).get(0).toString();
				//有沒有包含此惡意軟體名稱的pcap檔案，並且此list大小要超過1，因為陣列的第一個為子是檔案名稱
				if(filename.indexOf(malware)>=0 && this.dataList.get(i).size()>1){	
					for(int j=1;j<this.dataList.get(i).size();j++){
						rw.appendWrite(outputMilaFile, dataList.get(i).get(j));
					}
				}
			}
		}
		
	}
	
	
	/**
	 * @define 將所有的mila malware產生的網域名稱，放到二維陣列中
	 * */
	void setData() throws Exception{
		FileReader fr = new FileReader(this.milaRowPath);
		BufferedReader br = new BufferedReader(fr);
		int mIndex = 0;
		while(br.ready()){
			String line = br.readLine();
			if(line.indexOf(".pcap")>=0){
				String malwareName = "";
				if(line.split(".pcap").length==1){
					malwareName = line.split(".pcap")[0];
				}else{
					malwareName = line;
				}
				ArrayList<String> list = new ArrayList<String>();
//				System.out.println(line.split(".pcap").length+",軟體名稱："+line+"\tsplit後的資料："+line.split(".pcap")[0]);
				list.add(malwareName);
				int ii = mIndex;
				this.dataList.add(ii, list);
				mIndex++;
			}else{
				int inIndex = this.dataList.size()-1;
				ArrayList<String> list = new ArrayList<String>();
				list = this.dataList.get(inIndex);
				list.add(line);
				this.dataList.set(inIndex, list);
			}
		}
//		System.out.println(this.dataList);
	}
	/**
	 * @define 列印出mila malware產生的網域名稱在二維陣列中的
	 * */
	void printDataList() throws InterruptedException{
		String printPath = "experimentData/milaData.dns";
		rw.cleanFile(printPath);
		for( int i=0; i<this.dataList.size();i++){
			rw.appendWrite(printPath, this.dataList.get(i).get(0));
			for(int j=1;j<this.dataList.get(i).size();j++){
				rw.appendWrite(printPath, this.dataList.get(i).get(j));
			}
//			Thread.sleep(1000);
			rw.appendWrite(printPath, "==\t===\t===\t===");
			
		}
		
	}
	
	void setMalwareList() throws Exception{
		FileReader fr = new FileReader(this.selectMalwareListPath);
		BufferedReader br = new BufferedReader(fr);
		while(br.ready()){
			String line = br.readLine();
			this.malwareList.add(line);
		}
//		System.out.println(this.malwareList);
	}
	
}
