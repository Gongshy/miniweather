package com.example.gongsy.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gongsy.app.MyApplication;
import com.example.gongsy.bean.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gongsy on 2017/10/21.
 */

public class SelectCity extends Activity implements View.OnClickListener{
    private ListView mList;
    private ArrayList<City> cityList;
    private List<String> city;
    private ImageView mBackBtn;
    ClearEditView mClearEditText;
    private ArrayList<City> filterDateList;
    private CityAdapter adapter;
    ListView listView;
    ArrayList<City> ctList=new ArrayList<City>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
        mBackBtn=(ImageView)findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        MyApplication myApplication=(MyApplication) getApplication();
        cityList=(ArrayList<City>) myApplication.getCityList();
        adapter=new CityAdapter(SelectCity.this,cityList);
        listView=(ListView)findViewById(R.id.title_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                City city=cityList.get(i);
                Intent j = new Intent();
                j.putExtra("cityCode", city.getNumber());
                setResult(RESULT_OK, j);
                finish();
                Toast.makeText(SelectCity.this, "你单击了:"+city.getNumber(),Toast.LENGTH_SHORT).show();
            }
        });
        mClearEditText=(ClearEditView)findViewById(R.id.search_city);
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterDate(s.toString());
//                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void filterDate(String filterStr){
        filterDateList=new ArrayList<City>();
        if (TextUtils.isEmpty(filterStr)){
            for(City city : cityList){
                filterDateList.add(city);
            }
        }else{
            filterDateList.clear();
            for(City city:cityList){
                if(city.getCity().indexOf(filterStr.toString())!=-1){

                    filterDateList.add(city);

                }
            }
        }
        if(filterDateList!=null){
            adapter.updateListView(filterDateList);
        }
//        adapter.updateListView(filterDateList);
//        adapter.notifyDataSetChanged();
    }
    private class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            for(City ct:cityList){
                String str=ct.getProvince()+"-"+ct.getCity()+"-"+ct.getNumber();
                if(str.indexOf(s.toString())!=-1){
                    ctList.add(ct);
                }
            }
            adapter=new CityAdapter(SelectCity.this,ctList);
            listView.setAdapter(adapter);
            ctList.clear();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_back:
                Intent i = new Intent();
                i.putExtra("cityCode", "101160101");
                setResult(RESULT_OK, i);
                finish();
                break;
            default:
                break;
        }
    }
}
