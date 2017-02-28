package com.TeamNonat.SmartPowerPlug;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends Activity
{
    private static final String URL = AppController.serveraddress+"/smartsocket/App/register.php";
    RequestQueue requestQueue;
    ProgressDialog pDialog;
    /** Variables **/
    Button signup;
    EditText etfname,etlname,etaddress,etemail,etuname,etpword,etrpword;
    String valid_email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signup);

        signup = (Button) findViewById(R.id.SignupButton);

        etfname =(EditText) findViewById(R.id.etfirstname);
        etlname = (EditText) findViewById(R.id.etlastname);
        etaddress = (EditText) findViewById(R.id.etaddress);
        etemail = (EditText) findViewById(R.id.etemail);
        etpword = (EditText) findViewById(R.id.etpassword);
        etuname = (EditText) findViewById(R.id.etusername);
        etrpword = (EditText) findViewById(R.id.etrepeatpass);

        pDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);

        etemail.addTextChangedListener(new TextWatcher()
        {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                Is_Valid_Email(etemail);
            }

            public void Is_Valid_Email(EditText edt) {
                if (edt.getText().toString() == null) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                } else if (isEmailValid(edt.getText().toString()) == false) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                } else {
                    valid_email = edt.getText().toString();
                }
            }

            boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        });

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(Registration.this);
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(		etfname.getText().toString().matches("") &&
                        etlname.getText().toString().matches("") &&
                        etaddress.getText().toString().matches("") &&
                        etemail.getText().toString().matches("") &&
                        etuname.getText().toString().matches("") &&
                        etrpword.getText().toString().matches("") &&
                        etpword.getText().toString().matches("") &&
                        etrpword.getText().toString().matches(""))
                {
                    builder1.setMessage("Complete all fields first.");
                    builder1.setCancelable(true);
                    builder1.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder1.create();
                    alert.show();
                }
                else if(!etrpword.getText().toString().matches(etpword.getText().toString()))
                {
                    builder1.setMessage("Passwords mismatch.");
                    builder1.setCancelable(true);
                    builder1.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder1.create();
                    alert.show();
                }

                else if (!etfname.getText().toString().matches("") &&
                        !etlname.getText().toString().matches("") &&
                        !etaddress.getText().toString().matches("") &&
                        !etemail.getText().toString().matches("") &&
                        !etuname.getText().toString().matches("") &&
                        !etpword.getText().toString().matches("") &&
                        !etrpword.getText().toString().matches("") &&
                        etrpword.getText().toString().matches(etpword.getText().toString()) &&
                        valid_email.matches(etemail.getText().toString()))
                {
                    pDialog.setMessage("Signing up...");
                    pDialog.show();
                    addUser();
                }
                else if (!etfname.getText().toString().matches("") &&
                        !etlname.getText().toString().matches("") &&
                        !etaddress.getText().toString().matches("") &&
                        !etemail.getText().toString().matches("") &&
                        !etuname.getText().toString().matches("") &&
                        !etpword.getText().toString().matches("") &&
                        !etrpword.getText().toString().matches("") &&
                        !etrpword.getText().toString().matches(etpword.getText().toString()) &&
                        !valid_email.matches(etemail.getText().toString()))
                {
                    pDialog.setMessage("An error occured.");
                    pDialog.show();
                }

            }
        });

    }

    private void addUser()
    {
        StringRequest request = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>()
        {
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("success"))
                    {
                        pDialog.setMessage("Successfully Registered!\nRedirecting to Login page.\nWelcome "+jsonObject.getString("success").toString());
                        pDialog.show();
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
                                    pDialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                }
                            }
                        };
                        timer.start();
                    }
                    else if (jsonObject.names().get(0).equals("error"))
                    {
                        pDialog.setCancelable(true);
                        pDialog.setMessage(jsonObject.getString("error").toString());
                    }
                    else
                    {
                        pDialog.setCancelable(true);
                        pDialog.setMessage("Error!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError arg0)
            {

            }
        }) {
            protected Map<String, String> getParams()
                    throws AuthFailureError
            {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("firstname", etfname.getText().toString());
                hashMap.put("lastname", etlname.getText().toString());
                hashMap.put("address", etaddress.getText().toString());
                hashMap.put("emailaddress", etemail.getText().toString());
                hashMap.put("username", etuname.getText().toString());
                hashMap.put("password", etpword.getText().toString());
                return hashMap;
            }
        };
        requestQueue.add(request);

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
