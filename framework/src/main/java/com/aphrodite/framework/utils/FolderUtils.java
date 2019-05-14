package com.aphrodite.framework.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Description:
 * Name:         FolderUtils
 * Author:       zhangjingming
 * Date:         2016-07-14
 */

public class FolderUtils {
    /**
     * 删除文件夹内容
     *
     * @param filePath       文件夹路径
     * @param deleteThisPath 是否删除该文件夹
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) { // 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) { // 如果是文件，删除
                        file.delete();
                    } else { // 目录
                        if (file.listFiles().length == 0) { // 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除文件夹内容
     *
     * @param folderPath 文件夹路径
     * @param fileName   文件路径
     */
    public static void deleteOtherFile(String folderPath, String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            try {
                File file = new File(folderPath);
                if (file.isDirectory()) { // 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].getAbsolutePath().contains(fileName)) {
                            continue;
                        }
                        deleteOtherFile(files[i].getAbsolutePath(), fileName);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件夹大小
     *
     * @param file 文件句柄
     * @return 文件夹大小
     */
    public static long getFolderSize(File file) {
        if (null == file)
            return 0L;

        if (!file.isDirectory()) {
            return file.length();
        }

        long size = 0L;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                size += getFolderSize(fileList[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 转化文件大小为字符串
     *
     * @param size 文件大小
     * @return Byte、KB、MB、GB、TB等尺寸字符串
     */
    public static String formatSize(double size) {
        if (0 == size)
            return "0.0MB";

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }

        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getSDAvailableSize() {
        String path;
        if (PathUtils.isExternalStorageAvailable())
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        else
            path = Environment.getDataDirectory().getAbsolutePath();
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return formatSize(blockSize * availableBlocks);
    }

    /**
     * 解压文件
     *
     * @param zipFilename
     * @param outPath
     * @return
     */
    public static boolean unzipFolder(String zipFilename, String outPath) {
        if (!FileUtils.isFileExist(zipFilename) || TextUtils.isEmpty(outPath))
            return false;

        FileUtils.makeDirs(outPath);
        FileOutputStream fos = null;
        ZipInputStream zis = null;
        boolean ret = false;
        try {
            zis = new ZipInputStream(new FileInputStream(zipFilename));
            ZipEntry zipEntry;
            String zName = "";

            while ((zipEntry = zis.getNextEntry()) != null) {
                zName = zipEntry.getName();
                if (TextUtils.isEmpty(zName)) {
                    continue;
                }
                if (zipEntry.isDirectory()) {
                    FileUtils.makeDirs(outPath + zName);
                } else {
                    File file = new File(outPath + zName);
                    if (file.isFile() && file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    fos = new FileOutputStream(file);
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while ((len = zis.read(buffer)) != -1)
                        fos.write(buffer, 0, len);
                    fos.flush();
                }
            }
            ret = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(zis);
            IOUtils.closeQuietly(fos);
            return ret;
        }
    }

    /**
     * 复制文件
     *
     * @param fromFile 要复制的文件目录
     * @param toFile   要粘贴的文件目录
     * @return 是否复制成功
     */
    public static boolean copy(String fromFile, String toFile) {
        //要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        //如同判断SD卡是否存在或者文件是否存在
        //如果不存在则 return出去
        if (!root.exists()) {
            return false;
        }
        //如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();

        //目标目录
        File targetDir = new File(toFile);
        //创建目录
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        //遍历要复制该目录下的全部文件
        for (int i = 0; i < currentFiles.length; i++) {
            if (currentFiles[i].isDirectory())//如果当前项为子目录 进行递归
            {
                copy(currentFiles[i].getPath() + "/", toFile + currentFiles[i].getName() + "/");

            } else//如果当前项为文件则进行文件拷贝
            {
                CopySdcardFile(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
            }
        }
        return true;
    }

    /**
     * 文件拷贝
     * 要复制的目录下的所有非子目录(文件夹)文件拷贝
     *
     * @param fromFile
     * @param toFile
     * @return
     */
    public static boolean CopySdcardFile(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return true;

        } catch (Exception ex) {
            return false;
        }
    }

}
