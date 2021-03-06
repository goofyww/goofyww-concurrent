package cn.goofyww.concurrent.demo.http;

import java.io.*;
import java.net.Socket;

/**
 * Http请求处理者
 */
public class HttpRequestHandler implements Runnable {

    private Socket socket;

    public HttpRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String line = null;
        BufferedReader br = null;
        BufferedReader reader = null;
        PrintWriter out = null;
        InputStream in = null;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String header = reader.readLine();

            // 由相对路径计算出绝对路径
            String filePath = SimpleHttpServer.basePath + header.split(" ")[1];
            out = new PrintWriter(socket.getOutputStream());
            // 如果请求资源的后缀为 jpg 或者 png，则读取资源并输出
            if (filePath.endsWith("jpg") || filePath.endsWith("png") || filePath.endsWith("ico")) {
                in = new FileInputStream(filePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int i = 0;
                while ((i = in.read()) != -1) {
                    baos.write(i);
                }
                byte[] array = baos.toByteArray();
                out.println("HTTP/1.1 200 OK");
                out.println("Server: Molly");
                out.println("Content-Type: image/jpeg");
                out.println("Content-Length: " + array.length);
                out.println("");
                socket.getOutputStream().write(array, 0, array.length);
            } else {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                out = new PrintWriter(socket.getOutputStream());
                out.println("HTTP/1.1 200 OK");
                out.println("Server: Monlly");
                out.println("Content-Type: text/html; charset=UTF-8");
                out.println("");
                while ((line = br.readLine()) != null) {
                    out.println(line);
                }
            }
            out.flush();
        } catch (Exception ex) {
            out.println("HTTP/1.1 500");
            out.println("");
            out.flush();
        } finally {
            close(br, in, reader, out, socket);
        }
    }

    private static void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                try {
                    closeable.close();
                } catch (Exception e) {
                }
            }
        }
    }

}
