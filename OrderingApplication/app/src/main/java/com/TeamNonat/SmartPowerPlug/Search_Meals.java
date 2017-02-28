package com.TeamNonat.SmartPowerPlug;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search_Meals extends Fragment implements OnClickListener
{
    private final String ProductName = "ProductName";
    private final String Description = "Description";
    private final String Price = "Price";

    private static final String url = AppController.serveraddress+"/query/search.php";

    private ProgressDialog pDialog;
    private List<Product> ProductList = new ArrayList<Product>();
    private ListView listView;
    private CustomListAdapter adapter;
    Button MealsBack,searchButton;
    TextView searchTF;

    View myView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.search_meal,container,false);

        listView = (ListView) myView.findViewById(R.id.mealsLV);
        adapter = new CustomListAdapter(getActivity(), ProductList);

        MealsBack = (Button) myView.findViewById(R.id.MealsBack);
        searchButton = (Button) myView.findViewById(R.id.searchbutton);
        searchTF = (TextView) myView.findViewById(R.id.searchET);
        searchButton.setOnClickListener(this);
        searchTF.setOnClickListener(this);
        MealsBack.setOnClickListener(this);
        listView.setAdapter(adapter);

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

    public void Search()
    {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Items...");
        pDialog.setCancelable(false);
        pDialog.show();

        String searchquery = searchTF.getText().toString();

        JsonArrayRequest productReq = new JsonArrayRequest(url+"?query="+searchquery,new Response.Listener<JSONArray>()
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
                Toast.makeText(getActivity(), "No items found", Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(productReq);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MealsBack:
                    getFragmentManager().popBackStack();
                break;
            case R.id.searchbutton:
                    ProductList.clear();
                    Search();
                break;
            case R.id.searchET:
                ProductList.clear();
                Search();
                break;
        }
    }


}