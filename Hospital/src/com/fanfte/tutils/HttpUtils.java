package com.fanfte.tutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class HttpUtils {
	
	final static private String CONNECT_URL = "http://115.28.181.84";
	
	public static String postDevice(String device_msg) {
		URL url = null;
		HttpURLConnection connection = null;
		OutputStream out = null ;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer("");
		try {
			url = new URL(CONNECT_URL + "/device");

			connection = (HttpURLConnection)url.openConnection();

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			connection.setRequestProperty("Content-Type", "application/json");
			// connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("Accept", "application/json");
			connection.connect();
			// System.out.println(obj.toString());
			System.out.println("msg " + device_msg);
			out = connection.getOutputStream();
			out.write(device_msg.getBytes());
			out.flush();
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8"); // 中文
				sb.append(lines);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!sb.equals("") && !(sb == null)) {
			return sb.toString();
		} else {
			return null;
		}
	}
	
	public static String postInfusionStart(String start_msg) {
		URL url = null;
		HttpURLConnection connection = null;
		OutputStream out = null ;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer("");
		try {
			url = new URL(CONNECT_URL + "/infusion/start");

			connection = (HttpURLConnection)url.openConnection();

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			connection.setRequestProperty("Content-Type", "application/json");
			// connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("Accept", "application/json");
			connection.connect();
			// System.out.println(obj.toString());
			System.out.println("msg " + start_msg);
			out = connection.getOutputStream();
			out.write(start_msg.getBytes());
			out.flush();
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8"); // 中文
				sb.append(lines);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String sequenceNo = "";
		if(!sb.equals("") && !(sb == null)) {
			System.out.println(sb.toString());
				try {
					JSONObject obj = new JSONObject(sb.toString());
					JSONObject objData =  new JSONObject(obj.get("Data").toString());
					sequenceNo = objData.get("SequenceNo").toString();
					System.out.println("sequenceNo"  + sequenceNo);
				} catch (JSONException e) {
					if(sb.toString().contains("Data")) {
						String body = sb.toString();
						String msg = body.substring(body.indexOf("Message") + 10, body.lastIndexOf("}")-2);
						System.out.println(msg);
					}else {
						e.printStackTrace();
					}
				}
				if(!sequenceNo.equals("") && sequenceNo != null) {
					return sequenceNo;
				} else {
					return null;
				}
		} else {
			return null;
		}
	}
	
	public static String heartBeats(String heartBeatMsg) {
		URL url = null;
		HttpURLConnection connection = null;
		OutputStream out = null ;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer("");
		try {
			url = new URL(CONNECT_URL + "/infusion/Heartbeats");

			connection = (HttpURLConnection)url.openConnection();

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("PUT");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			connection.setRequestProperty("Content-Type", "application/json");
			// connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("Accept", "application/json");
			connection.connect();
			// System.out.println(obj.toString());
			System.out.println("msg " + heartBeatMsg);
			out = connection.getOutputStream();
			out.write(heartBeatMsg.getBytes());
			out.flush();
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8"); // 中文
				sb.append(lines);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!sb.equals("") && !(sb == null)) {
			return sb.toString();
		} else {
			return null;
		}
	}
	
	public static String delete(String deleteMsg) {
		URL url = null;
		HttpURLConnection connection = null;
		OutputStream out = null ;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer("");
		try {
			url = new URL(CONNECT_URL + "/infusion");
			
			connection = (HttpURLConnection)url.openConnection();
			
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("DELETE");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			
			connection.setRequestProperty("Content-Type", "application/json");
			// connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("Accept", "application/json");
			connection.connect();
			// System.out.println(obj.toString());
			System.out.println("msg " + deleteMsg);
			out = connection.getOutputStream();
			out.write(deleteMsg.getBytes());
			out.flush();
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8"); // 中文
				sb.append(lines);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!sb.equals("") && !(sb == null)) {
			return sb.toString();
		} else {
			return null;
		}
	}
}
