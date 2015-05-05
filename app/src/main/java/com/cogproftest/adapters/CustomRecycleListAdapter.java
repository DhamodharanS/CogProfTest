package com.cogproftest.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.cogproftest.R;
import com.cogproftest.model.CpModelRows;
import com.cogproftest.network.CustomVolleyRequestQueue;

import java.util.List;


/*
 *  Custom Adapter for RecycleView
 */

public class CustomRecycleListAdapter extends RecyclerView.Adapter<RecycleRowHolder> {

    private Context ctx;
    private List<CpModelRows> cpModelItems;
    ImageLoader mImageLoader;


    public CustomRecycleListAdapter(Context activity, List<CpModelRows> cpModelItems) {
        this.ctx = activity;
        this.cpModelItems = cpModelItems;
        mImageLoader = CustomVolleyRequestQueue.getInstance(ctx).getImageLoader();
    }

    @Override
    public RecycleRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lv_cplist_item, null);
        if(cpModelItems.get(i).getTitle()==null && cpModelItems.get(i).getDescription() == null && cpModelItems.get(i).getImageUrl()==null) {
            Log.wtf("Logging","Logging");
           v.setVisibility(View.GONE);
           return null;
        }
        else {
            v.setVisibility(View.VISIBLE);
            RecycleRowHolder mh = new RecycleRowHolder(v);
            return mh;
        }

    }

    @Override
    public void onBindViewHolder(RecycleRowHolder cpRowHolder, int i) {
        CpModelRows cpItem = cpModelItems.get(i);

        if(cpItem.getTitle()!=null) {
            cpRowHolder.title.setText(cpItem.getTitle());
        }
        if(cpItem.getDescription()!=null) {
            cpRowHolder.desc.setText(cpItem.getDescription());
        }
        if(cpItem.getImageUrl()!=null) {
            mImageLoader.get(cpItem.getImageUrl(), ImageLoader.getImageListener(cpRowHolder.thumbnail,
                    android.R.drawable.ic_dialog_alert, android.R.drawable.ic_delete));
        }
    }

    @Override
    public int getItemCount() {
        return (cpModelItems != null ? cpModelItems.size() : 0);
    }

}
