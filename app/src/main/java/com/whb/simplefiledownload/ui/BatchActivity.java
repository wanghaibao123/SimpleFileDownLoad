package com.whb.simplefiledownload.ui;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.whb.simplefiledownload.R;
import com.whb.simplefiledownload.managedownload.DownParameter;
import com.whb.simplefiledownload.managedownload.SimpleDownLoadInfo;
import com.whb.simplefiledownload.managedownload.SimpleDownloadManage;

import java.util.ArrayList;
import java.util.List;

public class BatchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<DownParameter> list = new ArrayList<>();
    private List<SimpleDownLoadInfo> simpleDownLoadInfos = new ArrayList<>();
    BatchAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch);
        recyclerView = findViewById(R.id.recyclerview);
        initData();
        initView();
    }

    public void initData() {
        List list = SimpleDownloadManage.getInstance(this).getDownloadInfos();
        if(list !=null && list.size()>0) {
            simpleDownLoadInfos.addAll(list);
        }
    }

    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BatchAdapter(this,simpleDownLoadInfos);
        recyclerView.setAdapter(adapter);
    }

    public void onAllStart(View view) {
        SimpleDownloadManage.getInstance(this).startAll();
    }

    public void onAllPause(View view) {
        SimpleDownloadManage.getInstance(this).pauseAll();
    }

    public void onAllDelete(View view) {
        SimpleDownloadManage.getInstance(this).deleteAll();
        List list = SimpleDownloadManage.getInstance(this).getDownloadInfos();
        simpleDownLoadInfos.clear();
        simpleDownLoadInfos.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void onAddDownload(View view) {
        list.clear();
        for(int i = 0; i < 50; i++) {
            list.add(new DownParameter("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk"));
        }
        SimpleDownloadManage.getInstance(this).addDownload(list);
        List list = SimpleDownloadManage.getInstance(this).getDownloadInfos();
        simpleDownLoadInfos.clear();
        if(list !=null && list.size()>0) {
            simpleDownLoadInfos.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }
}
