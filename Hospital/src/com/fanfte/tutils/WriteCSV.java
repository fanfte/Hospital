package com.fanfte.tutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;


public class WriteCSV {
    public static final String GBK = "GBK";
    
    public static void writeNewFile(String path) {
    	
    	File file = new File(path);
    	try {
    		if  (!file.exists()) {
    		makeDir(file.getParentFile());
    		file.createNewFile();
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "gbk");
			BufferedWriter bw = new BufferedWriter(osw);
			String fileContent = "重量" 
					+ "," + "类型" + "," + "写入时间";
			bw.write(fileContent);
			bw.flush();
			osw.flush();
			fos.flush();
			bw.close();
    		} else {
    			System.out.println("文件存在");
    		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    } 
	public static void writeData(String path, List<String> data) {
		File csv = new File(path);
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    	try {
    		if  (csv.exists()) {
    			// 追记模式
    			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
				
				StringBuffer fileContent = new StringBuffer();
				for(int i = 0; i < data.size(); i ++ ) {
					fileContent.append(data.get(i));
					fileContent.append(",");
				}
				bw.newLine();
				String date = sdf.format(new Date()).toString();
				bw.write(fileContent.append(date).toString());
				bw.flush();
				bw.close();
    		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeData(String path, List<String> data,String date) {
		File csv = new File(path);
    	try {
    		if  (csv.exists()) {
    			// 追记模式
    			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
				
				StringBuffer fileContent = new StringBuffer();
				for(int i = 0; i < data.size(); i ++ ) {
					fileContent.append(data.get(i));
					fileContent.append(",");
				}
				bw.newLine();
				bw.write(fileContent.append(date).toString());
				bw.flush();
				bw.close();
    		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean clearData(String path) {
		boolean isDeleted = false;
		File csv = new File(path);
		if(csv.exists() && csv.isFile()) {
			return csv.delete();
		}else {
			return false;
		}
	} 
	
	public static void writeListData(String path, List<String> data) {
		File csv = new File(path);
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    	try {
    		if  (csv.exists()) {
    			// 追记模式
    			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
				
				StringBuffer fileContent = new StringBuffer();
				for(int i = 0; i < data.size(); i ++) {
					fileContent.append(data.get(i));
					fileContent.append(",");
				}
				bw.newLine();
				String date = sdf.format(new Date()).toString();
				bw.write(fileContent.append(date).toString());
				bw.flush();
				bw.close();
    		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeData(String path, String [] data) {
		File csv = new File(path);
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    	try {
    		if  (csv.exists()) {
    			// 追记模式
    			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
				
				StringBuffer fileContent = new StringBuffer();
				for(int i = 0; i < data.length; i ++ ) {
					fileContent.append(data[i]);
					fileContent.append(",");
				}
				bw.newLine();
				String date = sdf.format(new Date()).toString();
				bw.write(fileContent.append(date).toString());
				bw.flush();
				bw.close();
    		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addCloumn(List<String> pList, String filePath) throws IOException{
		  BufferedReader bufReader = new BufferedReader(new FileReader(filePath));
		  String lineStr = "";
		  int rowNumber = 0;
		  StringBuffer nContent = new StringBuffer();
		  while((lineStr = bufReader.readLine()) != null){
		   String addValue = "";
		   if(rowNumber < pList.size()){
		    addValue = pList.get(rowNumber);
		   }
		   if(lineStr.endsWith(",")){
		    nContent.append(lineStr).append("\""+addValue+"\"");
		   }else{
		    nContent.append(lineStr).append(",\""+addValue+"\"");
		   }
		   rowNumber++;
		   nContent.append("\r\n");
		  }
		  bufReader.close();
		  
		  FileOutputStream fileOs = new FileOutputStream(new File(filePath), false);
		  fileOs.write(nContent.toString().getBytes());
		  fileOs.close();
		 }
	
	public static void open_directory(String folder) {  
		try {  
            String[] cmd = new String[5];  
            cmd[0] = "cmd";  
            cmd[1] = "/c";  
            cmd[2] = "start";  
            cmd[3] = " ";  
            cmd[4] = folder;  
            Runtime.getRuntime().exec(cmd);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 
	
	public static boolean createFile(File file) throws IOException {  
        if(! file.exists()) {  
            makeDir(file.getParentFile());  
        }  
        return file.createNewFile();  
    }  
      
    /** 
     * Enhancement of java.io.File#mkdir() 
     * Create the given directory . If the parent folders don't exists, we will create them all. 
     * @see java.io.File#mkdir() 
     * @param dir the directory to be created 
     */  
    public static void makeDir(File dir) {  
        if(! dir.getParentFile().exists()) {  
            makeDir(dir.getParentFile());  
        }  
        dir.mkdir();  
    }  
    
    public static void processBody(String body,String from) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    	String s = "";
    	if(body.contains("weight")) {
    		String weight = body.substring(body.indexOf("="));
    		String path =  GlobalConstants.path + from + " " + GlobalConstants.type + ".csv";
    		List<String> datas = new ArrayList<String>();
			datas.add(weight);
			datas.add(GlobalConstants.type);
    		WriteCSV.writeNewFile(path);
    		WriteCSV.writeData(path, datas,sdf.format(new Date()));
    	} else {
			JOptionPane.showMessageDialog(null, sdf.format(new Date()).toString() + " " + from + " :  " + body, "", JOptionPane.WARNING_MESSAGE);
		}	
    }
}
