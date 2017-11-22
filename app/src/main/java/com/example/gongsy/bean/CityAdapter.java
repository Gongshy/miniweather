package com.example.gongsy.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gongsy.miniweather.R;

import java.util.List;

/**
 * Created by Gongsy on 2017/11/19.
 */

public class CityAdapter extends ArrayAdapter<City> {
    private int resourceId;
    public CityAdapter(Context context, int textViewResourceId, List<City> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View contentView, ViewGroup parent){
        City city=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView cityName=(TextView)view.findViewById(R.id.city_item);
        cityName.setText(city.getProvince()+"-"+city.getCity()+"-"+city.getNumber());
        return view;
    }

}
