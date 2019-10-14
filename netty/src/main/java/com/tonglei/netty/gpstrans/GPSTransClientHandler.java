package com.tonglei.netty.gpstrans;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class GPSTransClientHandler extends ChannelInboundHandlerAdapter {

	// private GPSTransClient client = new GPSTransClient();
	// 控制发送程序
	private static volatile boolean success;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive ...");
		success = true;
		System.out.println("send data to server ...");
		new Thread() {
			@Override
			public void run() {
				while (success) {
					if (!GPSTransConsts.NAME_MESS.isEmpty()) {
						StringBuilder sb = new StringBuilder();
						GPSTransConsts.NAME_MESS.values().forEach(value -> {
							sb.append(value);
						});
						ByteBuf resp = Unpooled.copiedBuffer(sb.toString(), CharsetUtil.UTF_8);
						ctx.writeAndFlush(resp);

						try {
							Thread.sleep(1000L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				System.err.println("client thread exit ...");
			};
		}.start();
		super.channelActive(ctx);

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("server disconnect ...");
		success = false;
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
