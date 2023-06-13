package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class EchoClientV2 {
    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static EchoClientV2 instance;

    public static void main(String[] args) throws IOException {
        start();
        sendMessage("Hello");
        sendMessage("world");
        stop();
    }

    public static EchoClientV2 start() {
        if(instance == null)
            instance = new EchoClientV2();
        return instance;
    }

    public static void stop() throws IOException {
        client.close();
        buffer = null;
    }

    private EchoClientV2() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 8080));
            buffer = ByteBuffer.allocate(256);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        String response = null;
        try {
            client.write(buffer);
            buffer.clear();
            client.read(buffer);
            response = new String(buffer.array()).trim();
            System.out.println("response=" + response);
            buffer.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}