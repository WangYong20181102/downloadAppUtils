package com.tool.library.v2.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.tool.library.core.http.AllenHttp;
import com.tool.library.core.http.HttpRequestMethod;
import com.tool.library.v2.AllenVersionChecker;
import com.tool.library.v2.builder.DownloadBuilder;
import com.tool.library.v2.builder.RequestVersionBuilder;
import com.tool.library.v2.builder.UIData;
import com.tool.library.v2.callback.RequestVersionListener;

import java.io.IOException;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestVersionManager {
//    private boolean isCanceled=false;
    private Handler handler = new Handler(Looper.getMainLooper());
    public static RequestVersionManager getInstance() {
        return Holder.instance;
    }

    public static class Holder {
        static RequestVersionManager instance = new RequestVersionManager();

    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void receiveEvent(CommonEvent commonEvent) {
//        if(commonEvent.getEventType()== AllenEventType.CLOSE){
//           isCanceled=true;
//        }
//    }
//
//    private void registerEventBus() {
//        isCanceled=false;
//        if(!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);
//    }
//    private void unregister(){
//
//        if(EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().unregister(this);
//    }

    /**
     * 请求版本接口
     * #issue 239
     */
    public void requestVersion(final DownloadBuilder builder, final Context context) {
//        registerEventBus();
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                RequestVersionBuilder requestVersionBuilder = builder.getRequestVersionBuilder();
                OkHttpClient client = AllenHttp.getHttpClient();
                HttpRequestMethod requestMethod = requestVersionBuilder.getRequestMethod();
                Request request = null;
                switch (requestMethod) {
                    case GET:
                        request = AllenHttp.get(requestVersionBuilder).build();
                        break;
                    case POST:
                        request = AllenHttp.post(requestVersionBuilder).build();
                        break;
                    case POSTJSON:
                        request = AllenHttp.postJson(requestVersionBuilder).build();
                        break;
                }
                final RequestVersionListener requestVersionListener = requestVersionBuilder.getRequestVersionListener();
                if (requestVersionListener != null) {
                    try {
                        final Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            final String result = response.body() != null ? response.body().string() : null;
                              post(new Runnable() {
                                  @Override
                                  public void run() {

                                          UIData versionBundle = requestVersionListener.onRequestVersionSuccess(builder, result);
                                          if (versionBundle != null) {
                                              builder.setVersionBundle(versionBundle);
                                              builder.download(context);
                                          }
                                      }


                              });
                        } else {
                          post(new Runnable() {
                                @Override
                                public void run() {
                                    requestVersionListener.onRequestVersionFailure(response.message());
                                    AllenVersionChecker.getInstance().cancelAllMission();
                                }
                            });
                        }
                    } catch (final IOException e) {
                        e.printStackTrace();
                        post(new Runnable() {
                            @Override
                            public void run() {
                                requestVersionListener.onRequestVersionFailure(e.getMessage());
                                AllenVersionChecker.getInstance().cancelAllMission();
                            }
                        });
                    }
                } else {
                    throw new RuntimeException("using request version function,you must set a requestVersionListener");
                }
//                unregister();
            }
        });

    }
    private void post(Runnable r){
        handler.post(r);
    }
}
