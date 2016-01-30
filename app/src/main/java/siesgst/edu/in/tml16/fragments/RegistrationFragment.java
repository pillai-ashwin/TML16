package siesgst.edu.in.tml16.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import siesgst.edu.in.tml16.R;
import siesgst.edu.in.tml16.utils.ConnectionUtils;
import siesgst.edu.in.tml16.utils.LocalDBHandler;
import siesgst.edu.in.tml16.utils.OnlineDBDownloader;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    OnlineDBDownloader db;
    EditText fullName, emailID, phone, division, rollNO, amountPaid;

    AutoCompleteTextView college;

    String year, branch, event = "";

    SharedPreferences sharedPreferences;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        db = new OnlineDBDownloader(getActivity());

        fullName = (EditText) view.findViewById(R.id.full_name);
        emailID = (EditText) view.findViewById(R.id.email_id);
        phone = (EditText) view.findViewById(R.id.phone_no);
        division = (EditText) view.findViewById(R.id.div);
        rollNO = (EditText) view.findViewById(R.id.roll_no);
        amountPaid = (EditText) view.findViewById(R.id.amount);

        sharedPreferences = getActivity().getSharedPreferences("TML", Context.MODE_PRIVATE);

        college = (AutoCompleteTextView) view.findViewById(R.id.college);


        Spinner spinnerYear = (Spinner) view.findViewById(R.id.spinner_year);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerBranch = (Spinner) view.findViewById(R.id.spinner_branch);
        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branch = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerEvent = (Spinner) view.findViewById(R.id.spinner_event);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, (new LocalDBHandler(getActivity())).getAllEventNames());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEvent.setAdapter(dataAdapter);
        spinnerEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                event = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AppCompatButton submit = (AppCompatButton) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new Thread() {
                    @Override
                    public void run() {
                        if ((new ConnectionUtils(getActivity()).checkConnection())) {
                            db.submitRegData(fullName.getText().toString(), emailID.getText().toString(), phone.getText().toString(), year, branch, college.getText().toString(), division.getText().toString(), rollNO.getText().toString(), event, amountPaid.getText().toString());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(getActivity(), sharedPreferences.getString("reg_status", ""), Toast.LENGTH_SHORT).show();
                                    Snackbar.make(v, sharedPreferences.getString("reg_status", ""), Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(getActivity(), "Please check your internet connection..", Toast.LENGTH_SHORT).show();
                                    Snackbar.make(v, "Please check your internet connection..", Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }.start();

            }
        });
        return view;
    }
}