package siesgst.edu.in.tml16.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.tml16.R;
import siesgst.edu.in.tml16.helpers.FeedEvents;
import siesgst.edu.in.tml16.utils.LocalDBHandler;

/**
 * Created by vishal on 10/1/16.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    List<FeedEvents> feedEventsList;
    Context context;

    public EventAdapter(Context context, String category, String subCategory) {
        this.context = context;
        feedEventsList = new ArrayList<>();

        for (int i = 0; i < ((new LocalDBHandler(context)).getEventNamesAndDay(category, subCategory)).size() - 1; i = i +2) {
            FeedEvents feedEvents = new FeedEvents();
            feedEvents.setEventName((new LocalDBHandler(context)).getEventNamesAndDay(category, subCategory).get(i) );
            feedEvents.setEventDay((new LocalDBHandler(context)).getEventNamesAndDay(category, subCategory).get(i + 1));
            feedEventsList.add(feedEvents);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_event_layout, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        FeedEvents feedEvents = feedEventsList.get(i);

        viewHolder.eventName.setText(feedEvents.getEventName());
        viewHolder.eventDay.setText(feedEvents.getEventDay());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView eventIcon;
        protected TextView eventName;
        protected TextView eventDay;

        public ViewHolder(View itemView) {
            super(itemView);

            eventIcon = (ImageView) itemView.findViewById(R.id.event_icon);
            eventName = (TextView) itemView.findViewById(R.id.event_name);
            eventDay = (TextView) itemView.findViewById(R.id.event_day);
        }
    }

    @Override
    public int getItemCount() {
        return feedEventsList.size();
    }
}