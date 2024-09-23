package com.example.my_android_training;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TextureViewActivity extends Activity {

    private TextureView textureView;
    private CameraCaptureSession cameraCaptureSession;
    private CameraDevice cameraDevice;
    private Size previewSize;
    private String cameraId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_view);

        textureView = findViewById(R.id.texture_view);
        displayVideo();
    }

    private void displayVideo() {
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
//                setFixedResolution(); // 在xml中直接指定也可
                setupCamera("0", textureView.getWidth(), textureView.getHeight());
                openCamera("0");
            }

            @Override
            public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
                closeCamera();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

            }
        });
    }

    private void closeCamera() {
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    private void openCamera(String cameraId) {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // 处理未授权情况
                return;
            }

            // 打开摄像头
            cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    cameraDevice = camera;
                    startCameraPreview();  // 开启摄像头预览
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    cameraDevice.close();
                    cameraDevice = null;
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                    cameraDevice.close();
                    cameraDevice = null;
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void startCameraPreview() {
        SurfaceTexture texture = textureView.getSurfaceTexture();
        if (texture == null) {
            return;
        }
        // 设置 SurfaceTexture 的缓冲区大小
        texture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
        Surface surface = new Surface(texture);

        try {
            CaptureRequest.Builder captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);

            cameraDevice.createCaptureSession(Collections.singletonList(surface),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            cameraCaptureSession = session;
                            // 开始视频流请求
                            try {
                                cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                            // 处理配置失败
                        }
                    }, null
            );
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void setupCamera(String cameraId, int width, int height) {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            if (map != null) {
                // 获取摄像头支持的预览尺寸列表
                Size[] previewSizes = map.getOutputSizes(SurfaceHolder.class);
                previewSize = chooseOptimalSize(previewSizes, width, height);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private Size chooseOptimalSize(Size[] choices, int textureViewWidth, int textureViewHeight) {
        List<Size> bigEnough = new ArrayList<>();

        for (Size option : choices) {
            // 确保预览尺寸的纵横比与 TextureView 的比例匹配
            if (option.getHeight() == option.getWidth() * textureViewHeight / textureViewWidth &&
                    option.getWidth() >= textureViewWidth && option.getHeight() >= textureViewHeight) {
                bigEnough.add(option);
            }
        }

        // 从候选中选择最小的合适尺寸
        if (!bigEnough.isEmpty()) {
            return Collections.min(bigEnough, new Comparator<Size>() {
                @Override
                public int compare(Size lhs, Size rhs) {
                    return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                            (long) rhs.getWidth() * rhs.getHeight());
                }
            });
        } else {
            // 没有合适尺寸，返回默认尺寸
            return choices[0];
        }
    }

    private void setFixedResolution() {
        ViewGroup.LayoutParams params = textureView.getLayoutParams();
        params.width = 1280;
        params.height = 720;
        textureView.setLayoutParams(params);
    }


    /*
    如果你有对 SurfaceView 或 TextureView 的尺寸做过自定义处理，
    也可以选择在 onResume() 生命周期方法中调用 setupCamera()，以确保在活动恢复时初始化摄像头。
     */

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (textureView.isAvailable()) {
//            // TextureView 已经可用，直接调用 setupCamera()
//            setupCamera(textureView.getWidth(), textureView.getHeight());
//            openCamera();
//        } else {
//            // 设置 SurfaceTextureListener，等待 TextureView 准备好时调用
//            textureView.setSurfaceTextureListener(surfaceTextureListener);
//        }
//    }
}
