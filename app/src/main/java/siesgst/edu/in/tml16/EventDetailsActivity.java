package siesgst.edu.in.tml16;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import siesgst.edu.in.tml16.fragments.RegistrationFragment;
import siesgst.edu.in.tml16.utils.LocalDBHandler;

public class EventDetailsActivity extends AppCompatActivity {

    TextView description, eventDay, venue, head1, head2;
    ImageView iconHead1, iconHead2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String eventName = getIntent().getExtras().getString("event_name");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(eventName);

        description = (TextView) findViewById(R.id.event_detail_detail);
        eventDay = (TextView) findViewById(R.id.event_detail_day);
        venue = (TextView) findViewById(R.id.event_detail_venue);
        head1 = (TextView) findViewById(R.id.event_detail_head1);
        head2 = (TextView) findViewById(R.id.event_detail_head2);
        iconHead1 = (ImageView) findViewById(R.id.icon_head1);
        iconHead2 = (ImageView) findViewById(R.id.icon_head2);

        final ArrayList<String> details = (new LocalDBHandler(this)).getEventKaSabKuch(eventName);
        description.setText(details.get(0));
        eventDay.setText(details.get(1));
        venue.setText(details.get(2));
        head1.setText(details.get(3));
        head2.setText(details.get(4));

        iconHead1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + details.get(5)));
                startActivity(intent);
            }
        });

        iconHead2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + details.get(6)));
                startActivity(intent);
            }
        });
    }

    public void showConfirmationDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure you want to register?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EventDetailsActivity.this, HomeActivity.class);
                intent.putExtra("reg_click", true);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.create();
        alertDialog.show();
    }
}