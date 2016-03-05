package tsxdk.io;

import common.utility.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Ulli Gerhard on 05.03.2016.
 */
public class NIOSocketIOImpl {
    private final SocketChannel socketChannel;
    private final Selector selector;
    private final SelectionKey selectionKey;

    public NIOSocketIOImpl(String host, int port) {
        {
            SocketChannel ssc = null;
            Selector stor = null;
            SelectionKey skey = null;
            try {
                ssc = SocketChannel.open();
                ssc.configureBlocking(false);
                ssc.connect(new InetSocketAddress(host, port));

                while (!ssc.finishConnect()) {
                    Thread.sleep(10);
                }

                stor = Selector.open();
                skey = ssc.register(stor, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        {
            socketChannel = ssc;
            selector = stor;
            selectionKey = skey;
        }
    }

    private void handleSelectedKeys() {
        try {
            selector.selectNow();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
        SelectionKey key;

//        System.out.println("SelectedKeys: " + selectedKeys.stream().);
        while (keyIterator.hasNext()) {
            key = keyIterator.next();
            keyIterator.remove();
            if (key.isReadable()) {
                try {
                    dbuf.clear();
                    int ret = socketChannel.read(new ByteBuffer[]{});
                    });
                    dbuf.flip();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (key.isWritable()) {
                try {
                    dbuf.clear();
                    dbuf.put(encoder.encode(CharBuffer.wrap(newData)));
                    dbuf.flip();
                    while (dbuf.hasRemaining()) {
                        try {
                            int count = socketChannel.write(dbuf);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
