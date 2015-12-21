package siesgst.edu.in.tml16;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProfileActivity extends PreferenceActivity {

    private ImageView mProfilepic;
    private TextView mUsername;
    private TextView mEmail;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        addPreferencesFromResource(R.xml.profile_preferences);

        sharedPreferences = getSharedPreferences("TML", MODE_PRIVATE);

        View profile = getLayoutInflater().inflate(R.layout.profile_card, null);

        mProfilepic = (ImageView) profile.findViewById(R.id.profile_pic1);
        if (!sharedPreferences.getString("profile_pic","").equals("")) {
            Picasso.with(this).load(sharedPreferences.getString("profile_pic", "")).into(mProfilepic);
        } else {
            mProfilepic.setImageResource(R.mipmap.ic_launcher);
        }

        mUsername = (TextView) profile.findViewById(R.id.username);
        mUsername.setText(sharedPreferences.getString("username", ""));

        mEmail = (TextView) profile.findViewById(R.id.text_email);
        mEmail.setText(sharedPreferences.getString("email", ""));

        ListView listView = getListView();
        listView.addHeaderView(profile);
    }

}
