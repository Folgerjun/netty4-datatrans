package com.tonglei.netty;

import com.tonglei.netty.gpstrans.GPSTransClient;
import com.tonglei.netty.gpstrans.GPSTransConsts;
import com.tonglei.netty.gpstrans.GPSTransProp;
import com.tonglei.netty.gpstrans.GPSTransServer;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		// 读取加载配置信息
		GPSTransProp.readProp();
		// 启动客户端转发数据
		new Thread() {
			@Override
			public void run() {
				try {
					new GPSTransClient().connect(GPSTransConsts.REMOTE_IP,
							Integer.parseInt(GPSTransConsts.REMOTE_PORT));
				} catch (NumberFormatException | InterruptedException e) {
					System.out.println("client start err!");
					e.printStackTrace();
				}
			}
		}.start();
		// 启动服务端接收数据
		try {
			new GPSTransServer().run(Integer.parseInt(GPSTransConsts.SERVER_PORT));
		} catch (NumberFormatException | InterruptedException e1) {
			System.err.println("server start err!");
			e1.printStackTrace();
		}

	}
}
