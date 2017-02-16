package com.fanfte.datatransfer;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.fanfte.global.GlobalConstants;
import com.fanfte.tutils.ConnectUtils;
import com.fanfte.tutils.FileUtils;
import com.fanfte.tutils.HttpUtils;
import com.fanfte.tutils.JsonUtils;
import com.fanfte.tutils.SystemUtils;
import com.fanfte.tutils.WriteCSV;

public class DataTransfer {
	public volatile static Map<String, Boolean> isRunning = new HashMap<>();
	
	public static void main(String[] args) {
		
		startTransfer();
	}
	
	public static void startTransfer() {
		Properties pro = SystemUtils.getOptsPro();
		GlobalConstants.username = pro.getProperty("username", null);
		GlobalConstants.password = pro.getProperty("password", null);
		GlobalConstants.domain = pro.getProperty("domain", null);
		GlobalConstants.port = Integer.parseInt(pro.getProperty("port", null));
		GlobalConstants.serverName = pro.getProperty("serverName", null);
		xmppConnect();
	}
	
	public static void xmppReconnect() {
		ConnectUtils.getConnection().addConnectionListener(new ConnectionListener() {
			SimpleDateFormat sdf = new SimpleDateFormat();

			// 当网络断线了，重新连接上服务器触发的事件
			public void reconnectionSuccessful() {
				System.out.println("reconnectionSuccessful");
				JOptionPane.showMessageDialog(null, "server reconnectionSuccessful" + sdf.format(new Date()), "",
						JOptionPane.WARNING_MESSAGE);
			}

			// 重新连接失败
			public void reconnectionFailed(Exception arg0) {
				System.out.println("reconnectionFailed");
			}

			// 重新连接的动作正在进行的动作，里面的参数arg0是一个倒计时的数字，如果连接失败的次数增多，数字会越来越大，开始的时候是14
			public void reconnectingIn(int arg0) {
				System.out.println("time: " + arg0);
				System.out.println("reconnectingIn");
			}

			// 这里就是网络不正常断线激发的事件
			public void connectionClosedOnError(Exception arg0) {
				System.out.println("connectionClosedOnError");
				JOptionPane.showMessageDialog(null, "server connectionClosedOnError" + sdf.format(new Date()), "",
						JOptionPane.WARNING_MESSAGE);
				xmppConnect();
			}

			// 这里是正常关闭连接的事件
			public void connectionClosed() {
				System.out.println("connectionClosed");
			}
		});
		new Thread() {
			public void run() {
				while (true) {
					try {
						sleep(3 * 1000);
						if(!ConnectUtils.getConnection().isConnected()) {
							System.out.println("disconnected");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	public static void xmppConnect() {
    	
    	try {
			ConnectUtils.openConnection(GlobalConstants.serverName, GlobalConstants.port);;
		} catch (IllegalStateException e) {
			JOptionPane.showMessageDialog(null, "已经连上服务器", "", JOptionPane.WARNING_MESSAGE);
		} 
    	
    	try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
		boolean isLogin = ConnectUtils.loginUser(GlobalConstants.username, GlobalConstants.password);
		
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(isLogin) {
			JOptionPane.showMessageDialog(null, "连接服务器成功", "", JOptionPane.WARNING_MESSAGE);
			
			Roster roster = ConnectUtils.getConnection().getRoster();
			Collection<RosterEntry> entries = roster.getEntries();
			String s = null;
			for(RosterEntry entry: entries){
				System.out.println("---" + entry.getName());
				s = s + entry.getName() + " ";
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Roster rostera = ConnectUtils.getConnection().getRoster();
			
			// 添加花名册监听器，监听好友状态的改变
			rostera.addRosterListener(new RosterListener() {

				@Override
				public void entriesAdded(Collection<String> invites) {
					System.out.println("entriesAdded" + invites);
					for (Iterator iter = invites.iterator(); iter.hasNext();) {  
                        String fromUserJids = (String)iter.next();  
                        System.out.println(fromUserJids + "请求添加好友");  
                        ConnectUtils.addFriend(fromUserJids.substring(0, fromUserJids.indexOf("@")), "");
                  }   
				}

				@Override
				public void entriesUpdated(Collection<String> invites) {
					System.out.println("entriesUpdated" + invites);
					for (Iterator iter = invites.iterator(); iter.hasNext();) {  
                        String fromUserJids = (String)iter.next();  
                        System.out.println("同意添加的好友是："+fromUserJids);  
                      }  
				}

				@Override
				public void entriesDeleted(Collection<String> addresses) {
					System.out.println("entriesDeleted");
				}

				@Override
				public void presenceChanged(Presence presence) {
					System.out.println("presenceChanged - >" + presence.getStatus());
				}

			});
			
			//ConnectUtils.processOfflineMessage();
			
			ChatManager chatManager = ConnectUtils.getConnection().getChatManager();
			chatManager.addChatListener(new ChatManagerListener() {
				
				@Override
				public void chatCreated(Chat chat, boolean arg1) {
					chat.addMessageListener(new MessageListener() {
						
						@Override
						public void processMessage(Chat chat, Message message) {
							String body = message.getBody().toString();
							System.out.println("GlobalConstants.domain " + GlobalConstants.domain);
							System.out.println("body " + body);
							String s = "";
							String seqNo = null;
							String from = message.getFrom().substring(0,message.getFrom().indexOf("@"));
							WriteCSV.processBody(body, from);
							ConnectUtils.sendDeviceMessage( from + "@" + GlobalConstants.domain, "ack:packet ok");
							System.out.println("from " + from);
							//首次注册信息保存
							if(body.contains("MAC")) {
								try {
									JSONObject jo = new JSONObject(body);
									String mac =  jo.getString("MAC");
									String ip = jo.getString("IP");
									jo.put("FROM", from);
									//mac地址在文件中不存在则保存
									if(!FileUtils.isMacExists(mac)) {
										FileUtils.writeUserData(jo.toString());
										//对服务器发送注册请求
										s = HttpUtils.postDevice(JsonUtils.makeDeviceJson(mac, ip, ""));
										System.out.println("post result  " + s);
									} 
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							
							if(body.contains("startInfusion")) {
								
								String deviceData = FileUtils.readDeiceData(from);
								System.out.println();
								if(!deviceData.equals("") && deviceData != null) {
									try {
										JSONObject jo = new JSONObject(deviceData);
										String mac = jo.getString("MAC");
										String ip = jo.getString("IP");
										s = HttpUtils.postInfusionStart(JsonUtils.makeInfusionStartJson(mac, ip, "", ""));
										System.out.println("post result  " + s);
										seqNo = s;
										if(!seqNo.equals("") && seqNo != null) {
											FileUtils.writeProperties(mac, seqNo);
										}
										s = HttpUtils.heartBeats(JsonUtils.makeHeartBeatJson(mac, ip, seqNo, "3", "2", "1", 2));
										System.out.println("heartBeats result  " + s);
										//设置当前线程正在运行
										
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}
							
							if(body.contains("weight")) {
								double weight = 0.0;
								double speed = 0.0;
								int remainTime = (int) (weight / speed);
								
							}
							
							if(body.contains("deleteInfusion")) {
								
								String deviceData = FileUtils.readDeiceData(from);
								System.out.println();
								if(!deviceData.equals("") && deviceData != null) {
									try {
										JSONObject jo = new JSONObject(deviceData);
										String mac = jo.getString("MAC");
										String ip = jo.getString("IP");
										String seq = FileUtils.readValue(FileUtils.profilepath, mac);
										s = HttpUtils.delete(JsonUtils.makeEndJson(mac, "2", "2", seq));
										System.out.println("delete result  " + s);
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}
							
//							String SeqNo = s;
//							s = HttpUtils.heartBeats(JsonUtils.makeHeartBeatJson("12332", "192.168.1.1", "fe3f227f-f875-4d67-8a10-e05e3b574227", "3", "2", "1", 2));
//							System.out.println("heartBeats result  " + s);

						}
					});
				}
			});
		}
		xmppReconnect();
	}
}

