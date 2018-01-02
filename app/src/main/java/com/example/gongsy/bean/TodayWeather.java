package com.example.gongsy.bean;

import java.util.AbstractSequentialList;
import java.util.ArrayList;

/**
 * Created by Gongsy on 2017/10/16.
 */

public class TodayWeather {
    private String city;
    private String updatetime;
    private String wendu;
    private String shidu;
    private String pm25;
    private String quality;
    private ArrayList<String> fengxiang=new ArrayList<String>();
    private ArrayList<String> fengli=new ArrayList<String>();
    private ArrayList<String> date=new ArrayList<String>();
    private ArrayList<String> high=new ArrayList<String>();
    private ArrayList<String> low=new ArrayList<String>();
    private ArrayList<String> type=new ArrayList<String>();

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public ArrayList<String> getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(ArrayList<String> fengxiang) {
        this.fengxiang = fengxiang;
    }

    public ArrayList<String> getFengli() {
        return fengli;
    }

    public void setFengli(ArrayList<String> fengli) {
        this.fengli = fengli;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    public ArrayList<String> getHigh() {
        return high;
    }

    public void setHigh(ArrayList<String> high) {
        this.high = high;
    }

    public ArrayList<String> getLow() {
        return low;
    }

    public void setLow(ArrayList<String> low) {
        this.low = low;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TodayWeather{"+"city='"+city+'\''+",updatetime='"+updatetime+'\''+",wendu='"+wendu+'\''
                +"shidu='"+shidu+'\''+",pm25='"+pm25+'\''+",quality='"+quality+'\''
                +"fengxiang='"+fengxiang+'\''+",fengli='"+fengli+'\''+",date='"+date+'\''
                +"high='"+high+'\''+",low='"+low+'\''+",type='"+type+'\''+'}';
    }

}
