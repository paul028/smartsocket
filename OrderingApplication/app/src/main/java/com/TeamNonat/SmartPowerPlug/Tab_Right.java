package com.TeamNonat.SmartPowerPlug;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab_Right extends Fragment
{

    View myView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.tab_right_layout, container, false);
        return myView;
    }
}