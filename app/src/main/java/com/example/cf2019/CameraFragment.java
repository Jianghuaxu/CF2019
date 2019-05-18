package com.example.cf2019;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class CameraFragment extends Fragment implements View.OnClickListener{

    DecoratedBarcodeView dbcScanner;
    ImageView productPreview;

    ImageView ForkLiftStausGRImageView;
    TextView ForkLiftStausGRTextView;

    Button testBtn;

    private CallBack callBack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_frag, container, false);
        //Button scanBtn = view.findViewById(R.id.scan_trigger);
        //scanBtn.setOnClickListener(this);
        dbcScanner = view.findViewById(R.id.dbv_barcode);
        productPreview = view.findViewById(R.id.product_preview);

        ForkLiftStausGRImageView = view.findViewById(R.id.ForkliftStatusGR1);
        ForkLiftStausGRTextView = view.findViewById(R.id.ForkliftStatusGR2);


        testBtn = view.findViewById(R.id.test_camera);
        testBtn.setOnClickListener(this);

        requestPermission();

        dbcScanner.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_LONG).show();
                ForkLiftStausGRImageView.setVisibility(View.VISIBLE);
                dbcScanner.setVisibility(View.GONE);
                productPreview.setVisibility(View.VISIBLE);
                callBack.getHUNumber(result.getText());
                if(dbcScanner.isActivated()) {
                    dbcScanner.pause();
                }
                showForkLiftStatus();

            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });

        return view;
    }

    public void showForkLiftStatus() {
        try {
            Thread.currentThread().sleep(1000);
            ForkLiftStausGRTextView.setVisibility(View.VISIBLE);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.test_camera:
                Button btn = (Button) view;
                if(btn.getText().equals("Resume")) {
                    dbcScanner.resume();
                    Toast.makeText(getActivity(), dbcScanner.isActivated()?"Activated":"Disabled", Toast.LENGTH_LONG).show();
                    btn.setText("Pause");

                } else if(btn.getText().equals("Pause")) {
                    dbcScanner.pause();
                    Toast.makeText(getActivity(), dbcScanner.isActivated()?"Activated":"Disabled", Toast.LENGTH_LONG).show();
                    btn.setText("Resume");
                }
                break;
        }
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

    @Override
    public void onPause() {
        super.onPause();
        Log.d("status", "Paused");
        if(!dbcScanner.isActivated()) {
            dbcScanner.pause();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("status", "Destroyed");
        dbcScanner.pause();
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
