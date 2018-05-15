package com.example.jinkejinfuapplication.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by naihe on 2017/10/27.
 */

public class Haiwai_gameBean {
    private String  id,img_bk,img_icon,name,type,jiashao,zhujitype;
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

    public String getJiashao() {
        return jiashao;
    }

    public void setJiashao(String jiashao) {
        this.jiashao = jiashao;
    }

    public String getZhujitype() {
        return zhujitype;
    }

    public void setZhujitype(String zhujitype) {
        this.zhujitype = zhujitype;
    }

    public List<Map<String, String>> getGameList() {
        return gameList;
    }

    public void setGameList(List<Map<String, String>> gameList) {
        this.gameList = gameList;
    }

}
