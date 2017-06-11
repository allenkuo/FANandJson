package com.example.allen.fanandjson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.allen.fanandjson.dummy.DummyContent;
import com.example.allen.fanandjson.object.VideoTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private static final String TAG = ItemListActivity.class.getSimpleName();
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        /* API solution one */
        AndroidNetworking.get("https://www.dropbox.com/s/nfb9g7mnnt1yqrm/video.json?raw=1")
        /* API solution two */
//        AndroidNetworking.get("https://www.dropbox.com/s/xljpcq7bpzig08y/new_video.json?raw=1")
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        ArrayList<VideoTemplate> list = new ArrayList<>();

                        /* parser solution one */
                        JSONArray jsonArray1 = response.optJSONArray("banners");
                        VideoTemplate template1 = new VideoTemplate();
                        template1.setType(VideoTemplate.TYPE_BANNER);
                        template1.setItems(VideoTemplate.createData(VideoTemplate.TYPE_BANNER, jsonArray1));
                        list.add(template1);

                        JSONObject jsonObj = response.optJSONObject("liveInfo");
                        VideoTemplate template2 = new VideoTemplate();
                        template2.setType(VideoTemplate.TYPE_LIVE_INFO);
                        template2.setTitle(jsonObj.optString("subunitTitle"));
                        template2.setSubUnitId(jsonObj.optString("subunitId"));
                        template2.setItems(VideoTemplate.createData(VideoTemplate.TYPE_LIVE_INFO, jsonObj.optJSONArray("lives")));
                        list.add(template2);

                        JSONArray jsonArray2 = response.optJSONArray("subUnits");
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject data = jsonArray2.optJSONObject(i);
                            VideoTemplate template = new VideoTemplate();
                            template.setType(VideoTemplate.TYPE_SUB_UNIT);
                            template.setTitle(data.optString("subunitTitle"));
                            template.setSubUnitId(data.optString("subunitId"));
                            template.setItems(VideoTemplate.createData(VideoTemplate.TYPE_SUB_UNIT, data.optJSONArray("subunitVideos")));
                            list.add(template);
                        }

                        /* parser solution two */
//                        JSONArray rawdata = response.optJSONArray("rawdata");
//
//                        for (int i = 0; i < rawdata.length(); i++) {
//
//                            JSONObject data = rawdata.optJSONObject(i);
//
//                            VideoTemplate videoTemplate = new VideoTemplate();
//
//                            videoTemplate.setType(data.optString(VideoTemplate.PARAM_DATA_TYPE));
//
//                            videoTemplate.setTitle(data.optString(VideoTemplate.PARAM_TITLE));
//
//                            videoTemplate.setSubUnitId(data.optString(VideoTemplate.PARAM_SUB_UNIT_ID));
//
//                            videoTemplate.setItems(VideoTemplate.createData(videoTemplate.getType(), data.optJSONArray(VideoTemplate.PARAM_ITEMS)));
//
//                            list.add(videoTemplate);
//                        }

                        for (VideoTemplate t : list) {
                            Log.d(TAG, t.toString());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d(TAG, "onError");
                        if (error.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
//                            ApiError apiError = error.getErrorAsObject(ApiError.class);
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
