package siesgst.edu.in.tml16.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.tml16.R;
import siesgst.edu.in.tml16.helpers.FeedNews;
import siesgst.edu.in.tml16.utils.LocalDBHandler;

/**
 * Created by vishal on 3/2/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    List<FeedNews> feedNewsList;
    Context context;

    public NewsAdapter(Context context) {
        this.context = context;
        addAl();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_layout, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final FeedNews feedEvents = feedNewsList.get(i);

        viewHolder.postMessage.setText(feedEvents.getPostMessage());
        Picasso.with(context).load(feedEvents.getPostImage()).into(viewHolder.postIcon);
        viewHolder.postLink = feedEvents.getPostLink();
        viewHolder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + feedEvents.getPostLink()));
                    context.startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(feedEvents.getPostLink()));
                    context.startActivity(intent);
                }

            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView postIcon;
        protected TextView postMessage;
        protected String postLink;
        protected AppCompatButton readMore;

        public ViewHolder(View itemView) {
            super(itemView);

            postIcon = (ImageView) itemView.findViewById(R.id.news_image);
            postMessage = (TextView) itemView.findViewById(R.id.news_title);
            postMessage.setSelected(true);
            readMore = (AppCompatButton) itemView.findViewById(R.id.read_more);
        }
    }

    public void addAl() {
        feedNewsList = new ArrayList<>();
        for (int i = 0; i < (((new LocalDBHandler(context)).getFBData()).size()) - 3; i = i + 3) {
            FeedNews feedNews = new FeedNews();
            feedNews.setPostMessage((new LocalDBHandler(context)).getFBData().get(i));
            feedNews.setPostImage((new LocalDBHandler(context)).getFBData().get(i + 1));
            feedNews.setPostLink((new LocalDBHandler(context)).getFBData().get(i+2));
            feedNewsList.add(feedNews);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return feedNewsList.size();
    }
}
