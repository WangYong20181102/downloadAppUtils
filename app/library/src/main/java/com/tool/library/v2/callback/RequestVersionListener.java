package com.tool.library.v2.callback;

import androidx.annotation.Nullable;

import com.tool.library.v2.builder.DownloadBuilder;
import com.tool.library.v2.builder.UIData;

public interface RequestVersionListener {
    /**
     * @param result the result string of request
     * @return developer should return version bundle ,to use when showing UI page,could be null
     */
    @Nullable
    UIData onRequestVersionSuccess(DownloadBuilder downloadBuilder, String result);

    void onRequestVersionFailure(String message);

}
