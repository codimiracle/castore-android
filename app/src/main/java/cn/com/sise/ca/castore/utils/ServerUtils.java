package cn.com.sise.ca.castore.utils;

public class ServerUtils {
    public static final String HOST = "172.16.102.10";
    public static final String PORT = "";
    public static final String BASE_PATH = "castore-server";
    public static final String BASE_URL = "http://" + HOST + (PORT.isEmpty() ? "" : ":" + PORT) + "/" + BASE_PATH;
    public static final String BASE_QUERY_URL = BASE_URL + "/index.php?q=";

}
