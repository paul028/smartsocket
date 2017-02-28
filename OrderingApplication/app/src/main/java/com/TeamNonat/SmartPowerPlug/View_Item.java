package com.TeamNonat.SmartPowerPlug;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class View_Item extends Fragment implements OnClickListener
{

    public static final String addToCartURL = AppController.serveraddress+"/query/addcart.php";
    public static final String URL = AppController.serveraddress+"/query/viewitem.php";


    public static final String userLogged = "userLoggedIn";

    TextView title;
    String name, price, description, photoURL,productID,ItemQuantity,username;
    Button addtocart;
    NumberPicker itemquantity;

    RequestQueue requestQueue;
    NetworkImageView mNetworkImageView;
    SimpleDateFormat sdf;

    TextView lblName,lblPrice,lblDescription;

    static final String NAME = "ProductName";
    static final String PRICE = "Price";
    static final String DESCRIPTION = "Description";
    ProgressDialog pDialog;


    View myView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.view_item,container,false);


        Bundle bundle = this.getArguments();
        if(bundle !=null)
        {
            name = bundle.getString(NAME, "TEST");
            price = bundle.getString(PRICE, "100");
            description = bundle.getString(DESCRIPTION, "DESCRIPTION");


        }
        else
        {
            name = "TEST";
            price = "100";
            description = "DESCRIPTION";
        }

        requestQueue = Volley.newRequestQueue(getActivity());
        LoadItem();
        // Displaying all values on the screen
        lblName = (TextView) myView.findViewById(R.id.ViewItemTitle);
        lblPrice = (TextView) myView.findViewById(R.id.ViewItemPrice);
        lblDescription = (TextView) myView.findViewById(R.id.ViewItemDescription);
        mNetworkImageView = (NetworkImageView) myView.findViewById(R.id.networkImageView);
        itemquantity = (NumberPicker) myView.findViewById(R.id.numberPicker1);
        addtocart = (Button) myView.findViewById(R.id.addtocartbutton);


        itemquantity.setMaxValue(100);
        itemquantity.setMinValue(1);
        itemquantity.setWrapSelectorWheel(false);
        addtocart.setOnClickListener(this);


        return myView;
    }

    public void parseData()
    {

        StringRequest request = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    lblName.setText(name);
                    lblPrice.setText(price);
                    lblDescription.setText(description);
                    photoURL = jsonObject.getString("Photo").toString();
                    productID = jsonObject.getString("productID").toString();
                    loadImage(photoURL);
                } catch (JSONException e)
                {
                    pDialog.setCancelable(true);
                    pDialog.setMessage("Error loading item");
                    pDialog.show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        }){
            protected Map<String, String> getParams()
                    throws AuthFailureError
            {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("productname", name);
                return hashMap;
            };
        };
        requestQueue.add(request);


    }

    public void onClick(View v) {
        if(v.getId()==R.id.addtocartbutton)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            username = sharedPreferences.getString("userLoggedIn", "");
            ItemQuantity = "" + itemquantity.getValue();

            StringRequest request = new StringRequest(Request.Method.POST,
                    addToCartURL, new Response.Listener<String>() {
                public void onResponse(String response) {

                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.names().get(0).equals("success"))
                        {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setMessage("Item added to cart.");
                            builder1.setCancelable(true);
                            builder1.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Cart()).addToBackStack(null).commit();
                                }
                            });

                            AlertDialog alert = builder1.create();
                            alert.show();
                        }
                        else
                        {
                            pDialog.setCancelable(true);
                            pDialog.setMessage("Error loading item" +jsonObject.getString("error").toString());
                            pDialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                public void onErrorResponse(VolleyError error)
                {
                }
            }){
                protected Map<String, String> getParams()
                        throws AuthFailureError
                {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("productname", name);
                    hashMap.put("username", username);
                    hashMap.put("quantity", ItemQuantity);
                    return hashMap;
                };
            };
            requestQueue.add(request);

        }
    }

    public void LoadItem()
    {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Item...");
        pDialog.show();
        parseData();

    }

    public void loadImage(String url)
    {
        ImageLoader mImageLoader = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getImageLoader();
        mImageLoader.get(url, ImageLoader.getImageListener(mNetworkImageView, android.R.drawable.ic_dialog_alert, 0));
        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                }
            }

        };
        timer.start();
        mNetworkImageView.setImageUrl(url, mImageLoader);
        pDialog.dismiss();
    }
}
