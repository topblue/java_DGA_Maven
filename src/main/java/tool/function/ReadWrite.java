package tool.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReadWrite {
	
	/**
	 * @see 給檔案路徑，就可以讀取檔案內容
	 * @return ArrayList<String>
	 * ***/
	ArrayList<String> readFile(String readFileName) throws Exception{
		ArrayList<String> arr = new ArrayList<String>();
		FileReader fr = new FileReader(readFileName);
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			String str = br.readLine();
			arr.add(str);
		}
		fr.close();
		return arr;
	}
	/**
	 * @see 連續寫入檔案
	 * **/
	public void appendWrite(String outputFilename,String data){
		try {
			FileWriter fw = new FileWriter(outputFilename, true);
			fw.write(data);
			fw.write('\r');
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @see 只要給檔案路徑就可以"清除檔案"的內容
	 * **/
	public void cleanFile(String outputFilename){
		try {
			FileWriter fw = new FileWriter(outputFilename);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @see 建立檔案檔案，並且如果已經有會刪除 
	 * **/
	public void createFolder(String folderName){
		File dir = new File(folderName);
		delAllFile(folderName);
		dir.delete(); // 刪除資料夾
		dir.mkdir();  // 建立資料夾
	}
	/**
	*刪除資料夾裡面的所有檔
	* @param path String資料夾路徑 如 c:/fqf
	*/
	private void delAllFile(String path) {
		File file =new File(path);
		if(!file.exists()) {
			return;
		}
		if(!file.isDirectory()) {
			return;
		}
		String[]tempList = file.list();
		File temp =null;
		for (int i =0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			}
			else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path+"/"+ tempList[i]);//先刪除資料夾裡面的檔
				delFolder(path+"/"+ tempList[i]);//再刪除空資料夾
			}
		}
	}
	/**
	* 刪除資料夾
	* @param filePathAndNameString 資料夾路徑及名稱 如c:/fqf
	* @param fileContentString
	* @return boolean
	*/
	private void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //刪除完裡面所有內容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); //刪除空資料夾
		}
		catch(Exception e) {
			System.out.println("刪除資料夾操作出錯");
			e.printStackTrace();
		}
	}
	
	/**
	 * @see 讀取檔案夾底下的檔案名稱
	 * @return ArrayList<String>
	 * **/
	public ArrayList<String> readFileName(String path){
		ArrayList<String> list = new ArrayList<String>();
		File folder1 = new File(path);
		for (String name:folder1.list()) {
			if(!name.split("\\.")[0].isEmpty()){	//去除隱藏檔案
				list.add(name);
			}
		}
		return list;
	}
	
	
}
