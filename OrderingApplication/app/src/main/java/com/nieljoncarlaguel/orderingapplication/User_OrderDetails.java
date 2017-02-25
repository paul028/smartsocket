package com.nieljoncarlaguel.orderingapplication;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class User_OrderDetails extends Fragment implements OnClickListener
{
    View myView;


    private static final String url = AppController.serveraddress+"/query/viewOrderDetails.php";
    String username;
    RequestQueue requestQueue;

    private ProgressDialog pDialog;
    private List<Product> ProductList = new ArrayList<Product>();
    private ListView listView;
    private TextView totalpriceTV;
    private Button BackButton;
    private CustomListAdapter adapter;
    private List<Integer> prices = new ArrayList<Integer>();
    String userorderID;
    int totalPrice;

    Intent in;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.order_details,container,false);

        listView = (ListView) myView.findViewById(R.id.orderDetailsLV);
        adapter = new CustomListAdapter(getActivity(), ProductList);
        totalpriceTV = (TextView) myView.findViewById(R.id.ODtotalpriceTV);
        BackButton = (Button) myView.findViewById(R.id.backButton);
        BackButton.setOnClickListener(this);

        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Cart...");
        pDialog.setCancelable(false);
        pDialog.show();

        Bundle bundle = this.getArguments();
        if(bundle !=null)
        {
            userorderID = bundle.getString("orderID","");
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username = sharedPreferences.getString("userLoggedIn", "");

        TextView title = (TextView) myView.findViewById(R.id.textView1);
        title.setText("Order Details of OrderID: "+userorderID);



        JsonArrayRequest productReq = new JsonArrayRequest(url+"?username="+username+"&orderID="+userorderID,new Response.Listener<JSONArray>()
        {
            public void onResponse(JSONArray response)
            {
                pDialog.dismiss();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Product product = new Product();
                        product.setProductName(obj.getString("ProductName"));
                        product.setThumbnailUrl(obj.getString("Photo"));
                        product.setPrice(obj.getInt("Price"));
                        product.setDescription("Quantity: "+obj.getInt("Quantity"));
                        ProductList.add(product);
                        prices.add(obj.getInt("Price") * obj.getInt("Quantity"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                for(int j = 0; j<prices.size();j++)
                {
                    totalPrice = totalPrice + prices.get(j);
                }
                totalpriceTV.setText("Total Price:"+ totalPrice);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                pDialog.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(productReq);
        return myView;
    }
    public void onClick(View v) {
        if(v.getId()==R.id.backButton)
        {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
