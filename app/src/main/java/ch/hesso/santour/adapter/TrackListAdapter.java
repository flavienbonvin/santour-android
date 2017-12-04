package ch.hesso.santour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ch.hesso.santour.R;
import ch.hesso.santour.model.Track;

/**
 * Created by degir on 28.11.2017.
 */

public class TrackListAdapter extends BaseAdapter{

    private ArrayList<Track> listData;
    private LayoutInflater layoutInflater;
    private ArrayList<Track> copie;

    public TrackListAdapter(Context aContext, ArrayList<Track> listData){
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        copie = new ArrayList<>();
        copie.addAll(listData);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        TrackListAdapter.ViewHolder holder;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.item_list_track, null);
            holder = new TrackListAdapter.ViewHolder();
            holder.icon = convertView.findViewById(R.id.track_list_icon);
            holder.label = convertView.findViewById(R.id.track_list_label);

            convertView.setTag(holder);
        }
        else
        {
            holder = (TrackListAdapter.ViewHolder) convertView.getTag();
        }

        holder.label.setText(listData.get(position).getName());

        return convertView;
    }
    static class ViewHolder
    {
        TextView label;
        ImageView icon;
    }

    public ArrayList<Track> getListData() {
        return listData;
    }
}
