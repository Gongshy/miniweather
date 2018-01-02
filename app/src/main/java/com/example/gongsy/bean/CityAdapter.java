package com.example.gongsy.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gongsy.miniweather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gongsy on 2017/11/19.
 */
public class CityAdapter extends BaseAdapter {
    private Context context;
    private List<City> list;
    public CityAdapter(Context context,  ArrayList<City> objects){
        this.context=context;
        list=objects;
    }
    public void updateListView(ArrayList<City> objects) {
        list = objects;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public City getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = null;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = inflater.inflate(R.layout.item,parent,false);
//            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
//            viewHolder.id = (TextView) convertView.findViewById(R.id.id);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        viewHolder.name.setText(list.get(position).getName());
//        viewHolder.id.setText(list.get(position).getObjectId());
//        return convertView;
//    }
//    @Override
    public View getView(int position, View contentView, ViewGroup parent){
        ViewHolder holder = null;
        if (contentView == null) {
            holder=new ViewHolder();
            contentView= LayoutInflater.from(context).inflate(R.layout.city_item,null);
            holder.cityName=(TextView)contentView.findViewById(R.id.city_item);
            contentView.setTag(holder);
        }else{
            holder=(ViewHolder)contentView.getTag();
        }
        holder.cityName.setText(list.get(position).getCity()+"  "+list.get(position).getNumber());
        return contentView;
    }
    public final class ViewHolder {
        private TextView cityName;
    }


}
