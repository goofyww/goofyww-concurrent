package cn.goofyww.concurrent.demo.http;

/**
 * 基于线程池技术实现的简单Web服务器
 */
public class SimpleHttpServerTest {

    public static void main(String[] args) throws Exception {
        SimpleHttpServer server = new SimpleHttpServer();
        server.setPort(8099);
        server.setBasePath("/Users/gf/IdeaProjects/goofyww-concurrent/src/main/resources/static");
        server.start();
    }
}
