package problem7.eventloop;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by kennylbj on 16/9/11.
 * Simple Server that handle accept event only.
 */
public class Server implements EventHandler {
    private final ServerSocketChannel acceptChannel;

    public Server(ServerSocketChannel acceptChannel) {
        this.acceptChannel = acceptChannel;
    }

    @Override
    public void handleRead(SelectableChannel channel) {
        throw new UnsupportedOperationException("currently don't handle read event");
    }

    @Override
    public void handleWrite(SelectableChannel channel) {
        throw new UnsupportedOperationException("currently don't handle write event");
    }

    //Once there is a accept event, server will accept the socket and call onConnect(SocketChannel)
    @Override
    public void handleAccept(SelectableChannel channel) {
        System.out.println("handle accept on channel " + channel.toString());
        try {
            SocketChannel socketChannel = acceptChannel.accept();
            if (socketChannel != null) {
                socketChannel.configureBlocking(false);
                // Set the maximum possible send and receive buffers
                socketChannel.socket().setSendBufferSize(1024);
                socketChannel.socket().setReceiveBufferSize(1024);
                socketChannel.socket().setTcpNoDelay(true);
                onConnect(socketChannel);
            }
        } catch (IOException e) {
            throw new RuntimeException("failed to handle accept " + channel.toString());
        }
    }

    @Override
    public void handleConnect(SelectableChannel channel) {
        throw new UnsupportedOperationException("currently don't handle connect event");
    }

    @Override
    public void handleError(SelectableChannel channel) {
        throw new UnsupportedOperationException("currently don't handle error event");
    }

    //Do whatever you want when connected, in this case, we just simply respond a greeting string.
    //if the response body is not fully write to channel, we can register a Write event to NIOEventLooper with
    //a specify remaining data record.
    //In that situation, Buffer logical is needed to manage remaining data.
    private void onConnect(SocketChannel channel) {
        System.out.println("channel " + channel.toString() + " is connected");
        /*
        if (!looper.isWriteRegistered(channel)) {
            try {
                looper.registerWrite(channel, this);
            } catch (ClosedChannelException e) {
                handleError(channel);
            }
        }*/
        String respond = "hello world\n";
        ByteBuffer buffer = ByteBuffer.wrap(respond.getBytes(Charset.forName("UTF-8" )));
        try {
            //in most cases, channel is writable, if the data is not fully write, simple throw a Exception
            int writeLen = channel.write(buffer);
            Preconditions.checkState(writeLen == respond.getBytes().length, "no fully write");
            System.out.println("write buffer " + new String(buffer.array()));
        } catch (IOException e) {
            throw new RuntimeException("failed to write buffer " + buffer);
        }
    }

}
