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

/**
 * Created by degir on 05.12.2017.
 */

class NavigationDrawerAdapter extends BaseAdapter{
    private final ArrayList<String> listData;
    private final LayoutInflater layoutInflater;


    public NavigationDrawerAdapter(Context aContext, List<String> listData) {
        this.listData = (ArrayList<String>)listData;
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


        NavigationDrawerAdapter.ViewHolder holder;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.item_list_menu_navside, null);
            holder = new NavigationDrawerAdapter.ViewHolder();
            holder.name = convertView.findViewById(R.id.menu_name);

            convertView.setTag(holder);
        }
        else
        {
            holder = (NavigationDrawerAdapter.ViewHolder) convertView.getTag();
        }

        holder.name.setText(listData.get(position));

        return convertView;
    }

    static class ViewHolder
    {
        TextView name;
    }
}

