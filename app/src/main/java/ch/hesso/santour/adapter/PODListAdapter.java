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
import ch.hesso.santour.model.POD;

/**
 * Created by maxim on 28.11.2017.
 */

public class PODListAdapter extends BaseAdapter {
    private final ArrayList<POD> listData;
    private final LayoutInflater layoutInflater;

    private final Context context;


    public PODListAdapter(Context aContext, List<POD> listData) {
        this.listData = (ArrayList<POD>)listData;
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

        holder.name.setText(context.getString(R.string.pod_name) + listData.get(position).getName());

        return convertView;
    }

    static class ViewHolder
    {
        TextView name;
    }

    public ArrayList<POD> getListData() {
        return listData;
    }

    /**
     * This is a test for github clone**/
}
