package com.example.jinkejinfuapplication.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by naihe on 2017/10/27.
 */

public class MyliuyinBean {
    private String  id,datetime,name,replynum,pic,title,like,message;

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

    public String getReplynum() {
        return replynum;
    }

    public void setReplynum(String replynum) {
        this.replynum = replynum;
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

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Map<String, String>> getGameList() {
        return gameList;
    }

    public void setGameList(List<Map<String, String>> gameList) {
        this.gameList = gameList;
    }
}
