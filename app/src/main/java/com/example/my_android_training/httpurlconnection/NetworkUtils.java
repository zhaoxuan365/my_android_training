package com.example.my_android_training.httpurlconnection;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkUtils {

    private static final String TAG = "NetworkUtils";
    private static final String URL = "https://www.wanandroid.com/user/login";

    // 创建一个单线程池
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void sendPostRequest(String userName, String password, IResponse iResponse) {
        Log.i(TAG, "sendPostRequest");
        executorService.submit(() -> {
            HttpURLConnection connection = null;
            try {
                // 连接到URL
                URL url = new URL(URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);

                // 构建JSON请求体
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("username", userName);
                jsonObject.addProperty("password", password);
                String jsonObjectString = jsonObject.toString();
                Log.i(TAG, "jsonObjectString = " + jsonObjectString);

                // 发送POST请求
                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                os.writeBytes(jsonObjectString);
                os.flush();
                os.close();

                // 获取响应
                int responseCode = connection.getResponseCode();
                Log.d(TAG, "responseCode = " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 如果 result 是一个 JSON 对象，可以将其转换为一个特定的类

                    ResponseData responseData = getResponseData(connection);
                    Log.i(TAG, "responseData = " + responseData);
                    if (responseData != null && iResponse != null) {
                        iResponse.handleResponseData(responseData);
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException, e.getMessage = " + e.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    private static ResponseData getResponseData(HttpURLConnection connection) {
        Log.i(TAG, "getResponseData");
        try (BufferedReader in =
                     new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 解析返回的JSON字符串
            Gson gson = new Gson();
            return gson.fromJson(response.toString(), ResponseData.class);
        } catch (IOException e) {
            Log.e(TAG, "IOException, e.getMessage = " + e.getMessage());
        }
        return null;
    }
}
