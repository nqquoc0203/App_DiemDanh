package com.example.deanmoi;



import static android.Manifest.permission.CAMERA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Size;
import android.widget.Toast;

import com.example.deanmoi.UI.LoginActivity;
import com.example.deanmoi.databinding.ActivityScanBarCodeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScanBarCodeActivity extends AppCompatActivity {
    ActivityScanBarCodeBinding binding;
    ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanBarCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ContextCompat.checkSelfPermission(ScanBarCodeActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED) {
            init();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[] {CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
        binding.btnAction.setOnClickListener(v -> LogIn());
    }

    private void LogIn() {
        String id = binding.txtBarcodeValue.getText().toString().trim();
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    private void init() {
        cameraProviderListenableFuture =  ProcessCameraProvider.getInstance(this);
        cameraProviderListenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                    bindImageAnalysis(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }
        else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
    private void bindImageAnalysis (ProcessCameraProvider processCameraProvider) {
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                Image mediaImage = image.getImage();
                if (mediaImage != null) {
                    InputImage image1 = InputImage.fromMediaImage(mediaImage, image.getImageInfo().getRotationDegrees());
                    BarcodeScanner scanner = BarcodeScanning.getClient();
                    Task<List<Barcode>>  result = scanner.process(image1);
                    result.addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            for (Barcode barcode : barcodes) {
                                final String getValue = barcode.getRawValue();
                                binding.txtBarcodeValue.setText(getValue);
                            }
                            image.close();
                            mediaImage.close();
                        }
                    });
                }
            }
        });
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(binding.cameraPreview.getSurfaceProvider());
        processCameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);
    }
}