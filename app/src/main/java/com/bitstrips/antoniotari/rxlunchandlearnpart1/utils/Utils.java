package com.bitstrips.antoniotari.rxlunchandlearnpart1.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.bitstrips.antoniotari.rxlunchandlearnpart1.models.MockHttpResponse;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.models.MockHttpResponse.Error;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.models.Person;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by antonio.tari on 6/2/16.
 */
public class Utils {

    public static boolean testPeopleOver27(Person person) {
        return person.getAge() > 27;
    }

    /**
     * @return a list of Person for testing
     */
    public static List<Person> createPeopleList() {
        return Arrays.asList(new Person("Jimmy", "Page", 25),
                new Person("Robert", "Plant", 23),
                new Person("John Paul", "Jones", 24),
                new Person("John", "Bonham", 27),
                new Person("Eddie", "Van Halen", 31),
                new Person("David Lee", "Roth", 28),
                new Person("Alex", "Van Halen", 34),
                new Person("Michael", "Anthony", 26));
    }

    public static MockHttpResponse createMockHttpResponse() {
        return new MockHttpResponse(200, createPeopleList());
    }

    public static MockHttpResponse createMockHttpResponseError() {
        MockHttpResponse mockHttpResponse = new MockHttpResponse(400, null);
        mockHttpResponse.setError(new Error("random network error", "400"));
        return mockHttpResponse;
    }

    public static Observable<Bitmap> getBitmapFromURL(final String src) {
        return Observable.create(new OnSubscribe<Bitmap>() {
            @Override
            public void call(final Subscriber<? super Bitmap> subscriber) {
                try {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onNext(myBitmap);
                } catch (Exception e) {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onError(e);
                    Log.e(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
