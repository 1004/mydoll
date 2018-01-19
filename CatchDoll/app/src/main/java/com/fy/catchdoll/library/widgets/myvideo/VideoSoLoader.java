package com.fy.catchdoll.library.widgets.myvideo;


import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 
 *<p>用来解决so文件加载的</p><br/>
 *<p>解决无法加载so</p>
 * @since 1.0.0
 * @author xky
 */
public class VideoSoLoader {
	public static VideoSoLoader INSTANCE = null;
	private Context mContext;

	private VideoSoLoader(Context context) {
		mContext = context;
	}

	public static VideoSoLoader getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new VideoSoLoader(context);
		}
		return INSTANCE;
	}

	/**
	 * 
	 *<p>开始异步加载so</p><br/>
	 *<p>TODO(详细描述)</p>
	 * @since 1.0.0
	 * @author xky
	 */
	public void startLoad() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				readFile();
			}
		}.start();
	}

	private void readFile() {
		String path = "/data/data/com.qike.telecast/files/vinit.so";
		String fileName = "vinit.so"; //文件名字
		File file = new File(path);
		if (file.exists()) {
			System.load(path);
//			boolean initialize = Vitamio.initialize(mContext);
			boolean initialize = true;
		} else {
			try {
				InputStream in = mContext.getResources().getAssets().open(fileName);
				FileOutputStream fout = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);

				int length = in.available();

				byte[] buffer = new byte[length];

				in.read(buffer);
				fout.write(buffer);
				//		res = EncodingUtils.getString(buffer, "UTF-8");     
				in.close();
				fout.close();
				File mFile = new File(path);
				if(mFile.exists()){
					System.load(path);
//					boolean initialize = Vitamio.initialize(mContext);
				}
			} catch (Exception e) {

				e.printStackTrace();

			}
		}
	}

}
