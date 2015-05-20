package last.dga.dected.experiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tool.function.ReadWrite;

public class CuttingTrainTest_8 {

	final String sourcePath_absolute = "experimentData/Dataset/";
	final String outputPath_absolute = "experimentData/markov/";
	
	ReadWrite rw = new ReadWrite();
	
	public static void main(String[] args) throws IOException {
		CuttingTrainTest_8 mainClass = new CuttingTrainTest_8();
		ArrayList<String> fileList = mainClass.getFileList(mainClass.sourcePath_absolute);
		for(String file:fileList){
			mainClass.halfDataSet(file);
		}
		System.out.println("___CuttingFolder_8___ Finish ______");
		
	}
	
	/**
	 * @throws IOException 
	 * @Define training:50%,testing:50%
	 * @Define 單數:training,雙數:testing
	 * **/
	void halfDataSet(String filename) throws IOException{
		String sourcePath = this.sourcePath_absolute + filename;
		String trainingPath = this.outputPath_absolute + filename + ".training";
		String testingPath = this.outputPath_absolute + filename + ".testing";
		
		this.rw.cleanFile(trainingPath);
		this.rw.cleanFile(testingPath);
		
		BufferedReader br = new BufferedReader(new FileReader(sourcePath));
		int index = 1;
		while(br.ready()){
			String line = br.readLine();
			if(index%2 == 1){
				String data = line; // +","+ filename;
				this.rw.appendWrite(trainingPath, data);
			}else{
				String data = line; // +","+ filename;
				this.rw.appendWrite(testingPath, data);
			}
			index++;
		}
	}

	ArrayList<String> getFileList(String folderPath){
		ArrayList<String> list = new ArrayList<String>();
		list = this.rw.readFileName(folderPath);
		for(String p:list){
			System.out.println(p);
		}
		return list;
	}
}
