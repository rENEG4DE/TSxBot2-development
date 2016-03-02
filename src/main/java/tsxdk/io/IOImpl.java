package tsxdk.io;

import common.utility.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

public class IOImpl {
    // Direct byte buffer for reading
    private final ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);
    private final Charset charset = Charset.forName("UTF-8");
    private final CharsetEncoder encoder = charset.newEncoder();
    private final CharsetDecoder decoder = charset.newDecoder();

    private final SocketChannel socketChannel;
    private final Selector selector;
    private SelectionKey selectionKey;

    public IOImpl() {
        SocketChannel ssc = null;
        Selector stor = null;
        try {
            ssc = SocketChannel.open();
            ssc.configureBlocking(false);
            ssc.connect(new InetSocketAddress(Configuration.TSSERVER_HOST, Configuration.TSSERVER_PORT));
//            ServerSocket ss = ssc.socket();
//            final InetSocketAddress isa = new InetSocketAddress(Configuration.TSSERVER_HOST, Configuration.TSSERVER_PORT);
//            ssc.connect(isa);

            while (!ssc.finishConnect()) {
                Thread.sleep(100);
            }
            System.out.println("SocketChannel has been connected");
//            ssc.socket().bind(isa);
            stor = Selector.open();
            selectionKey = ssc.register(stor, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        socketChannel = ssc;
        selector = stor;
    }

    boolean doitonce = false;

    private void handleSelectedKeys() {
        System.out.println("Mark 1");
        try {
//            selector.selectNow();
            System.out.println("Selector: ");
            selector.selectNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Mark 2");

        System.out.println("interestOps READ: " + (selectionKey.interestOps() & SelectionKey.OP_READ));
        System.out.println("interestOps WRITE: " + (selectionKey.interestOps() & SelectionKey.OP_WRITE));
//        System.out.println("interestOps READ: " + (selectionKey.interestOps() & SelectionKey.OP_READ));
        System.out.println("selector: " + selector.selectedKeys().size());

        final Set<SelectionKey> selectedKeys = selector.selectedKeys();
        final Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
        SelectionKey key;
        System.out.println("Mark 3");

//        System.out.println("SelectedKeys: " + selectedKeys.stream().);
        while (keyIterator.hasNext()) {
            System.out.println("Mark 4");
            key = keyIterator.next();
            keyIterator.remove();
            if (key.isReadable()) {
                System.out.println("Mark 7");
                try {
                    dbuf.clear();
                    int ret = socketChannel.read(dbuf);
                    dbuf.flip();
                    System.out.println("Read returned: " + decoder.decode(dbuf));
                    System.out.println("Key@Read: " + key.interestOps());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (key.isWritable()) {
                System.out.println("Mark 8");
                try {
                    if (!doitonce) {
                        String newData = "login client_login_name=serveradmin client_login_password=ciqHJ9tF\n";

                        dbuf.clear();
                        dbuf.put(encoder.encode(CharBuffer.wrap(newData)));
                        dbuf.flip();
//                        final ByteBuffer toWrite = newData.toCharArray();
//                        toWrite.flip();
                        while (dbuf.hasRemaining()) {
                            try {
                                int count = socketChannel.write(dbuf);
                                System.out.println("# of bytes written: " + count);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (!doitonce) {
//                            System.out.println(selectionKey.interestOps() & SelectionKey.OP_READ);
                            key.interestOps(SelectionKey.OP_READ);
                        }
                        doitonce = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("keyIterator removed");
            if (!doitonce) {
                selectionKey.interestOps(SelectionKey.OP_WRITE);
            }
        }

    }

//    public static ByteBuffer str_to_bb(String msg) {
//        try {
//            return encoder.encode(CharBuffer.wrap(msg));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static String bb_to_str(ByteBuffer buffer) {
//        String data = "";
//        try {
//            int old_position = buffer.position();
//            data = decoder.decode(buffer).toString();
//            // reset buffer's position to its original so it is not altered:
//            buffer.position(old_position);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//        return data;
//    }

    public static void main(String[] args) {
        IOImpl thiz = new IOImpl();

        try {
            do {
//                thiz.query(Configuration.TSSERVER_HOST);
                thiz.handleSelectedKeys();
                Thread.sleep(100);
            } while (true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        for (int i = firstArg; i < args.length; i++) {
//            String host = args[i];
//            try {
//                query(host);
//            } catch (IOException x) {
//                System.err.println(host + ": " + x);
//            }
//        }
    }

}