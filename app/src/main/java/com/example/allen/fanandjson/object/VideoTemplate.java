package com.example.allen.fanandjson.object;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 影音專區首頁模板
 *
 * Created by Allen on 2017/6/11.
 */

public class VideoTemplate {

    public static final String PARAM_DATA_TYPE = "data_type";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_SUB_UNIT_ID = "subunitId";
    public static final String PARAM_ITEMS = "items";

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

    @Override
    public String toString() {
        return PARAM_DATA_TYPE + " = " + getType() + " , "
                + PARAM_TITLE + " = " + getTitle() + " , "
                + PARAM_SUB_UNIT_ID + " = " + getSubUnitId() + " , "
                + "item size = " + getItemSize();
    }

    public static ArrayList createData(String type, JSONArray jsonArray) {
        ArrayList<Object> list = new ArrayList<>();
        switch (type){
            case TYPE_BANNER:
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.optJSONObject(i);
                    Banner banner = new Banner();
                    banner.setBannerId(jsonObj.optString(Banner.PARAM_BANNER_ID));
                    banner.setKindId(jsonObj.optString(Banner.PARAM_KIND_ID));
                    banner.setLocationId(jsonObj.optString(Banner.PARAM_LOCATION_ID));
                    banner.setImageUrl(jsonObj.optString(Banner.PARAM_IMG_URL));
                    list.add(banner);
                }
                break;
            case TYPE_LIVE_INFO:
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.optJSONObject(i);
                    LiveInfo liveInfo = new LiveInfo();
                    liveInfo.setBannerId(jsonObj.optString(LiveInfo.PARAM_BANNER_ID));
                    liveInfo.setImgUrl(jsonObj.optString(LiveInfo.PARAM_IMG_URL));
                    liveInfo.setUrl(jsonObj.optString(LiveInfo.PARAM_URL));
                    list.add(liveInfo);
                }
                break;
            case TYPE_SUB_UNIT:
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.optJSONObject(i);
                    list.add(new SubUnit());
                }
                break;
        }
        return list;
    }

}
