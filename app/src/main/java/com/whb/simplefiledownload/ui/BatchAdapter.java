package com.whb.simplefiledownload.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whb.simplefiledownload.R;
import com.whb.simplefiledownload.basedownload.DownloadListner;
import com.whb.simplefiledownload.managedownload.SimpleDownLoadInfo;
import com.whb.simplefiledownload.managedownload.SimpleDownloadManage;

import java.util.List;
import java.util.logging.Handler;

/**
 * Created by whb on 2018/10/17.
 * Email:18720982457@163.com
 */
public class BatchAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<SimpleDownLoadInfo> mLoadInfoList;

    public BatchAdapter(Context context, List<SimpleDownLoadInfo> loadInfoList) {
        this.mContext = context;
        this.mLoadInfoList = loadInfoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BitchViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_download, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        BitchViewHolder holder = (BitchViewHolder) viewHolder;
        final SimpleDownLoadInfo downLoadInfo = mLoadInfoList.get(i);
        if (holder.getSimpleDownLoadInfo() != null) {
            //移除监听
            SimpleDownloadManage.getInstance(mContext).removelisten(holder.getSimpleDownLoadInfo().getPrimaryKey());
        }
        holder.setSimpleDownLoadInfo(downLoadInfo);
        //添加监听
        SimpleDownloadManage.getInstance(mContext).registerlisten(downLoadInfo.getPrimaryKey(), holder);

        holder.btn_download2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(downLoadInfo.getState() == SimpleDownLoadInfo.DOWNLOADING) {
                    SimpleDownloadManage.getInstance(mContext).pause(downLoadInfo);
                } else {
                    SimpleDownloadManage.getInstance(mContext).start(downLoadInfo);
                }
            }
        });

        holder.btn_cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDownloadManage.getInstance(mContext).delete(downLoadInfo);
                notifyItemRemoved(mLoadInfoList.indexOf(downLoadInfo));
                mLoadInfoList.remove(downLoadInfo);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.e("whb3=======>", mLoadInfoList.size() + "");
        return mLoadInfoList == null ? 0 : mLoadInfoList.size();
    }

    class BitchViewHolder extends RecyclerView.ViewHolder implements DownloadListner {
        TextView tv_file_name2;
        TextView tv_progress2;
        ProgressBar pb_progress2;
        Button btn_download2;
        Button btn_cancel2;
        private SimpleDownLoadInfo simpleDownLoadInfo;

        public BitchViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_file_name2 = itemView.findViewById(R.id.tv_file_name2);
            tv_progress2 = itemView.findViewById(R.id.tv_progress2);
            pb_progress2 = itemView.findViewById(R.id.pb_progress2);
            btn_download2 = itemView.findViewById(R.id.btn_download2);
            btn_cancel2 = itemView.findViewById(R.id.btn_cancel2);
        }

        public SimpleDownLoadInfo getSimpleDownLoadInfo() {
            return simpleDownLoadInfo;
        }

        public void setSimpleDownLoadInfo(final SimpleDownLoadInfo simpleDownLoadInfo) {
            this.simpleDownLoadInfo = simpleDownLoadInfo;
            tv_file_name2.setText(simpleDownLoadInfo.getTitle());
            tv_progress2.setText(String.format("%.2f", simpleDownLoadInfo.getProgress() * 100) + "%");
            pb_progress2.setProgress((int) (simpleDownLoadInfo.getProgress() * 100));
            switch (simpleDownLoadInfo.getState()) {
                case SimpleDownLoadInfo.DOWNLOADING:
                    btn_download2.setText("暂停");
                    break;
                default:
                    btn_download2.setText("开始");
                    break;
            }

        }

        @Override
        public void onFinished() {

        }

        @Override
        public void onProgress(float progress) {
            tv_progress2.setText(String.format("%.2f", simpleDownLoadInfo.getProgress() * 100) + "%");
            pb_progress2.setProgress((int) (progress * 100));
            btn_download2.setText("暂停");
        }

        @Override
        public void onPause() {
            btn_download2.setText("开始");
        }

        @Override
        public void onCancel() {

        }
    }
}
