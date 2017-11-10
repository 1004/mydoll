package com.fy.catchdoll.library.utils;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件下载
 * Created by yb on 2017/5/24.
 */
public class FileDownload extends AsyncTask<String, Integer, Integer> {
    public static final int SUCCESS = 200;
    public static final int FAIL = 100001;
    private String savePath = "";
    private OnFileDownloadListener onFileDownloadListener;

    public FileDownload(OnFileDownloadListener onFileDownloadListener) {
        this.onFileDownloadListener = onFileDownloadListener;
    }

    public void setOnFileDownloadListener(OnFileDownloadListener onFileDownloadListener) {
        this.onFileDownloadListener = onFileDownloadListener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String imageUrl = params[0];//网络图片地址
        savePath = params[1];//将图片保存在本地的地址
        FileOutputStream fileOutputStream = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            //本地存储准备
            File tempFile = new File(savePath);
            if (tempFile.exists()) {
                return SUCCESS;
            }
            tempFile = new File(savePath + ".tmp");//下载的临时文件
            File parentFile = tempFile.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                tempFile.getParentFile().mkdirs();
            }
            if (tempFile.exists()) {
                tempFile.delete();
            }
            tempFile.createNewFile();
            fileOutputStream = new FileOutputStream(tempFile);
            //网络连接
            URL url = new URL(imageUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(6000);
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setChunkedStreamingMode(0);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
            if (httpURLConnection.getResponseCode() != 200) {
                return httpURLConnection.getResponseCode();
            }
            inputStream = httpURLConnection.getInputStream();
            int fileSize = httpURLConnection.getContentLength();
            //准备写入本地
            byte[] buffer = new byte[1024];
            int length = 0;
            int countWrite = 0;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
                countWrite += length;
                publishProgress(countWrite, fileSize);//更新下载进度
            }
            fileOutputStream.flush();
            //将temp文件改为真正的文件
            File file = new File(savePath);
            tempFile.renameTo(file);
            return SUCCESS;
        } catch (Exception e) {

        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
        }
        return FAIL;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (onFileDownloadListener != null) {
            onFileDownloadListener.onUpdateProgress(values[0], values[1]);
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (SUCCESS == result) {
            if (onFileDownloadListener != null) {
                onFileDownloadListener.onDownloadSuccess(savePath);
            }
        } else {
            if (onFileDownloadListener != null) {
                onFileDownloadListener.onDownloadFail(result);
            }
        }
    }

    public interface OnFileDownloadListener {
        void onDownloadSuccess(String savePath);

        void onDownloadFail(Integer result);

        void onUpdateProgress(int downloadSize, int totalSize);
    }
}
