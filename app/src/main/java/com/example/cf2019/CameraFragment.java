package com.example.cf2019;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CameraFragment extends Fragment{

    int TAKE_PHOTO = 1;
    DecoratedBarcodeView dbcScanner;
    ImageView productPreview;

    LinearLayout productDetail3;

    private CallBack callBack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_frag, container, false);
        //Button scanBtn = view.findViewById(R.id.scan_trigger);
        //scanBtn.setOnClickListener(this);
        dbcScanner = (DecoratedBarcodeView) view.findViewById(R.id.dbv_barcode);
        productPreview = view.findViewById(R.id.product_preview);
        productDetail3 = view.findViewById(R.id.forklift_detail3);
        requestPermission();

        dbcScanner.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_LONG).show();
                dbcScanner.setVisibility(View.GONE);
                productPreview.setVisibility(View.VISIBLE);
                productDetail3.setVisibility(View.VISIBLE);
                callBack.getHUNumber(result.getText());

            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeScanner();
    }

    protected void resumeScanner() {
        if(!dbcScanner.isActivated()) {
            dbcScanner.resume();
        }
    }

    void requestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults.length < 1) {
            requestPermission();
        } else {
            dbcScanner.resume();
        }
    }

    //define a callback interface
    public interface CallBack {
        public void getHUNumber(String number);
    }

    //set callback listener
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
    /***

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_trigger:
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File outputImage = new File(path,  "product.jpg");
                try{
                    if(outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
                //IntentIntegrator.forSupportFragment(CameraFragment.this).initiateScan();
                break;

        }
    }***/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        /***
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this.getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                //newBin.setText(result.getContents());
                Toast.makeText(this.getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                //validateSB();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
         ***/
    }
}
