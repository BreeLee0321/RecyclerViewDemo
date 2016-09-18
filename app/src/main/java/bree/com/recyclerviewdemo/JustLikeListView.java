package bree.com.recyclerviewdemo;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class JustLikeListView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.adapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                showToast("onItemClick "+position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                showToast("onItemLongClick "+position);

            }
        });
        Configuration newConfig=getResources().getConfiguration();
        switch (newConfig.orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL));
        }
    }

    @Override
    public void setRecycler() {
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        switch (getIntent().getStringExtra("layout")) {
            case "1":
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                break;
            case "2":
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
                break;
            case "3":
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL));
                break;
        }



    }
}
