package com.nieljoncarlaguel.orderingapplication;


import android.content.SharedPreferences;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by LESTER on 2/27/2017.
 */

public class Graph extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_graph);

        double y,x;
        x= -5.0;
        //graph instance
        GraphView graph = (GraphView) findViewById(R.id.nav_graph);
        series = new LineGraphSeries<DataPoint>();
        for(int i = 0; i<500; i++){
            x= x + 0.1;
            y = Math.sin(x);

            series.appendData(new DataPoint(x, y), true, 500);

        }
        graph.addSeries(series);

    }

}
