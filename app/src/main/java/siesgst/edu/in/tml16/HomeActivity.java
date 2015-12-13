package siesgst.edu.in.tml16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private ImageView mProfilepic;
    private TextView mUsername;
    private TextView mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("TML", MODE_PRIVATE);

        //0: Not logged in
        //1: Skipped
        //2: Logged in

        //Check login status and switch activties accordingly to either Login screen or Home Screen

        //Login Screen
        if (sharedPreferences.getInt("login_status", 0) == 0 | sharedPreferences.getInt("login_status", 0) == 1) {

            startActivityForResult(new Intent(this, LoginActivity.class), 0);
        }

        //Home Screen
        else if (sharedPreferences.getInt("login_status", 0) == 2) {

            setContentView(R.layout.activity_home);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View header = navigationView.getHeaderView(0);

            //Set G+ Profile pic
            mProfilepic = (ImageView) header.findViewById(R.id.profile_pic);
            Uri personPhotoUrl = Uri.parse(sharedPreferences.getString("profile_pic",""));
            new LoadProfileImage(mProfilepic).execute(personPhotoUrl);

            //Set G+ Username
            mUsername = (TextView) header.findViewById(R.id.username);
            mUsername.setText(sharedPreferences.getString("username", ""));

            //Set G+ emailId
            mEmail = (TextView) header.findViewById(R.id.email);
            mEmail.setText(sharedPreferences.getString("email",""));

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    //Handle Login Activity Callback and execute the results obtained.
    // That is, User's G+ username, emailid and profile pic
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == 0 && responseCode == RESULT_OK) {

            editor = sharedPreferences.edit();
            editor.remove("login_status");
            editor.putInt("login_status", 2);
            editor.putString("username", intent.getStringExtra("username"));
            editor.putString("email", intent.getStringExtra("email"));
            editor.putString("profile_pic", intent.getStringExtra("profile_pic"));
            editor.apply();

            //Go back to Home screen once logged in.
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Handle Profile pic update in background
    private class LoadProfileImage extends AsyncTask<Uri, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(Uri... urls) {
            Uri urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay.toString()).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}