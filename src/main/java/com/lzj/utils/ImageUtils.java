package com.lzj.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public final static String WIDTH = "width";
    public final static String HEIGHT = "height";
    /**
     * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
     * @param imgdist 目标图片地址
     * @param rate 压缩比例
     */
    public static void reduceImg(InputStream inputStream, File dest, int targetWeight) {
        int widthdist = 0, heightdist = 0;
        FileOutputStream out = null;
        BufferedImage src = null;

            // 如果rate不为空说明是按比例压缩
        try {
            src = javax.imageio.ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取文件高度和宽度
            if (src.getWidth() <= targetWeight) {
                flushFile(src.getWidth(), src.getHeight(), dest, src);
                return;
        }
        float rate = calculationRate(src.getWidth(), targetWeight);
        widthdist = (int) (src.getWidth(null) * rate);
        heightdist = (int) (src.getHeight(null) * rate);
        flushFile(widthdist,heightdist,dest,src);


    }
    private static void flushFile(int weight, int height, File dest, BufferedImage src) {
        FileOutputStream out = null;
        try {
            BufferedImage tag = new BufferedImage((int) weight,
                    (int) height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(
                    src.getScaledInstance(weight, weight,
                            Image.SCALE_SMOOTH), 0, 0, null);

            out = new FileOutputStream(dest);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ComentUtils.closeStream(out);
        }


    }
    private static float calculationRate(float weight, int targetWeight) {
        return targetWeight/weight;
    }
}
