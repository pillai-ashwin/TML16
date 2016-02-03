package siesgst.edu.in.tml16.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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

    ProgressDialog progressDialog;

    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newsAdapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(newsAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshData();
            }
        });

        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefreshData();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);

        return view;
    }

    public void onRefreshData() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(new ConnectionUtils(getActivity()).checkConnection())  {
                    new LocalDBHandler(getActivity()).wapasTableBana();
                    new FBDataDownload().execute();
                } else {
                    Snackbar.make(getView(),"Can't connect to network..", Snackbar.LENGTH_INDEFINITE).setAction("Try Again", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onRefreshData();
                        }
                    }).show();
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newsAdapter = new NewsAdapter(getActivity());
                recyclerView.setAdapter(newsAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    private class FBDataDownload extends AsyncTask<Void, Void, JSONObject> {
        JSONObject object;

        @Override
        protected JSONObject doInBackground(Void... params) {
            OnlineDBDownloader downloader = new OnlineDBDownloader(getActivity());
            downloader.getFacebookData();
            object = downloader.getFBObject();
            return object;
        }

        @Override
        protected void onPostExecute(JSONObject object) {
            new DataHandler(getActivity()).pushFBData(object);

        }
    }
}
