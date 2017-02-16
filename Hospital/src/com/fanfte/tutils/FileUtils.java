package com.fanfte.tutils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.chainsaw.Main;


/**
 * @author fanfte
 * 2016年10月24日
 */
/**
 * @author fanfte
 * 2016年10月24日
 */
/**
 * @author fanfte
 * 2016年10月24日
 */
/**
 * @author fanfte
 * 2016年10月24日
 */
/**
 * @author fanfte
 * 2016年10月24日
 */
/**
 * @author fanfte
 * 2016年10月24日
 */
/**
 * @author fanfte
 * 2016年10月24日
 */
/**
 * @author fanfte
 * 2016年10月24日
 */
/**
 * @author fanfte
 * 2016年10月24日
 */
/**
 * @author fanfte
 * 2016年10月24日
 */
/**
 * @author fanfte
 * 2016年10月24日
 */
public class FileUtils {
	public static final String	DATA_FILE = "user.opts";
	
	public static final String USER_SEQNO = "userSeq.properties";
	public static final String fileDir = System.getProperty("user.dir")  ;
	public static final String profilepath = fileDir + File.separator + USER_SEQNO;
	 private static Properties pro = new Properties(); 
	 
	 static {   
	        try {   
	        	pro.load(new FileInputStream(profilepath));   
	        } catch (FileNotFoundException e) {   
	            e.printStackTrace();   
	            System.exit(-1);   
	        } catch (IOException e) {          
	            System.exit(-1);   
	        }   
	    } 
	public static void main(String[] args) {
		writeProperties("aaa1av","111");
	}
	
	/**  
    * 更新（或插入）一对properties信息(主键及其键值)  
    * 如果该主键已经存在，更新该主键的值；  
    * 如果该主键不存在，则插件一对键值。  
    * @param keyname 键名  
    * @param keyvalue 键值  
    */   
    public static void writeProperties(String keyname,String keyvalue) {          
        try {   
        	
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。   
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。   
            OutputStream fos = new FileOutputStream(profilepath);
            pro.setProperty(keyname, keyvalue);   
            // 以适合使用 load 方法加载到 Properties 表中的格式，   
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流   
            pro.store(fos, "Update '" + keyname + "' value");   
        } catch (IOException e) {   
            System.err.println("属性文件更新错误");   
        } 
    }   
    
    /**  
     * 读取属性文件中相应键的值  
     * @param key  
     *            主键  
     * @return String  
     */   
     public static String getKeyValue(String key) {   
         return pro.getProperty(key);   
     }   
   
     /**  
     * 根据主键key读取主键的值value  
     * @param filePath 属性文件路径  
     * @param key 键名  
     */   
     public static String readValue(String filePath, String key) {   
         Properties props = new Properties();   
         try {   
             InputStream in = new BufferedInputStream(new FileInputStream(   
                     filePath));   
             props.load(in);   
             String value = props.getProperty(key);   
             System.out.println(key +"键的值是："+ value);   
             return value;   
         } catch (Exception e) {   
             e.printStackTrace();   
             return null;   
         }   
     }   
	
	public static void writeUserSeqData(String username, String value) {
		String fileDir = System.getProperty("user.dir")  ;
		File file = new File(fileDir, DATA_FILE);
		FileOutputStream fos = null;
		try {
			 fos =new FileOutputStream(file);
			 Properties pro = FileUtils.getOptsPro();
	         if(!pro.containsKey(username)) {
	        	 pro.put(username, value);
	         }else {
	        	 pro.setProperty(username, value);
	         }
			 pro.store(fos," ");  
		     fos.close(); 
		} catch (Exception e) {
			
		} finally {
			try {
				if (null != fos) fos.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	public static Properties getOptsPro(){  
		if (null != pro) return pro;
		Reader fis = null;
        try {  
        	String file = System.getProperty("user.dir") + File.separator + USER_SEQNO;
        	System.out.println("file " + file);
        	fis = new FileReader(file);
            pro = new Properties();  
            pro.load(fis);
            return pro;
        } catch (Exception e) {  
            return null;
        }   finally {
        	if (null !=fis) {
        		try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }
    }  
	
	public static void writeUserData(String str) {
		String fileDir = System.getProperty("user.dir")  ;
		File file = new File(fileDir, DATA_FILE);
		if(file.exists()) {
			try {
				BufferedWriter bw = new BufferedWriter(
						new FileWriter(file, true));
				bw.newLine();
				bw.write(str);
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				file.createNewFile();
				BufferedWriter bw = new BufferedWriter(
						new FileWriter(file, true));
				bw.write(str);
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 读文件判断是否有mac地址存在
	 * @return true or false
	 */
	public static boolean isMacExists(String mac) {
		String fileDir = System.getProperty("user.dir")  ;
		File file = new File(fileDir, DATA_FILE);
		
		FileReader reader;
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String str = null;
	           
            while((str = br.readLine()) != null) {
                 if(str.contains(mac)) {
                	 return true;
                 }
            }
            return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * @author fanfte
	 * 通过设备mac读取设备信息
	 * @param mac
	 * @return
	 * 2016年10月24日
	 */
	public static String readDeiceData(String mac) {
		String fileDir = System.getProperty("user.dir")  ;
		File file = new File(fileDir, DATA_FILE);
		String str = null;
		FileReader reader;
		try {
			reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
            while((str = br.readLine()) != null) {
                 if(str.contains(mac)) {
                	 return str;
                 }
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static void writeData(String path, List<String> data) {
		File csv = new File(path);
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		try {
			if (csv.exists()) {
				// 追记模式
				BufferedWriter bw = new BufferedWriter(
						new FileWriter(csv, true));

				StringBuffer fileContent = new StringBuffer();
				for (int i = 0; i < data.size(); i++) {
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
}
