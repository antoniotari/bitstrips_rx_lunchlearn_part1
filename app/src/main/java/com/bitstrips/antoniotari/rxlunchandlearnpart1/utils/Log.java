package com.bitstrips.antoniotari.rxlunchandlearnpart1.utils;

import rx.Observable;

/**
 * Created by antonio.tari on 6/2/16.
 */
public final class Log {

    private static final String TAG = "rxLunchLearn";

    public static void log(Object... objects) {
        if (objects == null || objects.length == 0) {
            d("nothing to log");
        }

        Observable.from(objects).subscribe(Log::d, Log::e);
    }

    public static void d(Object obj) {
        android.util.Log.d(TAG, obj.toString());
    }

    public static void e(Object obj) {
        android.util.Log.e(TAG, obj.toString());
    }
}
