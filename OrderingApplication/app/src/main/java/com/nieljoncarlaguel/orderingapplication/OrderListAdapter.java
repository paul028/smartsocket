package com.nieljoncarlaguel.orderingapplication;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Orders> orderItems;

    public OrderListAdapter(Activity activity, List<Orders> orderList)
    {
        this.activity = activity;
        this.orderItems = orderList;
    }

    public int getCount()
    {
        return orderItems.size();
    }

    public Object getItem(int location)
    {
        return orderItems.get(location);
    }

    public long getItemId(int position)
    {
        return position;
    }

    @SuppressLint("InflateParams") public View getView(final int position, View convertView, ViewGroup parent)
    {

        if (inflater == null)
        {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_order, null);
        }

        TextView orderid = (TextView) convertView.findViewById(R.id.OrderIDTV);
        TextView orderdate = (TextView) convertView.findViewById(R.id.dateTV);

        TextView ordertype = (TextView) convertView.findViewById(R.id.ordertypeTV);
        TextView orderstatus = (TextView) convertView.findViewById(R.id.orderstatusTV);

        Orders o = orderItems.get(position);

        orderid.setText("OrderID: " +o.getProductName());

        orderdate.setText(String.valueOf(o.getPrice()));

        ordertype.setText("Order Type: "+o.getOrderType());
        orderstatus.setText("Order Status: "+o.getOrderStatus());


        return convertView;
    }

}