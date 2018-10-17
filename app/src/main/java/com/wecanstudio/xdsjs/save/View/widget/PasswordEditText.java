package com.wecanstudio.xdsjs.save.View.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.wecanstudio.xdsjs.save.R;


/**
 * 自定义实现四位密码框
 * Created by xdsjs on 2015/10/19.
 */
public class PasswordEditText extends EditText {

    public static final int MODE_SET_PASSWORD = 1;//设置密码模式
    public static final int MODE_CHECK_PASSWORD = 2;//验证密码模式

    private int currentMode = MODE_CHECK_PASSWORD;//当前模式，默认为验证密码
    private int passwordLength;//密码的长度
    private int passwordColor; //密码的颜色
    private float passwordRadius;//密码半径

    private String pwd;//密码

    private String pwdFirst;//第一次输入的密码
    private String pwdSecond;//第二次输入的密码

    private int textlength;

    private Paint passwordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private OnSetPwdListener onSetPwdListener;
    private OnCheckPwdListener onCheckPwdListener;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        passwordLength = 4;
        passwordColor = getResources().getColor(R.color.top_title_color);
        passwordRadius = 15.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        //中心点坐标
        float px;
        float py = height / 2;
        float half = width / passwordLength / 2;
        passwordPaint.setStyle(Paint.Style.STROKE);
        passwordPaint.setColor(passwordColor);
        for (int i = 0; i < passwordLength; i++) {
            if (i + 1 <= textlength) {
                passwordPaint.setStyle(Paint.Style.FILL);
            } else {
                passwordPaint.setStyle(Paint.Style.STROKE);
            }
            px = width * i / passwordLength + half;
            canvas.drawCircle(px, py, passwordRadius, passwordPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textlength = text.toString().length();
        invalidate();
        if (this.textlength == 4) {
            switch (currentMode) {
                case MODE_CHECK_PASSWORD:
                    if (text.toString().equals(pwd)) {
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PasswordEditText.this.setText("");
                                onCheckPwdListener.onCheckSuccess();
                            }
                        }, 500);
                    } else {
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PasswordEditText.this.setText("");
                                onCheckPwdListener.onCheckFail();
                            }
                        }, 500);
                    }
                    break;
                case MODE_SET_PASSWORD:
                    if (TextUtils.isEmpty(pwdFirst)) {
                        pwdFirst = text.toString();
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PasswordEditText.this.setText("");
                                onSetPwdListener.onSetPwdFirst();
                            }
                        }, 500);
                        return;
                    } else if (pwdSecond == null) {
                        pwdSecond = text.toString();
                        if (pwdFirst.equals(pwdSecond)) {
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    PasswordEditText.this.setText("");
                                    onSetPwdListener.onSetPwdSuccess(pwdFirst);
                                    pwdFirst = null;
                                    pwdSecond = null;
                                }
                            }, 500);
                        } else {
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    PasswordEditText.this.setText("");
                                    onSetPwdListener.onSetPwdFail();
                                    pwdFirst = null;
                                    pwdSecond = null;
                                }
                            }, 500);
                        }
                    }
            }
        }
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }

    public void setOnSetPwdListener(OnSetPwdListener onSetPwdListener) {
        this.onSetPwdListener = onSetPwdListener;
    }

    public void setOnCheckPwdListener(OnCheckPwdListener onCheckPwdListener) {
        this.onCheckPwdListener = onCheckPwdListener;
    }

    public interface OnSetPwdListener {
        void onSetPwdFirst();

        void onSetPwdFail();

        void onSetPwdSuccess(String pwd);
    }

    public interface OnCheckPwdListener {
        void onCheckSuccess();

        void onCheckFail();
    }
}
