package com.example.allen.fanandjson.object;

/**
 *
 * 影音專區首頁 - Banner
 *
 * Created by Allen on 2017/6/11.
 */

class Banner {

    public static final String PARAM_BANNER_ID = "bannerId";
    public static final String PARAM_KIND_ID = "kindId";
    public static final String PARAM_LOCATION_ID = "locationid";
    public static final String PARAM_IMG_URL = "imgUrl";

    private String bannerId;
    private String kindId;
    private String locationId;
    private String imageUrl;

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
