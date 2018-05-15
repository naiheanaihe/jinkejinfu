package com.example.jinkejinfuapplication.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by naihe on 2017/10/27.
 */

public class MyhuatiBean {
    private String  id,datetime,name,clicknum,pic,title,likenum,content,reti;

    private List<Map<String,String>> gameList=new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClicknum() {
        return clicknum;
    }

    public void setClicknum(String clicknum) {
        this.clicknum = clicknum;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLikenum() {
        return likenum;
    }

    public void setLikenum(String likenum) {
        this.likenum = likenum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReti() {
        return reti;
    }

    public void setReti(String reti) {
        this.reti = reti;
    }

    public List<Map<String, String>> getGameList() {
        return gameList;
    }

    public void setGameList(List<Map<String, String>> gameList) {
        this.gameList = gameList;
    }
}
