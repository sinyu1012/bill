package com.wecanstudio.xdsjs.save.Utils;

import android.widget.Toast;

import com.wecanstudio.xdsjs.save.MyApplication;

/**
 * Created by xdsjs on 2015/11/27.
 */
public class ToastUtils {
    public static void showToast(String msg) {
        Toast toast = Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
