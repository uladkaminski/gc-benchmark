package com.epam.jmpgc;

import org.junit.Before;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ServerTest {

    private Socket socket;
    private DataOutputStream os;

    @Before
    public void init() throws IOException {

        socket = new Socket("localhost", 7890);
        os = new DataOutputStream(socket.getOutputStream());
    }

    @Test
    public void testServer() throws IOException {
        for (int i = 0; i < 1_000_000_000; i++) {
            writeMessage(i);
        }
    }

    private void writeMessage(int i) throws IOException {
        int count = i % 10; // 0 to 9
        os.writeInt(count);

        int length = 1000 + count; // 1000 to 1009
        for (int j = 0; j < count; j++) {
            os.writeInt(length);   // 1000 to 1009
            os.writeBytes(makeString(length)); // 1000 to 1009
        }
    }

    private String makeString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(i % 5 == 0 ? ' ' : 'x');
        }
        sb.append(length);
        return sb.toString();
    }

}