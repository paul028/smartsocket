package com.nieljoncarlaguel.orderingapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Cart extends Fragment implements OnClickListener
{

    private final String ProductName = "ProductName";
    private final String Description = "Description";
    private final String Price = "Price";


    private static final String url = AppController.serveraddress+"/query/viewCartItems.php";
    public static final String addOrderURL = AppController.serveraddress+"/query/OrderInsert.php";
    String username;
    RequestQueue requestQueue;

    private ProgressDialog pDialog;
    private List<Product> ProductList = new ArrayList<Product>();
    private ListView listView;
    private TextView totalpriceTV;
    private Button checkoutButton;
    private CustomListAdapter adapter;
    private List<Integer> prices = new ArrayList<Integer>();

    Intent in;
    int totalPrice;
    View myView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cart_layout,container,false);

        listView = (ListView) myView.findViewById(R.id.cartLV);
        adapter = new CustomListAdapter(getActivity(), ProductList);
        totalpriceTV = (TextView) myView.findViewById(R.id.totalpriceTV);

        checkoutButton = (Button) myView.findViewById(R.id.CheckoutButton);
        checkoutButton.setOnClickListener(this);


        requestQueue = Volley.newRequestQueue(getActivity());


        listView.setAdapter(adapter);

        ProductList.clear();
        prices.clear();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Cart...");
        pDialog.setCancelable(false);
        pDialog.show();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username = sharedPreferences.getString("userLoggedIn", "");

        JsonArrayRequest productReq = new JsonArrayRequest(url+"?username="+username,new Response.Listener<JSONArray>()
        {
            public void onResponse(JSONArray response)
            {
                if(response.length()!=0)
                {
                    pDialog.dismiss();
                    for (int i = 0; i < response.length(); i++)
                    {
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
                    totalpriceTV.setText("Total Price: "+ totalPrice);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getActivity(), "No items in cart", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "No items in cart", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(productReq);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = ((TextView) view.findViewById(R.id.productnameTV)).getText().toString();
                String price = ((TextView) view.findViewById(R.id.priceTV)).getText().toString();
                String description = ((TextView) view.findViewById(R.id.descriptionTV)).getText().toString();
                String substr=description.substring(10);

                Bundle bundle = new Bundle();
                bundle.putString(ProductName, name);
                bundle.putString(Price, price);
                bundle.putString(Description, substr);
                Edit_Cart_Item args = new Edit_Cart_Item();
                args.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, args).addToBackStack(null).commit();
            }
        });

        return myView;
    }
    public void onClick(View v) {
        if(v.getId()==R.id.CheckoutButton)
        {
            if(ProductList.size()!=0)
            {
                String[] s = { "Dine In", "Take Out"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setSingleChoiceItems(s, 0,new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                            if(selectedPosition == 1)
                            {
                                addOrder("Take Out");
                            }

                            else if(selectedPosition == 0)
                            {
                                addOrder("Dine In");
                            }

                        }
                    });
                AlertDialog dialog = builder.create();
                dialog.show();

                final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                positiveButtonLL.gravity = Gravity.CENTER;
                positiveButton.setLayoutParams(positiveButtonLL);

                /*
                final ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, s);
                final Spinner sp = new Spinner(getActivity());
                sp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                sp.setAdapter(adp);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(sp);
                builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        addOrder(sp.getSelectedItem().toString());
                    }
                });
                builder.create().show();
                //addOrder();
                */
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(), "Empty Cart!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void addOrder(final String ordertype)
    {
        StringRequest request = new StringRequest(Request.Method.POST,addOrderURL, new Response.Listener<String>()
        {
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("success"))
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Checkout Complete!", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new User_Orders()).addToBackStack(null).commit();
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Unable to process order. Please make sure you are connected to the internet.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
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
                hashMap.put("username", username);
                hashMap.put("orderTYPE",ordertype);
                return hashMap;
            };
        };
        requestQueue.add(request);

    }
}
