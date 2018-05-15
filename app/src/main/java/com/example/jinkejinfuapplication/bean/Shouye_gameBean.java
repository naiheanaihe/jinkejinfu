package com.example.jinkejinfuapplication.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by naihe on 2017/10/27.
 */

public class Shouye_gameBean {
    private String  id,img_bk,img_icon,img1,img2,img3,name,type,time,jiashao,zixunid,shipingid1,shipingid2,zhujitype;
    private boolean hasnews=false;
    private int hasshiping=0;
    private List<Map<String,String>> gameList=new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_bk() {
        return img_bk;
    }

    public void setImg_bk(String img_bk) {
        this.img_bk = img_bk;
    }

    public String getImg_icon() {
        return img_icon;
    }

    public void setImg_icon(String img_icon) {
        this.img_icon = img_icon;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getJiashao() {
        return jiashao;
    }

    public void setJiashao(String jiashao) {
        this.jiashao = jiashao;
    }

    public String getShipingid1() {
        return shipingid1;
    }

    public void setShipingid1(String shipingid1) {
        this.shipingid1 = shipingid1;
    }

    public String getShipingid2() {
        return shipingid2;
    }

    public void setShipingid2(String shipingid2) {
        this.shipingid2 = shipingid2;
    }

    public List<Map<String, String>> getGameList() {
        return gameList;
    }

    public void setGameList(List<Map<String, String>> gameList) {
        this.gameList = gameList;
    }

    public boolean isHasnews() {
        return hasnews;
    }

    public void setHasnews(boolean hasnews) {
        this.hasnews = hasnews;
    }

    public int getHasshiping() {
        return hasshiping;
    }

    public void setHasshiping(int hasshiping) {
        this.hasshiping = hasshiping;
    }

    public String getZhujitype() {
        return zhujitype;
    }

    public void setZhujitype(String zhujitype) {
        this.zhujitype = zhujitype;
    }

    public String getZixunid() {
        return zixunid;
    }

    public void setZixunid(String zixunid) {
        this.zixunid = zixunid;
    }
}
