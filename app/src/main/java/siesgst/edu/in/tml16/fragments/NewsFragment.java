package siesgst.edu.in.tml16.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import siesgst.edu.in.tml16.R;
import siesgst.edu.in.tml16.adapters.NewsAdapter;
import siesgst.edu.in.tml16.utils.ConnectionUtils;
import siesgst.edu.in.tml16.utils.DataHandler;
import siesgst.edu.in.tml16.utils.LocalDBHandler;
import siesgst.edu.in.tml16.utils.OnlineDBDownloader;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    CoordinatorLayout layout;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        layout = (CoordinatorLayout) view.findViewById(R.id.news_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshData();
            }
        });

        onRefreshData();

        return view;
    }

    public void onRefreshData() {
        swipeRefreshLayout.setRefreshing(true);
        new FBDataDownload().execute();
    }

    private class FBDataDownload extends AsyncTask<Void, Void, JSONObject> {
        JSONObject object;

        @Override
        protected void onPreExecute() {
            if (new ConnectionUtils(getActivity()).checkConnection()) {
                new LocalDBHandler(getActivity()).dropFBTable();
            } else {
                Snackbar.make(layout, "Can't connect to network..", Snackbar.LENGTH_INDEFINITE).setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRefreshData();
                    }
                }).show();
            }
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TML", Context.MODE_PRIVATE);
            OnlineDBDownloader downloader = new OnlineDBDownloader(getActivity());
            downloader.getFacebookData();
            object = downloader.getFBObject();
            if (!sharedPreferences.getString("nw_status", "").equals("bad")) {
                new DataHandler(getActivity()).pushFBData(object);
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Check your internet connection...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return object;
        }

        @Override
        protected void onPostExecute(JSONObject object) {
            newsAdapter = new NewsAdapter(getActivity());
            recyclerView.setAdapter(newsAdapter);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
