package com.example.my_android_training.broadcast;

public class BroadCastUtils {

    // 清单文件中生命广播权限
//    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
//            package="com.example.appa">
//
//    <uses-permission android:name="com.example.CUSTOM_PERMISSION" />
//
//    <application>
//        <!-- 其他配置 -->
//    </application>
//</manifest>

//    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
//            package="com.example.appb">
//
//    <permission android:name="com.example.CUSTOM_PERMISSION"
//    android:protectionLevel="signature" />
//    <uses-permission android:name="com.example.CUSTOM_PERMISSION" />
//
//    <application>
//        <!-- 其他配置 -->
//    </application>
//</manifest>

//    Intent intent = new Intent("com.example.ACTION_FETCH_DATA");
//intent.setPackage("com.example.appb"); // 指定 B 应用包名
//    sendBroadcast(intent);

//    <receiver android:name=".FetchDataReceiver">
//    <intent-filter>
//        <action android:name="com.example.ACTION_FETCH_DATA" />
//    </intent-filter>
//</receiver>

//    public class FetchDataReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if ("com.example.ACTION_FETCH_DATA".equals(intent.getAction())) {
//                // 执行获取数据操作
//                String data = fetchData(); // 获取数据的逻辑
//
//                // 将数据通过广播返回给 A 应用
//                Intent responseIntent = new Intent("com.example.ACTION_DATA_RESPONSE");
//                responseIntent.putExtra("data", data);
//                responseIntent.setPackage("com.example.appa"); // 指定 A 应用包名
//                context.sendBroadcast(responseIntent);
//            }
//        }
//
//        private String fetchData() {
//            // 模拟获取数据
//            return "Hello from App B!";
//        }
//    }

//    <receiver android:name=".DataResponseReceiver">
//    <intent-filter>
//        <action android:name="com.example.ACTION_DATA_RESPONSE" />
//    </intent-filter>
//</receiver>


//    public class DataResponseReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if ("com.example.ACTION_DATA_RESPONSE".equals(intent.getAction())) {
//                String data = intent.getStringExtra("data");
//                // 处理接收到的数据
//                Log.d("DataResponseReceiver", "Received data: " + data);
//            }
//        }
//    }

    // A应用动态注册广播
//    private BroadcastReceiver dataResponseReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if ("com.example.ACTION_DATA_RESPONSE".equals(intent.getAction())) {
//                String data = intent.getStringExtra("data");
//                Log.d("DataResponseReceiver", "Received data: " + data);
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // 动态注册接收器
//        IntentFilter filter = new IntentFilter("com.example.ACTION_DATA_RESPONSE");
//        registerReceiver(dataResponseReceiver, filter);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // 注销广播接收器
//        unregisterReceiver(dataResponseReceiver);
//    }

    // B应用动态注册广播
//    private BroadcastReceiver fetchRequestReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if ("com.example.ACTION_FETCH_DATA".equals(intent.getAction())) {
//                // 模拟数据获取
//                String data = fetchData();
//
//                // 返回数据广播
//                Intent responseIntent = new Intent("com.example.ACTION_DATA_RESPONSE");
//                responseIntent.putExtra("data", data);
//                responseIntent.setPackage("com.example.appa"); // 指定 A 应用
//                context.sendBroadcast(responseIntent);
//            }
//        }
//
//        private String fetchData() {
//            // 模拟获取数据
//            return "Hello from App B!";
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // 动态注册接收器
//        IntentFilter filter = new IntentFilter("com.example.ACTION_FETCH_DATA");
//        registerReceiver(fetchRequestReceiver, filter);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // 注销广播接收器
//        unregisterReceiver(fetchRequestReceiver);
//    }

// 在 A 应用中发送广播时指定权限：
//    Intent intent = new Intent("com.example.ACTION_FETCH_DATA");
//    sendBroadcast(intent, "com.example.CUSTOM_PERMISSION");
    // B 应用的 AndroidManifest.xml 必须声明该权限：
    // 这样，只有声明了 com.example.CUSTOM_PERMISSION 权限的应用才能接收广播。
//    <uses-permission android:name="com.example.CUSTOM_PERMISSION" />
    // 当广播接收器被触发时，接收方可以通过 Context.checkCallingOrSelfPermission 检查广播发送方是否具有某权限。
// 在 B 应用中校验权限：
//public class FetchDataReceiver extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (context.checkCallingOrSelfPermission("com.example.CUSTOM_PERMISSION") == PackageManager.PERMISSION_GRANTED) {
//            // 权限校验通过，处理广播
//            String data = fetchData();
//            // 返回数据
//        } else {
//            // 权限校验失败，不处理广播
//        }
//    }
//}



}
