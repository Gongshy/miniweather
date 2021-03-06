package com.example.gongsy.miniweather;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.gongsy.bean.FutureWeather;
import com.example.gongsy.bean.TodayWeather;
import com.example.gongsy.bean.WeatherAdapter;
import com.example.gongsy.util.NetUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Created by Gongsy on 2017/9/27.
 */

public class MainActivity extends Activity implements View.OnClickListener{
    private static final int UPDATE_TODAY_WEATHER = 1;
    private ImageView mUpdateBtn;
    private ImageView mCitySelect;
    private ListView futureWeather;
    private ArrayList<FutureWeather> fw;
    WeatherAdapter wAdapter;
    private TextView cityTv, timeTv, humidityTv, weekTv, pmDataTv,pmQualityTv,temperatureTv, climateTv, windTv, city_name_Tv;
    private ImageView weatherImg, pmImg;
    int i;
    private int[] imagePm={R.drawable.biz_plugin_weather_0_50,R.drawable.biz_plugin_weather_51_100,
            R.drawable.biz_plugin_weather_101_150,R.drawable.biz_plugin_weather_151_200,
            R.drawable.biz_plugin_weather_201_300,R.drawable.biz_plugin_weather_201_300,
            R.drawable.biz_plugin_weather_greater_300};
    private Map<String,Integer> imageWeather=new HashMap<String,Integer>(){
        {
            put("暴雪",R.drawable.biz_plugin_weather_baoxue);
            put("暴雨",R.drawable.biz_plugin_weather_baoyu);
            put("大暴雨",R.drawable.biz_plugin_weather_dabaoyu);
            put("大雪",R.drawable.biz_plugin_weather_daxue);
            put("大雨",R.drawable.biz_plugin_weather_dayu);
            put("多云",R.drawable.biz_plugin_weather_duoyun);
            put("雷阵雨",R.drawable.biz_plugin_weather_leizhenyu);
            put("雷阵雨冰雹",R.drawable.biz_plugin_weather_leizhenyubingbao);
            put("晴",R.drawable.biz_plugin_weather_qing);
            put("沙尘暴",R.drawable.biz_plugin_weather_shachenbao);
            put("特大暴雨",R.drawable.biz_plugin_weather_tedabaoyu);
            put("雾",R.drawable.biz_plugin_weather_wu);
            put("小雪",R.drawable.biz_plugin_weather_xiaoxue);
            put("小雨",R.drawable.biz_plugin_weather_xiaoyu);
            put("阴",R.drawable.biz_plugin_weather_yin);
            put("雨夹雪",R.drawable.biz_plugin_weather_yujiaxue);
            put("阵雪",R.drawable.biz_plugin_weather_zhenxue);
            put("阵雨",R.drawable.biz_plugin_weather_zhenyu);
            put("中雪",R.drawable.biz_plugin_weather_zhongxue);
            put("中雨",R.drawable.biz_plugin_weather_zhongyu);
        }
    };
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);
        mUpdateBtn=(ImageView)findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);

        if(NetUtil.getNetworkState(this)!=NetUtil.NETWORN_NONE){
            Log.d("myWeather","网络OK");
            Toast.makeText(MainActivity.this,"网络OK！", Toast.LENGTH_LONG).show();
        }else{
            Log.d("myWeather","网络挂了");
            Toast.makeText(MainActivity.this,"网络挂了!",Toast.LENGTH_LONG).show();
        }
        mCitySelect=(ImageView) findViewById(R.id.title_city_manager);
        mCitySelect.setOnClickListener(this);
        initView();
    }
    @Override
    public void onClick(View view){
        if(view.getId()==R.id.title_city_manager){
            Intent i=new Intent(this,SelectCity.class);
            startActivityForResult(i,1);
        }
        if(view.getId()==R.id.title_update_btn){
            SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            String cityCode = sharedPreferences.getString("main_city_code","101010100");
            Log.d("myWeather",cityCode);
            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络OK");
                queryWeatherCode(cityCode);
            }else{
                Log.d("myWeather", "网络挂了");
                Toast.makeText(MainActivity.this,"网络挂了！",Toast.LENGTH_LONG).show();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String newCityCode= data.getStringExtra("cityCode");
            Log.d("myWeather", "选择的城市代码为"+newCityCode);
            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络OK");
                queryWeatherCode(newCityCode);
            } else {
                Log.d("myWeather", "网络挂了");
                Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
            }
        }
    }
    void initView(){
        city_name_Tv = (TextView) findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        humidityTv = (TextView) findViewById(R.id.humidity);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);
        futureWeather=(ListView)findViewById(R.id.futureweather);
        fw=new ArrayList<FutureWeather>();
        for(int i=0;i<4;i++){
            fw.add(new FutureWeather());
        }
        wAdapter=new WeatherAdapter(this,fw);
        futureWeather.setAdapter(wAdapter);
        city_name_Tv.setText("N/A");
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");

    }

    private void queryWeatherCode(String cityCode) {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;
        Log.d("myWeather", address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con=null;
                TodayWeather todayWeather = null;
                try{
                    URL url = new URL(address);
                    con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while((str=reader.readLine()) != null){
                        response.append(str);
                        Log.d("myWeather", str);
                    }
                    String responseStr=response.toString();
                    Log.d("myWeather", responseStr);
                    todayWeather = parseXML(responseStr);
                    if (todayWeather != null) {
                        Log.d("myWeather", todayWeather.toString());
                        Message msg =new Message();
                        msg.what = UPDATE_TODAY_WEATHER;
                        msg.obj=todayWeather;
                        mHandler.sendMessage(msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    if(con != null){
                        con.disconnect();
                    }
                }
            }
        }).start();
    }
    void updateTodayWeather(TodayWeather todayWeather){
        city_name_Tv.setText(todayWeather.getCity()+"天气");
        cityTv.setText(todayWeather.getCity());
        timeTv.setText(todayWeather.getUpdatetime()+ "发布");
        humidityTv.setText("湿度："+todayWeather.getShidu());
        if(todayWeather.getPm25()==null){
            pmDataTv.setText("0");
            i=Integer.valueOf("0");
        }else{
            pmDataTv.setText(todayWeather.getPm25());
            i=Integer.valueOf(todayWeather.getPm25());
        }
        pmQualityTv.setText(todayWeather.getQuality());
        weekTv.setText(todayWeather.getDate().get(0));
        temperatureTv.setText(todayWeather.getHigh().get(0)+"~"+todayWeather.getLow().get(0));
        climateTv.setText(todayWeather.getType().get(0));
        windTv.setText("风力:"+todayWeather.getFengli().get(0));
        pmImg.setImageResource(imagePm[Math.min((i-1)/50,6)]);
        int Index=R.drawable.biz_plugin_weather_qing;
        Index=imageWeather.get(todayWeather.getType().get(0));
        weatherImg.setImageResource(Index);
        updateFutureWeather(todayWeather);
        Toast.makeText(MainActivity.this,"更新成功！",Toast.LENGTH_SHORT).show();

    }
    public void updateFutureWeather(TodayWeather todayWeather){
        ArrayList<FutureWeather> fw=new ArrayList<FutureWeather>();

        for(int i=1;i<5;i++){
            FutureWeather f=new FutureWeather();
            f.setDate(todayWeather.getDate().get(i));
            f.setHigh(todayWeather.getHigh().get(i));
            f.setLow(todayWeather.getLow().get(i));
            f.setType(todayWeather.getType().get(i));
            fw.add(f);
        }
        wAdapter.updateListView(fw);
    }

    private TodayWeather parseXML(String xmldata) {
        TodayWeather todayWeather = null;
//        int fengxiangCount=0;
//        int fengliCount =0;
//        int dateCount=0;
//        int highCount =0;
//        int lowCount=0;
//        int typeCount =0;
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = fac.newPullParser();
            xmlPullParser.setInput(new StringReader(xmldata));
            int eventType = xmlPullParser.getEventType();
            Log.d("myWeather", "parseXML");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if(xmlPullParser.getName().equals("resp")){
                            todayWeather= new TodayWeather();
                        }
                        if (todayWeather != null) {
                            if (xmlPullParser.getName().equals("city")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setCity(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("updatetime")){
                                eventType = xmlPullParser.next();
                                todayWeather.setUpdatetime(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("shidu")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setShidu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setWendu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("pm25")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setPm25(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("quality")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setQuality(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("fengxiang") ) {
                                eventType = xmlPullParser.next();
                                todayWeather.getFengli().add(xmlPullParser.getText());
//                                fengxiangCount++;
                            } else if (xmlPullParser.getName().equals("fengli")) {
                                eventType = xmlPullParser.next();
                                todayWeather.getFengli().add(xmlPullParser.getText());
//                                fengliCount++;
                            }else if (xmlPullParser.getName().equals("date")) {
                                eventType = xmlPullParser.next();
                                todayWeather.getDate().add(xmlPullParser.getText());
//                                dateCount++;
                            }else if (xmlPullParser.getName().equals("high") ) {
                                eventType = xmlPullParser.next();
                                todayWeather.getHigh().add(xmlPullParser.getText().substring(2).trim());
//                                highCount++;
                            }else if (xmlPullParser.getName().equals("low")) {
                                eventType = xmlPullParser.next();
                                todayWeather.getLow().add(xmlPullParser.getText().substring(2).trim());
//                                lowCount++;
                            }else if (xmlPullParser.getName().equals("type")) {
                                eventType = xmlPullParser.next();
                                todayWeather.getType().add(xmlPullParser.getText());
//                                typeCount++;
                            }

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        }catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todayWeather;
    }
}
