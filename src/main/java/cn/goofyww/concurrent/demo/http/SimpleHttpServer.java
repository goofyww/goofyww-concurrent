package cn.goofyww.concurrent.demo.http;

import cn.goofyww.concurrent.demo.threadpool.DefaultThreadPool;
import cn.goofyww.concurrent.demo.threadpool.ThreadPool;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {

    // 处理 HttpRequest 的线程池
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<>(1);

    // SimpleHttpServer 的根路径
    static String basePath;

    static ServerSocket serverSocket;

    // 服务监听端口
    static int port = 8080;

    public static void setPort(int port) {
        if (port > 0) {
            SimpleHttpServer.port = port;
        }
    }

    public static void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        }
    }

    // 启动 SimpleHttpServer
    public static void start() throws Exception {
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
            // 接收一个客户端 Socket，生成一个 HttpRequestHandler，放入线程池执行
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }


}
