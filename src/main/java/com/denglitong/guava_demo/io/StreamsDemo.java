package com.denglitong.guava_demo.io;

import com.google.common.io.*;

import java.io.*;
import java.util.Arrays;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/9
 */
public class StreamsDemo {

    public static void main(String[] args) throws IOException {
        // ① toByteArray
        File file = new File("README.md");
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = ByteStreams.toByteArray(inputStream);
        System.out.println(Arrays.toString(bytes));

        // ② toString(Readable)
        Readable readable = new FileReader(file);
        String content = CharStreams.toString(readable);
        System.out.println(content);

        // ③ copy(Readable, Appendable)
        Readable readable1 = new FileReader(file);
        Appendable appendable = new StringBuffer();
        long size = CharStreams.copy(readable1, appendable);
        System.out.println(appendable.toString());
        System.out.println(size);

        // ④ stream copyTo
        ByteSource byteSource = Files.asByteSource(file);
        File output = new File("README-2.md");
        ByteSink byteSink = Files.asByteSink(output, FileWriteMode.APPEND);
        byteSource.copyTo(byteSink);

        String filePath = "test/dir1/dir2/helloWorld.txt";
        File file1 = new File(filePath);
        Files.createParentDirs(file1);
        System.out.println(Files.getFileExtension(filePath));
        System.out.println(Files.getNameWithoutExtension(filePath));
        System.out.println(Files.simplifyPath(filePath));
        byteSource.copyTo(Files.asByteSink(file1, FileWriteMode.APPEND));
    }
}
