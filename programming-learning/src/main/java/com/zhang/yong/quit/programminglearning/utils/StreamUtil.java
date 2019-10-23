package com.zhang.yong.quit.programminglearning.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhang yong
 * @email zhytwo@126.com
 * @date 20190102
 */
public class StreamUtil {

    /**
     * 统一关闭流对象
     * @param closeables 流对象集合
     */
    public static void close(Closeable... closeables) {
        if (closeables == null || closeables.length == 0) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从输入流写入到输出流
     * @param is 输入流
     * @param out 输出流
     * @throws IOException IO异常
     */
    public static void write(InputStream is, OutputStream out) throws IOException {
        if(null != is) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            out.flush();
        }
    }
}
