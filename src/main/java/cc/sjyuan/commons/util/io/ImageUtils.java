package cc.sjyuan.commons.util.io;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ImageUtils {
    public static Map<Integer, String> readfile(String filepath,
                                                Map<Integer, String> pathMap) throws Exception {
        if (pathMap == null) {
            pathMap = new HashMap<>();
        }
        File file = new File(filepath);
        if (!file.isDirectory()) {
            pathMap.put(pathMap.size(), file.getPath());
        } else if (file.isDirectory()) {
            String[] fileList = file.list();
            for (int i = 0; i < fileList.length; i++) {
                File readfile = new File(filepath + "/" + fileList[i]);
                if (!readfile.isDirectory()) {
                    pathMap.put(pathMap.size(), readfile.getPath());

                } else if (readfile.isDirectory()) {
                    readfile(filepath + "/" + fileList[i], pathMap);
                }
            }
        }
        return pathMap;
    }

    public static void compressImage(String srcImgPath, String outImgPath,
                                     int new_w, int new_h) {
        BufferedImage src = InputImage(srcImgPath);
        disposeImage(src, outImgPath, new_w, new_h);
    }

    public static void compressImage(String srcImgPath, String outImgPath,
                                     int maxLength) {
        BufferedImage src = InputImage(srcImgPath);
        if (null != src) {
            int old_w = src.getWidth();
            int old_h = src.getHeight();
            int new_w;
            int new_h;
            if (old_w > old_h) {
                new_w = maxLength;
                new_h = Math.round(old_h * ((float) maxLength / old_w));
            } else {
                new_w = Math.round(old_w * ((float) maxLength / old_h));
                new_h = maxLength;
            }
            disposeImage(src, outImgPath, new_w, new_h);
        }
    }

    public static void compressImageByWidth(String srcImgPath,
                                            String outImgPath, int new_w) {
        BufferedImage src = InputImage(srcImgPath);
        if (null != src) {
            int old_w = src.getWidth();
            int old_h = src.getHeight();
            int new_h;
            new_h = Math.round(old_h * ((float) new_w / old_w));
            disposeImage(src, outImgPath, new_w, new_h);
        }
    }

    public static void compressImageByHeight(String srcImgPath,
                                             String outImgPath, int new_h) {
        BufferedImage src = InputImage(srcImgPath);
        if (null != src) {
            int old_w = src.getWidth();
            int old_h = src.getHeight();
            int new_w;
            new_w = Math.round(old_w * ((float) new_h / old_h));
            disposeImage(src, outImgPath, new_w, new_h);
        }
    }

    private static BufferedImage InputImage(String srcImgPath) {
        BufferedImage srcImage = null;
        try {
            FileInputStream in = new FileInputStream(srcImgPath);
            srcImage = javax.imageio.ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return srcImage;
    }

    private synchronized static void disposeImage(BufferedImage src,
                                                  String outImgPath, int new_w, int new_h) {
        int old_w = src.getWidth();
        int old_h = src.getHeight();
        BufferedImage newImg = null;
        switch (src.getType()) {
            case 13:
                break;
            default:
                newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
                break;
        }
        Graphics2D g = newImg.createGraphics();
        g.drawImage(src, 0, 0, old_w, old_h, null);
        g.dispose();
        newImg.getGraphics().drawImage(
                src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0,
                null);
        outImage(outImgPath, newImg);
    }

    private static void outImage(String outImgPath, BufferedImage newImg) {
        File file = new File(outImgPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            ImageIO.write(newImg,
                    outImgPath.substring(outImgPath.lastIndexOf("") + 1),
                    new File(outImgPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
