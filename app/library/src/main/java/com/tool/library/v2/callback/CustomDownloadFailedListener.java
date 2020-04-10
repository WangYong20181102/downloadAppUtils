package com.tool.library.v2.callback;

import android.app.Dialog;
import android.content.Context;

import com.tool.library.v2.builder.UIData;


public interface CustomDownloadFailedListener {
    Dialog getCustomDownloadFailed(Context context, UIData versionBundle);

}
