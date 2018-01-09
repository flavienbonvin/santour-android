package ch.hesso.santour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.santour.R;
import ch.hesso.santour.model.POI;

/**
 * Created by maxim on 28.11.2017.
 */

public class POIListAdapter extends BaseAdapter{
    private ArrayList<POI> listData;
    private LayoutInflater layoutInflater;

    private Context context;

    public POIListAdapter(Context aContext, List<POI> listData) {
        this.listData = (ArrayList<POI>)listData;
        layoutInflater = LayoutInflater.from(aContext);

        context = aContext;
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
            convertView = layoutInflater.inflate(R.layout.item_list_pod, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.creation_name_pod);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(context.getString(R.string.poi_name) + listData.get(position).getName());

        return convertView;
    }

    static class ViewHolder
    {
        TextView name;
    }


    public ArrayList<POI> getListData(){
        return listData;
    }
}
