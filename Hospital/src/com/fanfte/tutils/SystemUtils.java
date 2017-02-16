package com.fanfte.tutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Properties;

import org.codehaus.jettison.json.JSONObject;


public class SystemUtils {
	
	private static Properties pro;
	public static final String		DATA_FILE = ".opts";
	
	private static void saveOpts(JSONObject obj) {
		String fileDir = System.getProperty("user.dir")  ;
		File file = new File(fileDir, DATA_FILE);
		FileOutputStream fos = null;
		try {
			 fos =new FileOutputStream(file);
			 Properties pro = new Properties();
			 Iterator it = obj.keys();  
	            while (it.hasNext()) {  
	            	 String key = (String) it.next();  
	                 String value = obj.getString(key); 
	            	pro.setProperty(key, value);
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
        	String file = System.getProperty("user.dir") + File.separator + DATA_FILE;
        	System.out.println("file " + file);
        	fis = new FileReader(file);
            pro = new Properties();  
            pro.load(fis);
            String username = pro.getProperty("username", null);
			String password = pro.getProperty("password", null);
			
			if(isEmpty(username) || isEmpty(password)) {
				return null;
			}
            return pro;
            
        } catch (Exception e) {  
           // System.out.println("鏂囦欢涓嶅瓨鍦�:"+ e.toString());  
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
	
	/**
	 * will trim the string
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
	    if (null == s)
	        return true;
	    if (s.length() == 0)
	        return true;
	    if (s.trim().length() == 0)
	        return true;
	    return false;
	}
}
