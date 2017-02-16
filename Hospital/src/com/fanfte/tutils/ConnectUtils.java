package com.fanfte.tutils;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Date;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smackx.packet.DelayInformation;

import com.fanfte.global.GlobalConstants;



/**
 * @author fanfte 2016
 * @version 1.0
 */
public class ConnectUtils {

	private final static String softVer = "1.00.160510";
	
	private static XMPPConnection connection = null;
	
	public static String server = GlobalConstants.serverName;
	public static int port = GlobalConstants.port;
	// 閹垫挸绱戞潻鐐村复
	public static void openConnection(String server, int port) {

		try {
//			ConnectionConfiguration configer = new ConnectionConfiguration("im.gototi.com",
//					5222);
			System.out.println("server " + server + " port " + port);
			ConnectionConfiguration configer = new ConnectionConfiguration(server,
					port);
			configer.setSecurityMode(SecurityMode.disabled);// 鐠佸墽鐤嗘稉绡竔sabled
			configer.setSASLAuthenticationEnabled(false);// 鐠佸墽鐤嗘稉绡篴lse
			configer.setCompressionEnabled(false);
			configer.setSelfSignedCertificateEnabled(false);
			configer.setReconnectionAllowed(true);  
			Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.accept_all);
			
			connection = new XMPPConnection(configer);
			connection.connect();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	// 閼惧嘲褰囬柧鐐复
	public static XMPPConnection getConnection() {
		if (connection == null) {

			openConnection(server, port);
		}
		return connection;
	}

	// 閸忔娊妫存潻鐐村复
	public static void closeConnection() {

		connection.disconnect();
		connection = null;

	}

	public boolean isClientConnected() {
		return connection != null && connection.isConnected();
	}

	public static boolean loginUser(String deviceName, String password) {
		if (getConnection() == null) {
			return false;
		}
		try {
			// Log into the server
			getConnection().login(deviceName, password);
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

	public static void closeConn() {
		if (connection != null) {
			connection.disconnect();

			connection = null;
		}
	}

	/**
	 * 濞ｈ濮炴總钘夊几 閺冪姴鍨庨敓锟�???
	 * 
	 * @param userName
	 * @param name
	 * @return
	 */
	public static boolean addUser(String userName, String name) {
		if (getConnection() == null)
			return false;
		try {
			getConnection().getRoster().createEntry(userName, name, null);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 濞ｈ濮炴總钘夊几 閺堝鍨庨敓锟�??
	 * 
	 * @param userName
	 * @param name
	 * @param groupName
	 * @return
	 */
	public static boolean addFriend(String userName,
			String groupName) {
		if (getConnection() == null)
			return false;
		try {
			Presence subscription = new Presence(Presence.Type.subscribed);
			subscription.setTo(userName);
			userName += "@" + getConnection().getServiceName();
			getConnection().sendPacket(subscription);
			getConnection().getRoster().createEntry(userName, userName,
					new String[] { groupName });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 濞夈劌锟�?
	 * 
	 * @param account
	 *            濞夈劌鍞界敮鎰娇
	 * @param password
	 *            濞夈劌鍞斤拷?锟藉棛锟�?
	 * @return 1閵嗕焦鏁為崘灞惧灇閿燂拷? 0閵嗕焦婀囬崝鈥虫珤濞屸剝婀佹潻鏂挎礀缂佹挻锟�?2閵嗕浇绻栨稉顏囧閸欏嘲鍑＄紒蹇撶摠閿燂拷?3閵嗕焦鏁為崘灞姐亼閿燂拷?
	 */
	public static String regist(String username) {
		if (getConnection() == null)
			return "0";
		Registration reg = new Registration();
		reg.setType(IQ.Type.SET);
		reg.setTo(getConnection().getServiceName());
		// 濞夈劍鍓版潻娆撳櫡createAccount濞夈劌鍞介弮璁圭礉閸欏倹鏆熼弰鐤穝erName閿涘奔绗夐弰鐥d閿涘本锟�?"@"閸撳秹娼伴惃鍕劥閸掑棴锟�???
		reg.setUsername(username);
		reg.setPassword(username);
		// 鏉╂瑨绔焌ddAttribute娑撳秷鍏樻稉铏光敄閿涘苯鎯侀崚娆忓毉闁挎瑱锟�??閿熻姤澧嶆禒銉ヤ粵娑擃亝鐖ｈ箛妤佹Цandroid閹靛锟�?閸掓稑缂撻惃鍕儌閿涗緤绱掗敍渚婄磼閿燂拷?
		reg.addAttribute("android", "geolo_createUser_android");
		PacketFilter filter = new AndFilter(new PacketIDFilter(
				reg.getPacketID()), new PacketTypeFilter(IQ.class));
		PacketCollector collector = getConnection().createPacketCollector(
				filter);
		getConnection().sendPacket(reg);
		IQ result = (IQ) collector.nextResult(SmackConfiguration
				.getPacketReplyTimeout());
		// Stop queuing results閸嬫粍顒涚拠閿嬬湴results閿涘牊妲搁崥锔藉灇閸旂喓娈戠紒鎾寸亯閿燂拷?
		collector.cancel();
		if (result == null) {
//			Log("regist", "No response from server.");
			System.out.println("No response from server.");
			return "閺堝秴濮熼崳銊︾梾閺堝鎼烽敓锟�??";
		} else if (result.getType() == IQ.Type.RESULT) {
//			Log.v("regist", "regist success.");
			System.out.println("regist success.");
			return "濞夈劌鍞介幋鎰";
		} else { // if (result.getType() == IQ.Type.ERROR)
			if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
//				Log.e("regist", "IQ.Type.ERROR: "
//						+ result.getError().toString());
				System.out.println("IQ.Type.ERROR: "
						+ result.getError().toString());
				return "瀹歌尙绮″▔銊ュ斀鏉╁洦顒濈拹锟�?锟�?";
			} else {
//				Log.e("regist", "IQ.Type.ERROR: "
//						+ result.getError().toString());
				System.out.println("IQ.Type.ERROR: "
						+ result.getError().toString());
				return "濞夈劌鍞芥径杈Е";
			}
		}
	}

	public static void sendDeviceMessage(String deviceName, String msg) {
		Chat newchat;
		if (msg.length() > 0) {
			try {
				ChatManager cm = ConnectUtils.getConnection()
						.getChatManager();
				// 閺傛澘缂撻懕濠傘亯
				newchat = cm.createChat(deviceName, null);
				newchat.sendMessage(msg);
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 閼惧嘲褰囬幍瀣簚閻ㄥ埓ac閸︽澘锟�?
	 * @return
	 */
	public static String getUserName() {
		String macSerial = null;
		String str = "";

		try {
			Process pp = Runtime.getRuntime().exec(
					"cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 閸樿崵鈹栭敓锟�??
					break;
				}
			}
			macSerial = macSerial.replace(":", "");
		} catch (IOException ex) {
			// 鐠у绨ｆ妯款吇閿燂拷?
			ex.printStackTrace();
		}
		return macSerial;
	}
	
	public static String generatePass(String string) {
		byte[] b = string.getBytes();
		for(int j=0;j < b.length; j++) {
			b[j] = (byte) ~b[j];
		}
		byte[] c = b;
		int sum = 0;
		for(int i=0;i < c.length;i++) {
			sum += c[i];
		}
		String pass = string + sum + "";
		return pass;
	}
	
	public static String getSdkVersion() {    
	    return ConnectUtils.softVer;    
	} 

	public static boolean removeUser(String userName) { 
        try { 
//            if (userName.contains("@")) { 
//                userName = userName.split("@")[0]; 
//            } 
        	Roster roster = ConnectUtils.getConnection().getRoster();
            RosterEntry entry = roster.getEntry("dyd32" + userName.toLowerCase() + "@" + GlobalConstants.domain); 
            roster.removeEntry(entry); 
 
            return true; 
        } catch (Exception e) { 
            e.printStackTrace(); 
            return false; 
        } 
    } 
	
	/**  
	 * 閸掔娀娅庤ぐ鎾冲閻€劍锟�?  
	 * @param connection  
	 * @return  
	 */    
	public static boolean deleteAccount(XMPPConnection connection)    {    
	    try {    
	        connection.getAccountManager().deleteAccount();    
	        return true;    
	    } catch (Exception e) {    
	        return false;    
	    }    
	}
	
	public static void processOfflineMessage() {
		if (getConnection() == null)
            return;
        
        PacketFilter messageFilter = new PacketTypeFilter(Message.class); 
        
        //创建一个message listener  
        PacketListener messageListener = new PacketListener() {  
        	public void processPacket(Packet packet) {  
        		String xml = packet.toXML();
        			System.out.println(xml);
//        		if(xml.contains("<offline/>")) {
//        			if(xml.contains("stamp")) {
//        				final Message message = (Message)packet;
//                      DelayInformation inf = (DelayInformation) message.getExtension("delay", "urn:xmpp:delay");
//                      Date sentDate;
//                      if (inf != null) {
//                          sentDate = inf.getStamp();
//                      } else {
//                          sentDate = new Date();
//                      }
//                      String body = message.getBody();
//                      String from = message.getFrom();
//                      System.out.println("date" + getDate(sentDate));
//                      WriteCSV.processBody(body, from, getDate(sentDate));
//        			}
//        		}
	        }  
        };  
        getConnection().addPacketListener(messageListener, messageFilter);  
	}
}
