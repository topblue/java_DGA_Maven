import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class Main_Evaluation {

	final String sourcePath = "experimentData/markov/testingData.result";
	
	public static void main(String[] args) throws IOException {
		Main_Evaluation mainClass = new Main_Evaluation();
		HashMap<String, Integer> map = mainClass.confusionMatrix();
		for(String key:map.keySet()){
			System.out.println(key +"="+ map.get(key));
		}

	}
	HashMap<String, Integer> confusionMatrix() throws IOException{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new FileReader(this.sourcePath));
		while(br.ready()){
			String line = br.readLine();
			String key =line.split(",")[1].split("-")[0] +">"+ line.split(",")[2].split("-")[0] ;
			if(map.containsKey(key)){
				int count = map.get(key);
				count++;
				map.put(key, count);
			}else{
				map.put(key, 1);
			}
		}
		return map;
	}

}
