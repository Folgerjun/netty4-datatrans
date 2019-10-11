package com.tonglei.netty.gpstrans;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

/**
 * 启动监听
 * 
 * @author ffj
 *
 */
public class GPSTransClientConnectionListener implements ChannelFutureListener {

	private GPSTransClient client = new GPSTransClient();

	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		if (!future.isSuccess()) {
			final EventLoop loop = future.channel().eventLoop();
			loop.schedule(new Runnable() {
				@Override
				public void run() {
					System.err.println("client reconnecting ...");
					try {
						client.connect(GPSTransConsts.REMOTE_IP, Integer.parseInt(GPSTransConsts.REMOTE_PORT));
					} catch (NumberFormatException | InterruptedException e) {
						System.out.println("restart err...");
						e.printStackTrace();
					}
				}
			}, 5L, TimeUnit.SECONDS);
		} else {
			System.out.println("client connected ...");
		}

	}
}
