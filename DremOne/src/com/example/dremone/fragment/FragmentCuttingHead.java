package com.example.dremone.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;
import com.example.dremone.R;
import com.example.http.UserHttp;

public class FragmentCuttingHead extends Fragment {
	private ImageView iv_cutting;
	private CropImageView cImageView;
	private ImageView image_button_cutting_left;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragement_cutting_head,
				container, false);
		init(view);
		listener();
		return view;
	}

	// 绑定单击事件
	private void listener() {
		iv_cutting.setOnClickListener(new CuttingOnClickListener());
		image_button_cutting_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
	}

	// 初始化方法
	private void init(View view) {
		iv_cutting = (ImageView) view.findViewById(R.id.iv_cutting);
		cImageView = (CropImageView) view.findViewById(R.id.ci_CropImageView);
		image_button_cutting_left = (ImageView) view
				.findViewById(R.id.image_button_cutting_left);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, 0);

	}

	/**
	 * 单击裁剪时
	 * 
	 * @author Administrator
	 *
	 */
	private class CuttingOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Bitmap bitmap = cImageView.getCroppedImage();// 得到裁剪好的图片
			// 将裁剪好的图片进行格式转换后保存
			String filename = bitmapToPng(bitmap);
			if (filename != null) {
				// 将头像上传到服务器
				UserHttp.uploadHeadImage(filename, getActivity());
				// 修改用户的头像Id
				Intent intentload = getActivity().getIntent();
				String id = intentload.getStringExtra("id");
				UserHttp.updateHeadImageId(id, filename, getActivity());
				// 设置返回值并关闭activity
				FragmentActivity activity = getActivity();
				Intent intent = new Intent();
				intent.putExtra("path", filename);
				activity.setResult(2, intent);
				activity.finish();
			}

		}
	}

	/**
	 * 由bitmap转为png
	 */
	private String bitmapToPng(Bitmap capturedBitmap) {
		String path = getActivity().getCacheDir().getPath().toString();
		OutputStream fOutputStream = null;
		String filename = System.currentTimeMillis() + ".png";// 头像名称
		File file = new File(path + "/", filename);

		try {
			file.createNewFile();
			fOutputStream = new FileOutputStream(file);
			capturedBitmap.compress(Bitmap.CompressFormat.PNG, 100,
					fOutputStream);
			fOutputStream.flush();
			fOutputStream.close();
			return filename;
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), "保存头像失败", Toast.LENGTH_SHORT).show();
		}

		return null;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 & data != null) {
			Uri uri = data.getData();
			Bitmap bitmap = null;

			try {
				bitmap = MediaStore.Images.Media.getBitmap(getActivity()
						.getContentResolver(), uri);
			} catch (Exception e) {
				Toast.makeText(getActivity(), "Uri转换为图片失败", Toast.LENGTH_SHORT)
						.show();
			}

			if (bitmap != null) {
				cImageView.setImageBitmap(bitmap);
				cImageView.setFixedAspectRatio(true);// 设置允许按比例截图，如果不设置就是默认的任意大小截图
				cImageView.setAspectRatio(1, 1);// 设置比例为一比一
				cImageView.setGuidelines(2);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
