package com.example.allen.fanandjson.object;

/**
 * 影音專區首頁 - 直播速報
 *
 * Created by Allen on 2017/6/11.
 */

class LiveInfo {

    public static final String PARAM_BANNER_ID = "bannerId";
    public static final String PARAM_IMG_URL = "imgUrl";
    public static final String PARAM_URL = "url";

    private String bannerId;
    private String imgUrl;
    private String url;

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
