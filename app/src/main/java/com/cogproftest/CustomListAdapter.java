package com.cogproftest;

import android.widget.BaseAdapter;

/**
 * Created by Krishna on 01/05/15.
 */


import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<CpModelRows> cpModelItems;

    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;


    public CustomListAdapter(Activity activity, List<CpModelRows> cpModelItems) {
        this.activity = activity;
        this.cpModelItems = cpModelItems;
        this.inflater = ( LayoutInflater )activity.
                getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        options = new DisplayImageOptions.Builder()
                .showStubImage(android.R.drawable.ic_delete)
                .showImageForEmptyUri(android.R.drawable.ic_delete).cacheInMemory()
                .cacheOnDisc().build();



    }

    @Override
    public int getCount() {
        return cpModelItems.size();
    }

    @Override
    public Object getItem(int location) {
        return cpModelItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.lv_cplist_item,null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_description=(TextView)convertView.findViewById(R.id.tv_desc);
            holder.iv_cpImg=(ImageView)convertView.findViewById(R.id.iv_cpimg);
            convertView.setTag( holder );
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(cpModelItems.get(position).getTitle());
        holder.tv_description.setText(cpModelItems.get(position).getDescription());

        if(cpModelItems.get(position).getImageUrl()!=null)
        imageLoader.displayImage(cpModelItems.get(position).getImageUrl(), holder.iv_cpImg, options);
        return convertView;
    }

    public static class ViewHolder{

        public TextView tv_title;
        public TextView tv_description;
        public ImageView iv_cpImg;


    }

}


