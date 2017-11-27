package ch.hesso.santour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import ch.hesso.santour.R;
import ch.hesso.santour.model.CategoryPOD;

/**
 * Created by peixotte on 26.11.2017.
 */

public class CategoryListAdapter extends BaseAdapter {

    private ArrayList<CategoryPOD> listData;
    private LayoutInflater layoutInflater;
    private ArrayList<CategoryPOD> copie;


    public CategoryListAdapter(Context aContext, ArrayList<CategoryPOD> listData) {
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
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        CategoryListAdapter.ViewHolder holder;
        if (convertView == null)
            {
                convertView = layoutInflater.inflate(R.layout.pod_category, null);
                holder = new CategoryListAdapter.ViewHolder();
                holder.label = convertView.findViewById(R.id.pod_category_label);
                holder.difficulty = convertView.findViewById(R.id.pod_category_difficulty);
                convertView.setTag(holder);
            }
        else
            {
                holder = (CategoryListAdapter.ViewHolder) convertView.getTag();
            }

        holder.label.setText(listData.get(position).getName());
        holder.difficulty.setProgress(listData.get(position).getRating());

        return convertView;
    }

    static class ViewHolder
    {
        TextView label;
        SeekBar difficulty;
    }
}

