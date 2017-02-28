package com.TeamNonat.SmartPowerPlug;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Meals_RiceMeals extends Fragment implements OnClickListener
{
    private final String ProductName = "ProductName";
    private final String Description = "Description";
    private final String Price = "Price";

    private static final String url = AppController.serveraddress+"/query/RiceMeals.php";

    FragmentManager fragmentManager = getFragmentManager();

    private ProgressDialog pDialog;
    private List<Product> ProductList = new ArrayList<Product>();
    private ListView listView;
    private CustomListAdapter adapter;
    Button mealsBack;

    Intent in;

    View myView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.meals_menu_layout,container,false);

        TextView pageTitle = (TextView) myView.findViewById(R.id.textView1);
        pageTitle.setText("Rice Meals");

        listView = (ListView) myView.findViewById(R.id.mealsLV);
        adapter = new CustomListAdapter(getActivity(), ProductList);
        mealsBack = (Button) myView.findViewById(R.id.MealsBack);
        mealsBack.setOnClickListener(this);
        listView.setAdapter(adapter);

        ProductList.clear();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Rice Meals...");
        pDialog.setCancelable(false);
        pDialog.show();

        JsonArrayRequest productReq = new JsonArrayRequest(url,new Response.Listener<JSONArray>()
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
                        product.setDescription(obj.getString("Description"));
                        ProductList.add(product);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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

        listView.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                String name = ((TextView) view.findViewById(R.id.productnameTV)).getText().toString();
                String price = ((TextView) view.findViewById(R.id.priceTV)).getText().toString();
                String description = ((TextView) view.findViewById(R.id.descriptionTV)).getText().toString();


                Bundle bundle = new Bundle();
                bundle.putString(ProductName,name);
                bundle.putString(Price, price);
                bundle.putString(Description, description);
                View_Item args = new View_Item();
                args.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, args).addToBackStack(null).commit();
            }
        });
        return myView;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MealsBack:
                    getFragmentManager().popBackStack();
                break;
        }
    }


}