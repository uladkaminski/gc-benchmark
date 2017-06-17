package com.epam.jmpgc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Server {

    private int port;
    private ServerSocket server;
    private Socket socket;
    private DataOutputStream os;
    private DataInputStream is;
    private ReentrantLock lock = new ReentrantLock();

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = new ServerSocket(port);
        socket = server.accept();
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());

        while (true) {
            readMessage();
        }
    }

    private void readMessage() throws IOException {
        int count = is.readInt();
        byte[] bytes = null;
        for (int i = 0; i < count; i++) {
            int length = is.readInt();
            bytes = new byte[length];
            is.readFully(bytes);
            int index = i;
            startThread(bytes, index);
        }
    }

    private void startThread(byte[] bytes, int index) {
        new Thread(() -> calcSentence(bytes, index)).start();
    }

    private void calcSentence(byte[] bytes, int index) {
        WeakReference<String> str = new WeakReference<>(new String(bytes));
        int big = 0;
        int small = 0;
//        List<WeakReference<String>> collect = stream(str.get().split(" ")).map(String::intern).map(WeakReference::new).collect(toList());
        for (String word : str.get().split(" ")) {
            if (word.length() <= 4) {
                small++;
            } else {
                big++;
            }
        }
        try {
            lock.lock();
            os.writeInt(index);
            os.writeInt(big);
            os.writeInt(small);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(7890).start();
        System.out.println("Server started");
    }
}
