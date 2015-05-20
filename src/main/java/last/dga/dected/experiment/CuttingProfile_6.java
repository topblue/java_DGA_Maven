package last.dga.dected.experiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import tool.function.ReadWrite;

public class CuttingProfile_6 {

	final String sourceDataPath = "experimentData/tagDataset/type.1234";
	final String outputDataPath = "experimentData/tagDataset/";

	ReadWrite rw = new ReadWrite();
	
	public static void main(String[] args) throws IOException {
		CuttingProfile_6 mainClass = new CuttingProfile_6();
		mainClass.halfDataSet();
		System.out.println("______ Finish ______");
	}
	
	/**
	 * @throws IOException 
	 * @Define training:50%,testing:50%
	 * @Define 單數:training,雙數:testing
	 * **/
	void halfDataSet() throws IOException{
		String trainingPath = this.outputDataPath +"50Traindga";
		String testingPath = this.outputDataPath +"50Testdga";
		this.rw.cleanFile(trainingPath);
		this.rw.cleanFile(testingPath);
		BufferedReader br = new BufferedReader(new FileReader(this.sourceDataPath));
		int index = 1;
		while(br.ready()){
			String line = br.readLine();
			if(index%2 == 1){
				String data = line.split(",")[0] +","+ line.split(",")[1] +","+ line.split(",")[3];
				this.rw.appendWrite(trainingPath, data);
			}else{
				String data = line.split(",")[0] +","+ line.split(",")[3];
				this.rw.appendWrite(testingPath, data);
			}
			index++;
		}
	}

}
