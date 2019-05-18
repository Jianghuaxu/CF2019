package com.example.cf2019;

import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PackingFragment extends Fragment implements CameraFragment.CallBack, View.OnDragListener {

    ImageView productView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.packing_fragment, container, false);
        CameraFragment cameraFragment = (CameraFragment) getFragmentManager().findFragmentById(R.id.camera_fragment);
        cameraFragment.setCallBack(this);

        productView = view.findViewById(R.id.product);
        productView.setOnDragListener(this);

        return view;
    }

    @Override
    public void getHUNumber(String number) {
        productView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        return false;
    }
}
