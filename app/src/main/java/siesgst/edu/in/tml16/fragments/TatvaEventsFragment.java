package siesgst.edu.in.tml16.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import siesgst.edu.in.tml16.EventDetailsActivity;
import siesgst.edu.in.tml16.HomeActivity;
import siesgst.edu.in.tml16.R;
import siesgst.edu.in.tml16.adapters.EventAdapter;
import siesgst.edu.in.tml16.helpers.FeedEvents;
import siesgst.edu.in.tml16.helpers.ItemClickSupport;

/**
 * A simple {@link Fragment} subclass.
 */
public class TatvaEventsFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter adapter;

    public TatvaEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tatva_events, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new EventAdapter(getActivity(), "Tatva");
        recyclerView.setAdapter(adapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                TextView eventName = (TextView) v.findViewById(R.id.event_name);
                Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                intent.putExtra("event_name", eventName.getText());
                startActivity(intent);
            }
        });
        return view;
    }

}