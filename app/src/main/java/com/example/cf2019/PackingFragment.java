package com.example.cf2019;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PackingFragment extends Fragment implements CameraFragment.CallBack{

    ImageView productView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.packing_fragment, container, false);
        CameraFragment cameraFragment = (CameraFragment) getFragmentManager().findFragmentById(R.id.camera_fragment);
        cameraFragment.setCallBack(this);

        productView = view.findViewById(R.id.product);

        return view;
    }

    @Override
    public void getHUNumber(String number) {
        productView.setVisibility(View.VISIBLE);
    }
}
