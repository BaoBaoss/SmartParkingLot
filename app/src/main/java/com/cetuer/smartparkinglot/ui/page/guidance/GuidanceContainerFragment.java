package com.cetuer.smartparkinglot.ui.page.guidance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cetuer.smartparkinglot.R;

/**
 * Created by Cetuer on 2021/10/11 10:39.
 * 容器fragment，用于切换
 */
public class GuidanceContainerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guidance_container, container, false);
    }
}
