package com.cogproftest.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cogproftest.R;
import com.cogproftest.adapters.CustomRecycleListAdapter;
import com.cogproftest.common.CpAppConstants;
import com.cogproftest.model.CpModel;
import com.cogproftest.model.CpModelRows;
import com.cogproftest.network.CustomVolleyRequestQueue;
import com.cogproftest.network.GsonRequest;

import java.util.ArrayList;
import java.util.Collections;


/*
 * Implementation of MainActivity Class for the Application.
 */
public class CpMainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RequestQueue rQueue;
    private RecyclerView lv_cpList;
    private SwipeRefreshLayout sw_refreshList;
    CustomRecycleListAdapter cpAdapter;
    private TextView tv_error_text;
    private ProgressBar loding_pb;
    private Context mCtx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getSupportActionBar().setTitle("");
        mCtx=this.getApplicationContext();
        lv_cpList=(RecyclerView)findViewById(R.id.recycler_view);
        lv_cpList.setLayoutManager(new LinearLayoutManager(this));
        loding_pb=(ProgressBar)findViewById(R.id.progressBar1);
        tv_error_text=(TextView)findViewById(R.id.tv_error_text);
        sw_refreshList=(SwipeRefreshLayout)findViewById(R.id.lySwipeRefresh);
        sw_refreshList.setOnRefreshListener(this);
        sw_refreshList.setColorScheme(android.R.color.holo_blue_light, android.R.color.white, android.R.color.holo_blue_light,android.R.color.white);

    }

    @Override
    protected void onStart() {
        super.onStart();
        newServiceCall();

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
    public void onRefresh() {

        newServiceCall();

    }

    /*
     *  Custom GSONRequest Calling
     */
    void newServiceCall() {

        rQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        GsonRequest gsonRequest = new GsonRequest(CpAppConstants.KEY_URL, CpModel.class, null, new Response.Listener<CpModel>() {

            @Override
            public void onResponse(CpModel cpModelVal) {

                getSupportActionBar().setTitle(cpModelVal.getTitle());
                loding_pb.setVisibility(View.GONE);
                cpModelVal.getCpModelRows().removeAll(Collections.singleton(null));
                if (cpModelVal.getCpModelRows().size() > 0) {
                    loding_pb.setVisibility(View.GONE);
                    // Intialize RecyclerList Adapter
                    ArrayList<CpModelRows> localRows = new ArrayList<CpModelRows>();

                    for(int i=0;i<cpModelVal.getCpModelRows().size();i++) {
                        if (!ContainsAllNulls(cpModelVal.getCpModelRows().get(i))) {
                            localRows.add(cpModelVal.getCpModelRows().get(i));
                        }
                    }
                    cpAdapter = new CustomRecycleListAdapter(mCtx, localRows);
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError != null) {
                    if(loding_pb !=null && loding_pb.getVisibility()==View.VISIBLE) {
                        loding_pb.setVisibility(View.GONE);
                    }
                    tv_error_text.setVisibility(View.VISIBLE);
                    tv_error_text.setText(getResources().getString(R.string.no_data));
                }

            }
        });

        /* Add the request to the RequestQueue. */
        rQueue.add(gsonRequest);
    }


    public static Boolean ContainsAllNulls(CpModelRows rows)
    {
        if(rows.getTitle()==null && rows.getDescription()==null&&rows.getImageUrl()==null) {
            return true;
        }
        return false;
    }


}
