package com.nieljoncarlaguel.orderingapplication;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class User_Orders extends Fragment
{
    View myView;

    private static final String url = AppController.serveraddress+"/query/viewOrders.php";
    String username;

    private ProgressDialog pDialog;
    private List<Orders> OrderList = new ArrayList<Orders>();
    private ListView listView;
    private OrderListAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.orders_layout,container,false);

        listView = (ListView) myView.findViewById(R.id.transactionsLV);
        adapter = new OrderListAdapter(getActivity(), OrderList);
        listView.setAdapter(adapter);


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Orders..");
        pDialog.setCancelable(false);
        pDialog.show();

        OrderList.clear();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username = sharedPreferences.getString("userLoggedIn", "");

        JsonArrayRequest productReq = new JsonArrayRequest(url+"?username="+username,new Response.Listener<JSONArray>()
        {
            public void onResponse(JSONArray response)
            {
                pDialog.dismiss();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Orders orders = new Orders();
                        orders.setProductName(obj.getString("OrderID"));
                        orders.setPrice("Date Ordered: "+obj.getString("DateOrdered"));
                        orders.setOrderType(obj.getString("OrderType"));
                        orders.setOrderStatus(obj.getString("OrderStatus"));
                        OrderList.add(orders);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error)
            {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "No Orders", Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(productReq);

        listView.setOnItemClickListener(new OnItemClickListener()
        {

            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {

                String orderID = ((TextView) view.findViewById(R.id.OrderIDTV)).getText().toString();
                String substr=orderID.substring(9);

                Bundle bundle = new Bundle();
                bundle.putString("orderID",substr);
                User_OrderDetails args = new User_OrderDetails();
                args.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, args).addToBackStack(null).commit();
            }

        });
        return myView;
    }
}
