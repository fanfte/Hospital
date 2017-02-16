package com.fanfte.tutils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JsonUtils {
	
	public static String  makeDeviceJson(String mac, String ip, String seatNo) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("MAC", mac);
			obj.put("IP", ip);
			obj.put("ProductNo", "3");
			obj.put("SeatNo", seatNo);
			obj.put("SystemConfigTs", "2");
			obj.put("AppConfigTs", "1");
			obj.put("ExtendConfigTs", "1");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		System.out.println(obj.toString());
		return obj.toString();
	}
	
	public static String makeInfusionStartJson(String mac, String ip,String time, String tare) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		JSONObject obj = new JSONObject();
		try {
			obj.put("MAC", mac);
			obj.put("IP", ip);
			obj.put("Time", "3");
			obj.put("SeatNo", "");
			obj.put("Weight", "2");
			obj.put("Tare", "1");
			obj.put("StartTime", sdf.format(new Date()));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		System.out.println(obj.toString());
		return obj.toString();
	}
	
	public static String makeHeartBeatJson(String mac, String ip,String sequenceNo, String time,String speed, String remainingTime, int status) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		JSONObject obj = new JSONObject();
		try {
			obj.put("MAC", mac);
			obj.put("SequenceNo", sequenceNo);
			obj.put("SeatNo", "");
			obj.put("Time", time);
			obj.put("Speed", speed);
			obj.put("RemainingTime", remainingTime);
			obj.put("Status", status);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(obj.toString());
		return obj.toString();
	}
	
	public static String makeEndJson(String mac, String actualTime, String actualEndWeight,String sequenceNo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		JSONObject obj = new JSONObject();
		try {
			obj.put("MAC", mac);
			obj.put("ActualTime", actualTime);
			obj.put("ActualEndWeight", actualEndWeight);
			obj.put("SequenceNo", sequenceNo);
			obj.put("ActualEndTime", sdf.format(new Date()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(obj.toString());
		return obj.toString();
	}
}
