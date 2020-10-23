package com.example.jdk18demo;

import java.io.*;

public class TryResourceTest {
    /**
     * 使用try-with-resources 改写示例一
     *
     * @param path
     *
     * @return
     *
     * @throws IOException
     */
    static String firstLineOfFileAutoClose(String path) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }

    static void copyAutoClose(String src, String dst) throws IOException {

        try (InputStream in = new FileInputStream(src);
             OutputStream os = new FileOutputStream(dst)) {
            byte[] buf = new byte[1000];
            int n;
            while ((n = in.read(buf)) >= 0) {
                os.write(buf, 0, n);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(1);
    }
}