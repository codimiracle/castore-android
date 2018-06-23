package cn.com.sise.ca.castore;

import com.codimiracle.libs.lumehttp.FormData;
import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpRequest;
import com.codimiracle.libs.lumehttp.HttpResponse;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import cn.com.sise.ca.castore.utils.JSONUtils;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private HttpClient httpClient;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testHttpClient() throws IOException {
        URL url = null;
        try {
            url = new URL("http://localhost/castore-server/?q=/User/SignIn");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        httpClient = new HttpClient();
        FormData formData = new FormData();
        formData.put("username", "codimiracle");
        formData.put("password", "ce9ec288e5");
        formData.put("user_sign_in", "user_sign_in");
        httpClient.open(url);
        HttpRequest request = httpClient.getHttpRequest();
        request.setRequestHeader("User-Agent", "CAstore/1.0");
        request.setFormData(formData);
        HttpResponse response = httpClient.getHttpResponse();
        System.out.println(response.getResponseBody());
        url = new URL("http://localhost/castore-server/?q=/User/");
        httpClient.open(url);
        request = httpClient.getHttpRequest();
        request.setRequestHeader("User-Agent", "CAstore/1.0");
        response = httpClient.getHttpResponse();
        String userinfo = response.getResponseBody();

    }

    @Test
    public void connection() {
        try {
            URL url = new URL("http://localhost/castore-server/?q=/User/SignIn");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);
            connection.setRequestProperty("User-Agent", "CAstore/1.0");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write("user_sign_in=user_sign_in&username=codimiracle&password=ce9ec288e5".getBytes());
            outputStream.flush();
            outputStream.close();
            System.out.println(connection.getResponseCode());
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            String cookie = connection.getHeaderField("set-cookie");
            cookieManager.put(url.toURI(), connection.getHeaderFields());
            connection.disconnect();

            connection = (HttpURLConnection) url.openConnection();

            cookieManager.get(url.toURI(), connection.getRequestProperties());
            connection.setRequestProperty("User-Agent", "CAstore/1.0");
            connection.connect();
            System.out.println(connection.getResponseCode());
            inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}