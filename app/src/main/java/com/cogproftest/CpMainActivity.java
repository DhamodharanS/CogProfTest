package com.cogproftest;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request;
import com.google.gson.GsonBuilder;
import com.cogproftest.CpModel;
import com.cogproftest.CpModelRows;
import org.json.JSONObject;
import android.util.Log;
import java.util.ArrayList;

public class CpMainActivity extends ActionBarActivity implements Response.Listener,Response.ErrorListener , SwipeRefreshLayout.OnRefreshListener{

    private RequestQueue rQueue;
    private ListView lv_cpList;
    private SwipeRefreshLayout sw_refreshList;
    CustomListAdapter cpAdapter;
    private TextView tv_error_text;
    private ProgressBar loding_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getSupportActionBar().setTitle("");
        lv_cpList=(ListView)findViewById(R.id.lv_cplist);
        loding_pb=(ProgressBar)findViewById(R.id.progressBar1);
        tv_error_text=(TextView)findViewById(R.id.tv_error_text);
        sw_refreshList=(SwipeRefreshLayout)findViewById(R.id.lySwipeRefresh);
        sw_refreshList.setOnRefreshListener(this);
        sw_refreshList.setColorScheme(android.R.color.holo_blue_light, android.R.color.white, android.R.color.holo_blue_light,android.R.color.white);

    }

    @Override
    protected void onStart() {
        super.onStart();
        serviceCall();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cp_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Object obj) {

        try {
            /*
            Paring JSON Response using GSON Method
             */
            CpModel cpModelVal = new GsonBuilder().create().fromJson(obj.toString(), CpModel.class);
            getSupportActionBar().setTitle(cpModelVal.getTitle());
            loding_pb.setVisibility(View.GONE);
            if (cpModelVal.getCpModelRows().size() > 0) {
                loding_pb.setVisibility(View.GONE);
                cpAdapter = new CustomListAdapter(this, cpModelVal.getCpModelRows());
                sw_refreshList.setVisibility(View.VISIBLE);
                lv_cpList.setAdapter(cpAdapter);
            }
            else {
                tv_error_text.setVisibility(View.VISIBLE);
                tv_error_text.setText(getResources().getString(R.string.no_data));
            }

            if (sw_refreshList.isRefreshing())
                sw_refreshList.setRefreshing(false);

        }
        catch (Exception e){
            Log.wtf("Service Exception", e.toString());
        }

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

        if(volleyError.networkResponse == null) {
            loding_pb.setVisibility(View.GONE);
            tv_error_text.setVisibility(View.VISIBLE);
            tv_error_text.setText(getResources().getString(R.string.no_data));

        }


    }

    @Override
    public void onRefresh() {

        serviceCall();

    }

    /*
       making service call Request by using Volley
     */

    void serviceCall() {

        rQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = "https://dl.dropboxusercontent.com/u/746330/facts.json";
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .GET, url,
                new JSONObject(), this, this);
        rQueue.add(jsonRequest);
    }


}
