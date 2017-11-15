package com.example.gongsy.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gongsy.util.NetUtil;

/**
 * Created by Gongsy on 2017/10/21.
 */

public class SelectCity extends Activity implements View.OnClickListener{
    private ImageView mBackBtn;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
        initViews();
    }
    private void initViews(){
        mBackBtn=(ImageView)findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        mClearEditText=(ClearEditText)findViewById(R.id.search_city);
        mList=(ListView)findViewById(R.id.title_list);
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
