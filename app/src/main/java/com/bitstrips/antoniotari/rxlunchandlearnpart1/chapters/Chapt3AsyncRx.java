package com.bitstrips.antoniotari.rxlunchandlearnpart1.chapters;

import android.graphics.Bitmap;

import java.util.List;

import com.bitstrips.antoniotari.rxlunchandlearnpart1.exceptions.NetworkException;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.models.MockHttpResponse;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.models.Person;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.utils.Utils;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by antonio.tari on 6/2/16.
 */
public class Chapt3AsyncRx extends Chapt2ReactiveIntro {

    public Observable<MockHttpResponse> threadBlockingHttpRequest() {
        return Observable.create(new OnSubscribe<MockHttpResponse>() {

            @Override
            public void call(final Subscriber<? super MockHttpResponse> subscriber) {
                try {
                    Thread.sleep(2000);
                    subscriber.onNext(Utils.createMockHttpResponse());
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    subscriber.onError(e);
                }
            }
        }).filter(mockHttpResponse -> mockHttpResponse.getCode() == 200)
                .subscribeOn(Schedulers.io());
    }

    public Observable<MockHttpResponse> threadBlockingHttpRequestError() {
        return Observable.create(new OnSubscribe<MockHttpResponse>() {

            @Override
            public void call(final Subscriber<? super MockHttpResponse> subscriber) {
                try {
                    Thread.sleep(2000);
                    MockHttpResponse mockHttp = Utils.createMockHttpResponseError();
                    if (mockHttp.getCode() != 200) {
                        throw new NetworkException(mockHttp.getError());
                    }
                    subscriber.onNext(mockHttp);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<List<Person>> threadBlockingHttpRequestList() {
        return threadBlockingHttpRequest()
                .flatMap(mockHttpResponse -> Observable.just(mockHttpResponse.getPeople()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * gets users with a certain name
     */
    public Observable<Person> getPeopleWithName(String name) {
        return threadBlockingHttpRequestList()
                .flatMap(Observable::from)
                .filter(person -> person.getName().equals(name));
    }

    /**
     * gets image for user  with a certain name
     */
    public Observable<Bitmap> getImageforName(String name) {
        return getPeopleWithName(name)
                .elementAt(0)
                .flatMap(person -> Observable.just(person.getLink()))
                .flatMap(Utils::getBitmapFromURL);
    }

    /**
     * gets image for user  with a certain name
     */
    public Observable<Bitmap> streamExample(String name) {
        return threadBlockingHttpRequest()
                .flatMap(mockHttpResponse -> Observable.just(mockHttpResponse.getPeople()))
                .flatMap(Observable::from)
                .filter(person -> person.getName().equals(name))
                .elementAt(0)
                .flatMap(person -> Observable.just(person.getLink()))
                .flatMap(Utils::getBitmapFromURL);
    }
}
