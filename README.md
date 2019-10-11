# netty4-datatrans
用 netty4 实现数据报文的接收/拆包/重组/转发

---

这是一个利用 netty 实现的数据中间层处理，我这根据实际需求写了服务端和客户端，即该脚本部署的机器同时作为 server 和 client。

可以进行对接收数据的拆包/逻辑重组/添加数据标识符等等 DIY 操作，再进行定向转发。

#### 客户端处理

- 添加了 Listener 启动时可监听判断 client 是否正常启动，即对应 server 端口是否启用监听
    +   若通道连通，正常连接进行数据传输
    +   若通道未连通，则调用 schedule 进行定时重连操作

**GPSTransClientConnectionListener.java**
```
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
```

- 同时若是启动成功但是运行一段时间后 server 端口关闭监听了，那也要进行重连处理，可以根据实际需求更改

**GPSTransClientHandler.java**
```
@Override
public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.err.println("server disconnect ...");
    success = false;
    // 使用过程中断线重连
    final EventLoop eventLoop = ctx.channel().eventLoop();
    eventLoop.schedule(new Runnable() {
        @Override
        public void run() {
            try {
                client.connect(GPSTransConsts.REMOTE_IP, Integer.parseInt(GPSTransConsts.REMOTE_PORT));
            } catch (Exception e) {
                System.out.println("restart err...");
                e.printStackTrace();
            }
        }
    }, 5L, TimeUnit.SECONDS);
    super.channelInactive(ctx);
}
```

#### 配置文件

为了方便配置的修改，可以把项目打成 jar 包，然后在同目录下新建一个 config 文件夹，把 gps.properties 丢进去，完事。