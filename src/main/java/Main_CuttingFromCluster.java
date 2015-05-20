import last.dga.dected.experiment.CutCluster_Malware7;
import last.dga.dected.experiment.CutCluster_White7;


public class Main_CuttingFromCluster {

	public static void main(String[] args) throws Exception {
		
		CutCluster_White7 whiteListClusterResult = new CutCluster_White7();
		String wSourcePath = "virusData/top-1m-onlyDomain.txt";
		String wOutputPath = "experimentData/Dataset/alexa-";
		int wCluster = 1;
		whiteListClusterResult.setPath(wSourcePath, wOutputPath , wCluster);
		whiteListClusterResult.enterPoint();
		
		CutCluster_Malware7 malwareClusterResult = new CutCluster_Malware7();
		String mSourcePath = "experimentData/tagDataset/type.1234";
//		String mFeaturePath = "experimentData/tagDataset/50Traindga.17f";
		String mFeaturePath = "experimentData/tagDataset/type1234.16f";
		String mOutputPath = "experimentData/Dataset/malware-";
		int mCluster = 5;
		malwareClusterResult.setPath(mSourcePath, mFeaturePath, mOutputPath , mCluster);
		malwareClusterResult.enterPoint();

		System.out.println("__恭喜___");
	}

}
