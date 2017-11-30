package ch.hesso.santour.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.santour.R;
import ch.hesso.santour.model.CategoryPOD;
import ch.hesso.santour.model.POD;
import ch.hesso.santour.model.RatePOD;

/**
 * Created by maxim on 28.11.2017.
 */

public class PODListAdapter extends BaseAdapter {
    private ArrayList<POD> listData;
    private LayoutInflater layoutInflater;


    public PODListAdapter(Context aContext, List<POD> listData) {
        this.listData = (ArrayList<POD>)listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {

        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.list_pod, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.creation_name_pod);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(listData.get(position).getName());

        return convertView;
    }

    static class ViewHolder
    {
        TextView name;
    }
}
