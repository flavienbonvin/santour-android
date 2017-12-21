package ch.hesso.santour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ch.hesso.santour.R;
import ch.hesso.santour.model.CategoryPOI;

/**
 * Created by peixotte on 26.11.2017.
 */

public class CategoryListAdapterPOI extends BaseAdapter {

    private ArrayList<CategoryPOI> listData;
    private LayoutInflater layoutInflater;

    private boolean[] isCheckedTab;


    public CategoryListAdapterPOI(Context aContext, ArrayList<CategoryPOI> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        isCheckedTab = new boolean[listData.size()];
    }
    public CategoryListAdapterPOI(Context aContext, ArrayList<CategoryPOI> listData, boolean[] isCheckedTab) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        this.isCheckedTab = isCheckedTab;
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

    public ArrayList<String> getAll(){
        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i < isCheckedTab.length; i++){
            if (isCheckedTab[i] == true){
                list.add(listData.get(i).getId());
            }
        }
        return list;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {


        CategoryListAdapterPOI.ViewHolder holder;
        if (convertView == null)
            {
                convertView = layoutInflater.inflate(R.layout.item_list_poi_category, null);
                holder = new CategoryListAdapterPOI.ViewHolder();
                holder.checkBox = convertView.findViewById(R.id.poi_category_checkbox);
                holder.checkBox.setText(listData.get(position).getName());
                if(isCheckedTab[position] == true){
                    holder.checkBox.setChecked(true);
                }
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isCheckedTab[position] = isChecked;
                    }
                });
                convertView.setTag(holder);
            }
        else
            {
                holder = (CategoryListAdapterPOI.ViewHolder) convertView.getTag();
            }

        return convertView;
    }

    static class ViewHolder
    {
        CheckBox checkBox;
    }
}

