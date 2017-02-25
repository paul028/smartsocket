package com.nieljoncarlaguel.orderingapplication;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Product> productItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Product> productItems)
    {
        this.activity = activity;
        this.productItems = productItems;
    }

    public int getCount()
    {
        return productItems.size();
    }

    public Object getItem(int location)
    {
        return productItems.get(location);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {

        if (inflater == null)
        {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        if (imageLoader == null)
        {
            imageLoader = AppController.getInstance().getImageLoader();
        }

        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView Productname = (TextView) convertView.findViewById(R.id.productnameTV);
        TextView Productprice = (TextView) convertView.findViewById(R.id.priceTV);
        TextView Productdescription = (TextView) convertView.findViewById(R.id.descriptionTV);

        //getting product data for the row
        Product p = productItems.get(position);

        // Product image
        thumbNail.setImageUrl(p.getThumbnailUrl(), imageLoader);

        // Product name
        Productname.setText(p.getProductName());

        // Product price
        Productprice.setText("Price: PHP" + String.valueOf(p.getPrice()));

        // Product description
        Productdescription.setText(p.getDescription());

        return convertView;
    }

}