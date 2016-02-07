package siesgst.edu.in.tml16;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import siesgst.edu.in.tml16.utils.QRInterface;

public class ProfileActivity extends PreferenceActivity {

    private ImageView mProfilepic;
    private TextView mUsername, mEmail, mPhone;

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

        mPhone = (TextView) profile.findViewById(R.id.text_phone);
        mPhone.setText(sharedPreferences.getString("uPhone", ""));

        Preference regCode = findPreference("reg_code");
        regCode.setSummary("TML2016" + sharedPreferences.getString("uID", ""));
        regCode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                launchQR();
                return false;
            }
        });

        Preference year = findPreference("year");
        year.setSummary(sharedPreferences.getString("uYear",""));

        Preference branch = findPreference("branch");
        branch.setSummary(sharedPreferences.getString("uBranch",""));

        Preference rollDiv = findPreference("roll_div");
        rollDiv.setSummary(sharedPreferences.getString("uRoll","") + ", " + sharedPreferences.getString("uDivision",""));

        Preference college = findPreference("college");
        college.setSummary(sharedPreferences.getString("uCollege",""));

        ListView listView = getListView();
        listView.addHeaderView(profile);
    }

    public void launchQR() {
        final Dialog alertDialog = new Dialog(this);
        View qr = getLayoutInflater().inflate(R.layout.qr_code_layout, null);
        alertDialog.setContentView(qr);
        AppCompatButton qrButton = (AppCompatButton) alertDialog.findViewById(R.id.qr_code_button);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        QRInterface qrInterface = new QRInterface();
        ((ImageView) alertDialog.findViewById(R.id.qr_code_image)).setImageBitmap(qrInterface.encodeQRcode("TML2016_0001", 200, 200));
        alertDialog.show();
    }
}
