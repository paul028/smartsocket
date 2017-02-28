package com.nieljoncarlaguel.orderingapplication;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private SharedPreferences sharedPreferences;
    String username,nameofUser;
    DrawerLayout drawer;
    NavigationView navigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = sharedPreferences.getString("userLoggedIn", "");
        nameofUser = sharedPreferences.getString("User Name","");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.UsernameView);
        TextView email = (TextView)header.findViewById(R.id.textView);

        name.setText("Hello, "+nameofUser);
        email.setText("Username: " + username);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Welcome()).commit();

    }

    public void onBackPressed()
    {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        getSupportFragmentManager().popBackStack();

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        /*if (id == R.id.action_cart)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Cart()).addToBackStack(null).commit();
            navigationView.setCheckedItem(R.id.nav_cart);
        }
        else if(id == R.id.action_search)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Search_Meals()).addToBackStack(null).commit();
        }*/
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        /*if (id == R.id.nav_meals)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Meals()).addToBackStack(null).commit();
        }
        else if (id == R.id.nav_cart)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Cart()).addToBackStack(null).commit();
        }
        else if (id == R.id.nav_orders)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new User_Orders()).addToBackStack(null).commit();
        }
        else if (id == R.id.nav_orderhistory)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new User_OrderHistory()).addToBackStack(null).commit();
        }*/
        if (id == R.id.nav_logout)
        {
            Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (id == R.id.nav_home)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Welcome()).commit();
        }

        else if (id == R.id.nav_graph)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Graph_Fragment()).commit();
    }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
