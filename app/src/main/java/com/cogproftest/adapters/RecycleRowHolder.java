package com.cogproftest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cogproftest.R;

/*
 * Implementation of ViewHolder Class for reference to each UI elements
 */

public class RecycleRowHolder extends RecyclerView.ViewHolder {

    protected ImageView thumbnail;
    protected TextView title;
    protected TextView desc;

    public RecycleRowHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.tv_title);
        this.desc = (TextView) view.findViewById(R.id.tv_desc);
        this.thumbnail = (ImageView) view.findViewById(R.id.iv_cpimg);
    }

}
