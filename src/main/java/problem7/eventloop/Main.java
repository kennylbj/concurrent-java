package problem7.eventloop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by kennylbj on 16/9/11.
 * NIOEventLooper is a event looper base on NIO.
 * We register a accept event on ServerSocketChannel in this looper so that
 * any connection to this channel will generate a Accept Event and be handle
 * in loop body.
 * We don't handle any other event because these event may need a high-level
 * logical such as Buffer manage. We will handle these events in further problems
 * such as implement a high performance Server and Client base on NIOEventLooper.
 */
public class Main {
    private static final String HOST = "localhost";
    private static final int PORT = 9988;

    public static void main(String[] args) throws IOException {
        NIOEventLooper looper = new NIOEventLooper();

        ServerSocketChannel acceptChannel = ServerSocketChannel.open();
        acceptChannel.configureBlocking(false);
        acceptChannel.socket().bind(new InetSocketAddress(HOST, PORT));
        //register accept event, so server can handle accept operation.
        looper.registerAccept(acceptChannel, new Server(acceptChannel));

        Runnable r = () -> {
            try {
                //wait for a minute to guarantee server is start.
                //fixme use CountDownLatch
                Thread.sleep(3000);
                Socket client = new Socket(HOST, PORT);
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));
                String result = bufferedReader.readLine();
                System.out.println("Server respond : " + result);
                //exit the loop after 3s
                Thread.sleep(3000);
                looper.exit();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("client failed");
            }
        };
        new Thread(r).start();

        looper.loop();
    }
}
