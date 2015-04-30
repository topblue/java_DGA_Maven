package temp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class TestMarkov {
	
	ArrayList<String> domainList = new ArrayList<String>();
	
	public static void main(String[] args) throws JSONException {
		TestMarkov mainclass = new TestMarkov();
//		JSONArray jsarr = new JSONArray();
		ArrayList<String> jsarr = new ArrayList<String>();
		mainclass.settingDomainList();
//		jsarr.put(20, "塞多");
		for(String domain:mainclass.domainList){
			String[] dl = domain.split("");
			for(int i=0;i<(dl.length-1);i++){
				String element = dl[i]+dl[i+1];
				System.out.println(element+"="+jsarr);
				if(!jsarr.contains(element)){
					jsarr.add(element);
					System.out.println("\t"+jsarr.indexOf(element));
				}
				
//				jsarr.put(i, element);
				
			}
		}
		
	}
	void settingDomainList(){
		this.domainList.add("nologo1093.com");
		this.domainList.add("nologo0094.com");
		this.domainList.add("valeriemesedahl.com");
	}
}
