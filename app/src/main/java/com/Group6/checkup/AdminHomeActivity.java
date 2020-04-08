package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Group6.checkup.Adapters.AdminViewHistoryActivity;
import com.Group6.checkup.CreateAccountPackage.AccountTypeOptionActivity;
import com.Group6.checkup.EditAndUpdateAccountPackage.EditAndUpdateAccountSearchActivity;
import com.Group6.checkup.Entities.Admin;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.Utils.Session;
import com.google.android.material.navigation.NavigationView;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Session appSession;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        appSession = new Session(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        TextView adminName = findViewById(R.id.text_admin_full_name);
        Admin user = new AdminDao(this).find(appSession.getCurrentUsername());
        String fullname = user.getFirstName() + " " +  user.getLastName();

        setTitle("Home");
        adminName.setText(fullname);

        toggleSetUp();

    }

    public void buttonClicked(View view) {
        if (view.getId() == R.id.btn_toCreateAccountActivity) {
            startActivity(new Intent(AdminHomeActivity.this, AccountTypeOptionActivity.class));
        } else if (view.getId() == R.id.btn_toEditAndUpdateAccountActivity) {
            startActivity(new Intent(AdminHomeActivity.this, EditAndUpdateAccountSearchActivity.class));
        }
    }

    public void toggleSetUp() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_home:
                Intent h = new Intent(AdminHomeActivity.this, AdminHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g = new Intent(AdminHomeActivity.this, AdminViewHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s = new Intent(AdminHomeActivity.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
