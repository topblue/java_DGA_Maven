import last.dga.dected.experiment.CuttingTrainTest_8;


public class Main_ControllerClass {

	public static void main(String[] args) throws Exception {
//		1
		Main_CuttingFromCluster class1 = new Main_CuttingFromCluster();
		class1.main(new String[0]);
		
		CuttingTrainTest_8 class2 = new CuttingTrainTest_8();
		class2.main(new String[0]);
		
		Main_FileToMarkov class3 = new Main_FileToMarkov();
		class3.main(new String[0]);
		
		Main_Detection class4 = new Main_Detection();
		class4.main(new String[0]);
		
		Main_Evaluation class5 = new Main_Evaluation();
		class5.main(new String[0]);
		
	}

}
