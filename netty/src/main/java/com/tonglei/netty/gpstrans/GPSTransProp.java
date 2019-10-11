package com.tonglei.netty.gpstrans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GPSTransProp {

	/**
	 * 读取对应配置文件
	 */
	public static void readProp() {

		Properties prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("config/gps.properties"));
			prop.load(fis);
		} catch (IOException e) {
			System.out.println("gps.properties read err!");
			e.printStackTrace();
		}
		String serverPORT = prop.getProperty("server_port");
		String remoteIP = prop.getProperty("remote_ip");
		String remotePORT = prop.getProperty("remote_port");
		System.out.println("SERVER_PORT: " + serverPORT);
		System.out.println("REMOTE_IP: " + remoteIP);
		System.out.println("REMOTE_PORT: " + remotePORT);
		GPSTransConsts.SERVER_PORT = serverPORT;
		GPSTransConsts.REMOTE_IP = remoteIP;
		GPSTransConsts.REMOTE_PORT = remotePORT;
		String ipNames = String.valueOf(prop.get("ip_name"));
		String[] ipNameArr = ipNames.split(",");
		for (String ipName : ipNameArr) {
			String[] ip_name = ipName.split("-");
			String ip = ip_name[0];
			String name = ip_name[1];
			System.out.println("ip:" + ip + " name:" + name);
			GPSTransConsts.IP_NAME.put(ip, name);
		}

	}

}
