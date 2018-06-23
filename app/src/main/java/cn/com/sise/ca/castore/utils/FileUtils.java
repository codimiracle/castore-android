package cn.com.sise.ca.castore.utils;

import java.io.File;

public class FileUtils {
    public static String extension(File file) {
        String name = file.getName();
        return name.substring(name.lastIndexOf('.'));
    }
}
