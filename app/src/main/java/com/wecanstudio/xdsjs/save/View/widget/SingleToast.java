package com.wecanstudio.xdsjs.save.View.widget;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.wecanstudio.xdsjs.save.R;


/**
 * 显示吐司
 * Created by xdsjs on 15/8/13.
 */
public class SingleToast {

    Context mContext;
    Toast mToast;

    public SingleToast(Context mContext) {
        this.mContext = mContext;
        mToast = new Toast(mContext);
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
    }

    public void showBottomToast(String msg) {
        mToast.setText(msg);
        mToast.setGravity(Gravity.BOTTOM, 0, mContext.getResources().getDimensionPixelOffset(R.dimen.toast_y));
        mToast.show();
    }

    public void showBottomToast(int messageId) {
        mToast.setText(messageId+"");
        mToast.setGravity(Gravity.BOTTOM, 0, mContext.getResources().getDimensionPixelOffset(R.dimen.toast_y));
        mToast.show();
    }

    public void showMiddleToast(int id) {
        mToast.setText(id+"");
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public void showMiddleToast(String msg) {
        mToast.setText(msg);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }


}
