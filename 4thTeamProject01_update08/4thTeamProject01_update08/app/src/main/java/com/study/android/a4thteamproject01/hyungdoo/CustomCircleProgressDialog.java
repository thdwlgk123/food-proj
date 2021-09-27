package com.study.android.a4thteamproject01.hyungdoo;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.study.android.a4thteamproject01.R;

public class CustomCircleProgressDialog extends Dialog {

    private TextView progressCntTv;
    private TextView progressTextTv;

          public CustomCircleProgressDialog(@NonNull Context context)
        {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_customcircleprogressdialog);
        }

}