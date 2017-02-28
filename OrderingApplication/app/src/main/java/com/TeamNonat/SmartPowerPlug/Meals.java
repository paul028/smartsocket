package com.TeamNonat.SmartPowerPlug;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Meals extends Fragment implements View.OnClickListener
{
    Button chickenmeals, breakfast, ricemeals, valuemeals, upgrades,desserts;
    View myView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.meals_layout,container,false);


        breakfast = (Button) myView.findViewById(R.id.breakfast);
        chickenmeals = (Button) myView.findViewById(R.id.chickenmeals);
        ricemeals = (Button) myView.findViewById(R.id.ricemeals);
        valuemeals = (Button) myView.findViewById(R.id.valuemeals);
        upgrades = (Button) myView.findViewById(R.id.upgrades);
        desserts = (Button) myView.findViewById(R.id.desserts);

        breakfast.setOnClickListener(this);
        chickenmeals.setOnClickListener(this);
        ricemeals.setOnClickListener(this);
        valuemeals.setOnClickListener(this);
        upgrades.setOnClickListener(this);
        desserts.setOnClickListener(this);

        return myView;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.breakfast:
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Meals_Breakfast()).addToBackStack(null).commit();
                break;
            case R.id.chickenmeals:
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Meals_ChickenMeals()).addToBackStack(null).commit();
                break;
            case R.id.desserts:
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Meals_Desserts()).addToBackStack(null).commit();
                break;
            case R.id.ricemeals:
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Meals_RiceMeals()).addToBackStack(null).commit();
                break;
            case R.id.upgrades:
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Meals_AddOns()).addToBackStack(null).commit();
                break;
            case R.id.valuemeals:
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Meals_ValueMeals()).addToBackStack(null).commit();
                break;

        }
    }
}
