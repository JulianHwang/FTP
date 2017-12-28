package com.dc.ftp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dc.ftp.R;
import com.dc.ftp.base.SPBaseFragment;

/**
 * Created by Julian on 2017/11/28.
 */

public class SPInfoFragment extends SPBaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        super.init(view);

        return view;


    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }


}
