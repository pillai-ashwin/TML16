package siesgst.edu.in.tml16;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView mProfilepic;
    private TextView mUsername;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("TML", MODE_PRIVATE);

        mProfilepic = (ImageView) findViewById(R.id.profile_pic);
        if (!sharedPreferences.getString("profile_pic","").equals("")) {
            Picasso.with(this).load(sharedPreferences.getString("profile_pic", "")).into(mProfilepic);
        } else {
            mProfilepic.setImageResource(R.mipmap.ic_launcher);
        }

        mUsername = (TextView) findViewById(R.id.username);
        mUsername.setText(sharedPreferences.getString("username", ""));
    }

}
