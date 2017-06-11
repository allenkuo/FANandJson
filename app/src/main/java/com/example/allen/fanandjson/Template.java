package com.example.allen.fanandjson;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 *
 * Created by Allen on 2017/6/11.
 */

class Template {

    public static final String TYPE_BANNER = "banner";
    public static final String TYPE_LIVE_INFO = "liveInfo";
    public static final String TYPE_SUB_UNIT = "subUnit";
    private String type;
    private String title;
    private String subUnitId;
    private ArrayList items;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSubUnitId(String subUnitId) {
        this.subUnitId = subUnitId;
    }

    public String getSubUnitId() {
        return subUnitId;
    }

    public void setItems(ArrayList items) {
        this.items = items;
    }

    public int getItemSize() {
        return items == null ? 0 : items.size();
    }

    public static ArrayList createData(String type, JSONArray jsonArray) {
        ArrayList<Object> list = new ArrayList<>();
        switch (type){
            case TYPE_BANNER:
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(new Banner());
                }
                break;
            case TYPE_LIVE_INFO:
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(new LiveInfo());
                }
                break;
            case TYPE_SUB_UNIT:
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(new SubUnit());
                }
                break;
        }
        return list;
    }

}
