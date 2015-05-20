import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import main.virus.es.Main_characteristic;

import org.json.JSONException;

import tool.function.ElasticsearchFunction;
import tool.function.ReadWrite;


public class MainModelingDGA_ES {
// GIT test??
	final String  esIndex = "dga",esType = "CRIME",element = "fileName";
	final String featuresOutput = "virusData/featuresOutput.csv";
	public static void main(String[] args) throws JSONException, IOException {
		MainModelingDGA_ES mainClass = new MainModelingDGA_ES();
		ElasticsearchFunction esTool = new ElasticsearchFunction();
		Main_characteristic mainCharacter = new Main_characteristic();
		ReadWrite w = new ReadWrite();
		w.cleanFile(mainClass.featuresOutput);
//		ArrayList<String> malwareList= esTool.queryDistinctElement(mainClass.esIndex, mainClass.esType, mainClass.element);
		ArrayList<String> malwareList= mainClass.setMalwareList();
		String f_tital = "name,f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16,f17";
//		w.appendWrite(mainClass.featuresOutput, f_tital);
		for(String malware:malwareList){
//			String malware = "cryptolocker_9CBB128E8211A7CD00729C159815CB1C";
			System.out.println("MainModelingDGA main : "+malware);
			/**
			HashMap<String,Double> features = mainCharacter.characteristic(malware);
			String f = features.get("f1")+","+features.get("f2")+","+features.get("f3")+","+features.get("f4")+","+
					features.get("f5")+","+features.get("f6")+","+features.get("f7")+","+features.get("f8")+","+
					features.get("f9")+","+features.get("f10")+","+features.get("f11")+","+features.get("f12")+","+
					features.get("f13")+","+features.get("f14")+","+features.get("f15")+","+
					features.get("f16")+","+features.get("f17");
			System.out.println(f);
			
			f=malware+","+f;
			w.appendWrite(mainClass.featuresOutput, f);
			**/
		}

//		String fileName = "BIN_CitadelPacked_2012-05";
	}
	ArrayList<String> setMalwareList(){
		ArrayList<String> malwareList = new ArrayList<String>();
		malwareList.add("BIN_Alurewo_2502edca284bd8bf782a65123a22f9a6");
		malwareList.add("cryptolocker_9CBB128E8211A7CD00729C159815CB1C");
		malwareList.add("BIN_Drowor_worm_0f015bb8e2f93fd7076f8d178df2450d_2013-04");
		malwareList.add("BIN_Lader-dlGameoverZeus_12cfe1caa12991102d79a366d3aa79e9");
		malwareList.add("BIN_Ramnitpcap_2012-01");
		malwareList.add("BIN_sality_CEAF4D9E1F408299144E75D7F29C1810");
		malwareList.add("InvestigationExtraction-RSA_Sality");
		malwareList.add("Salityand_many_other_malwares_unknownMD5_fromVT");
		malwareList.add("BIN_torpigminiloader_011C1CA6030EE091CE7C20CD3AAECFA0");
		malwareList.add("BIN_torpigminiloader_C3366B6006ACC1F8DF875EAA114796F0");
		
		return malwareList;
	}

}
