package com.tonglei.netty.gpstrans;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class GPSTransServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// System.out.println("channelRead...");
		InetSocketAddress ipsocket = (InetSocketAddress) ctx.channel().remoteAddress();
		String clientIP = ipsocket.getAddress().getHostAddress();
		// System.out.println("clientIP: " + clientIP);
		int index = clientIP.lastIndexOf(".");
		String ipNum = clientIP.substring(index + 1);
		// GPSTransConsts.IP_NAME.put(ipNum, "gps5"); // 测试用
		// System.out.println("ipNum: " + ipNum);
		ByteBuf in = (ByteBuf) msg;
		String message = in.toString(CharsetUtil.UTF_8);
		System.out.println("client message: " + message);
		if (message.startsWith("$")) {
			message = message.replace("$", "#");
			if (!GPSTransConsts.IP_NAME.containsKey(ipNum)) {
				System.err.println(ipNum + "未配置!");
				return;
			}
			String name = GPSTransConsts.IP_NAME.get(ipNum);
			message = "#" + GPSTransConsts.IP_NAME.get(ipNum) + message + "\r";
			// System.out.println("message: " + message);
			GPSTransConsts.NAME_MESS.put(name, message);
		}
		// 释放
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive...: " + ctx.channel().remoteAddress());
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInactive...: " + ctx.channel().remoteAddress());
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
