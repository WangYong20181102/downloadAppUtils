package com.tool.library.v2;

import android.content.Context;

import androidx.annotation.Nullable;

import com.tool.library.core.http.AllenHttp;
import com.tool.library.utils.AllenEventBusUtil;
import com.tool.library.v2.builder.DownloadBuilder;
import com.tool.library.v2.builder.RequestVersionBuilder;
import com.tool.library.v2.builder.UIData;
import com.tool.library.v2.eventbus.AllenEventType;


public class AllenVersionChecker {

    private AllenVersionChecker() {

    }

    public static AllenVersionChecker getInstance() {
        return AllenVersionCheckerHolder.allenVersionChecker;
    }

    private static class AllenVersionCheckerHolder {
        public static final AllenVersionChecker allenVersionChecker = new AllenVersionChecker();
    }

    @Deprecated
    public void cancelAllMission(Context context) {
        cancelAllMission();
    }

    public void cancelAllMission() {
        AllenHttp.getHttpClient().dispatcher().cancelAll();
//        Intent intent = new Intent(context.getApplicationContext(), VersionService.class);
//        context.getApplicationContext().stopService(intent);
        AllenEventBusUtil.sendEventBusStick(AllenEventType.CLOSE);
        AllenEventBusUtil.sendEventBusStick(AllenEventType.STOP_SERVICE);
    }

    /**
     * @param versionBundle developer should return version bundle ,to use when showing UI page,could be null
     * @return download builder for download setting
     */
    public DownloadBuilder downloadOnly(@Nullable UIData versionBundle) {
        return new DownloadBuilder(null, versionBundle);
    }

    /**
     * use request version function
     *
     * @return requestVersionBuilder
     */
    public RequestVersionBuilder requestVersion() {
        return new RequestVersionBuilder();
    }

}
