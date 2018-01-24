package com.qike.telecast.presentation.presenter.update2;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;


import com.qike.telecast.presentation.application.CdApplication;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by xky on 16/6/22.
 */
public class UploadTask extends AsyncTask<String,Long,String>{
    private boolean isSuccess ;
    public UploadTask(){
        isSuccess = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        UploadManager.getInstance().notifyListener(UploadManager.UploadState.STATE);
    }

    @Override
    protected String doInBackground(String... strings) {
        if (strings != null && strings.length>0){
            String apk_url = strings[0];
            String path = download(apk_url);
            return path;
        }
        return "";
    }

    private String download(String apk_url) {
        int retry = 3;
        int count = 0;
        while (count < retry) {
            count += 1;
            try {
                String response = realDownload(apk_url);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                if (count < retry) {
                    Log.i("test","重试");
                } else {
                }
            }
        }

        return "";
    }

    private String realDownload(String downloadUrl) throws Exception {
        long totalSize = 0;
        long downloadedSize = 0;
        OutputStream out = null;
        InputStream in = null;
        try {
            File apkDir = new File(CdApplication.mCacheApkDir + File.separator + "apk");
            if (!apkDir.exists()) // 如果目标目录不存在，则创建
                apkDir.mkdirs();
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1, downloadUrl.length());
            File file = new File(apkDir, fileName);
            if (file.exists())
                file.delete();

            DefaultHttpClient client = new DefaultHttpClient();
            HttpParams httpParams = new BasicHttpParams();
            // 连接超时
            HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
            // 服务器响应超时
            HttpConnectionParams.setSoTimeout(httpParams, 10000);
            client.setParams(httpParams);
            HttpGet httpGet = new HttpGet(downloadUrl);
            HttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 请求服务器成功
                in = response.getEntity().getContent();
                out = new FileOutputStream(file);
                totalSize = response.getEntity().getContentLength();
                downloadedSize = 0;
                byte buffer[] = new byte[1024];
                int bufferLength = 0;
                int currentProgress = 0;
                long previousRefreshTime = 0;
                while ((bufferLength = in.read(buffer)) > 0) {
                    if (isCancelled()) {
                        if (file != null && file.exists())
                            file.delete();
                        return null;
                    }
                    out.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                    currentProgress = (int) (((float) downloadedSize / (float) totalSize) * 100);
                    long currentRefreshTime = System.currentTimeMillis();
                    // 防止频繁刷新,导致UI线程阻塞
                    if (currentRefreshTime - previousRefreshTime >= 512 || currentProgress == 100) {
                        previousRefreshTime = currentRefreshTime;
                        publishProgress(totalSize,downloadedSize,Long.valueOf(currentProgress+""));
                    }
                }

            } else {
            }

            return file.getAbsolutePath();
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        long total = values[0];
        long size = values[1];
        int progress = Integer.valueOf(values[2]+"");
        UploadManager.getInstance().notifyListener(UploadManager.UploadState.PROGRESS,total,size,progress);
        if (progress == 100){
            isSuccess = true;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (TextUtils.isEmpty(s) || !isSuccess){
            UploadManager.getInstance().notifyListener(UploadManager.UploadState.ERROR,"失败");
        }else {
            UploadManager.getInstance().notifyListener(UploadManager.UploadState.FINISH,s);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        UploadManager.getInstance().notifyListener(UploadManager.UploadState.CANCEL);
    }

    @SuppressLint("NewApi")
	@Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        UploadManager.getInstance().notifyListener(UploadManager.UploadState.CANCEL);
    }
}
