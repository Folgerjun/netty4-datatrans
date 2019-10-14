package com.tonglei.netty.gpstrans;

import java.time.Instant;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class GPSTransClient {

	public void connect(String host, int port) throws InterruptedException {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new GPSTransClientHandler());
				}
			});
			System.out.println("client connecting ..." + Instant.now().toString());
			ChannelFuture f = b.connect(host, port).addListener(future -> {
				if (future.isSuccess())
					System.out.println("client connected ...");
				else
					System.err.println("client connect fail ...");
			}).sync();
			f.channel().closeFuture().sync();

		} finally {
			workerGroup.shutdownGracefully();
			Thread.sleep(5000L);
			// 重连
			connect(host, port);
		}
	}

}
