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

import ch.hesso.santour.R;
import ch.hesso.santour.model.CategoryPOD;
import ch.hesso.santour.model.RatePOD;

/**
 * Created by peixotte on 26.11.2017.
 */

public class CategoryListAdapter extends BaseAdapter {

    private ArrayList<CategoryPOD> listData;
    private LayoutInflater layoutInflater;
    private ArrayList<RatePOD> result;

    private int[] rating;
    public CategoryListAdapter(Context aContext, ArrayList<CategoryPOD> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        result = new ArrayList<>();
        for(int i = 0;i<listData.size();i++){
            result.add(new RatePOD(listData.get(i).getId(),0));
        }
    }
    public CategoryListAdapter(Context aContext, ArrayList<CategoryPOD> listData, int [] rating) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        result = new ArrayList<>();
        this.rating = rating;

        for(int i = 0;i<listData.size();i++){
            result.add(new RatePOD(listData.get(i).getId(),rating[i]));
        }
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {

        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<RatePOD> getAllItems(){
        return result;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {


        CategoryListAdapter.ViewHolder holder;
        if (convertView == null)
            {
                convertView = layoutInflater.inflate(R.layout.item_list_pod_category, null);
                holder = new CategoryListAdapter.ViewHolder();
                holder.label = convertView.findViewById(R.id.pod_category_label);
                holder.difficulty = convertView.findViewById(R.id.pod_category_difficulty);
                holder.difficulty.setMax(10);
                holder.difficulty.setProgress(result.get(position).getRate());
                //holder.difficulty.setProgress(rating[position]);
                holder.difficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                        Log.e("maxDebug", i +" progress");

                        result.get(position).setRate(i);

                        seekBar.setProgress(i);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                convertView.setTag(holder);
            }
        else
            {
                holder = (CategoryListAdapter.ViewHolder) convertView.getTag();
            }

        holder.label.setText(listData.get(position).getName());

        return convertView;
    }

    static class ViewHolder
    {
        TextView label;
        SeekBar difficulty;
    }
}

