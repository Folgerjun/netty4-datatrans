package com.tonglei.netty.gpstrans;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GPSTransConsts {

	/**
	 * 默认服务端端口
	 */
	public static String SERVER_PORT = "8008";

	/**
	 * 默认转发ip
	 */
	public static String REMOTE_IP = "192.168.30.100";

	/**
	 * 默认转发端口
	 */
	public static String REMOTE_PORT = "12345";

	/**
	 * key：ip末尾数
	 * 
	 * value：别名
	 * 
	 * 如(111,gps1)
	 */
	public static Map<String, String> IP_NAME = new ConcurrentHashMap<String, String>();

	/**
	 * key：别名
	 * 
	 * value：bytebuf
	 * 
	 * 如(gps1,#gps1#GPGGA,035335.00,3105.56913094,N,12119.35765215,E,4,10,0.9,102.532,M,10.614,M,1.0,0000*44\r
	 * )
	 */
	public static Map<String, String> NAME_MESS = new ConcurrentHashMap<String, String>();

}
