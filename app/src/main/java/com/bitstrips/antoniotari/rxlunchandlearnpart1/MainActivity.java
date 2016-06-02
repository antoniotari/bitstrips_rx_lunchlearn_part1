package com.bitstrips.antoniotari.rxlunchandlearnpart1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.bitstrips.antoniotari.rxlunchandlearnpart1.chapters.Chapt1LambdaIntro;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.chapters.Chapt3AsyncRx;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.exceptions.NetworkException;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.models.Person;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.utils.Log;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.utils.Utils;

import rx.Observable;
import rx.functions.Func1;

import static com.bitstrips.antoniotari.rxlunchandlearnpart1.utils.Log.log;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textMain);
        mImageView = (ImageView) findViewById(R.id.imageView);

        // initialize the instances to use in this example
        Chapt1LambdaIntro chapt1LambdaIntro = new Chapt1LambdaIntro();
        Chapt3AsyncRx chapt3AsyncRx = new Chapt3AsyncRx();
        List<Person> people = Utils.createPeopleList();

        // for convenience and to keep the code clean I tested the void methods inside
        // their own classes, in the run() method
        log("example 1");
        chapt1LambdaIntro.run();

        // a runnable can be passed to threads or to handlers
        //new Thread(Chapt1LambdaIntro::new).start();

        // generate a string from the object list and put it in a text view
//        List<Person> personList = chapt1LambdaIntro.callPeople2(people,person -> person.getAge()>27);
//        StringBuilder stringBuilder = new StringBuilder();
//        for(Person person:personList) {
//            stringBuilder.append(person.toString());
//            stringBuilder.append("\n");
//        }
//        mTextView.setText(stringBuilder.toString());

        // generate a string from the object list and put it in a text view, better way
        chapt3AsyncRx.callPeople7(people, Utils::testPeopleOver27)
                .subscribe(mTextView::setText);

        chapt3AsyncRx.getImageforName("Eddie")
                .subscribe(mImageView::setImageBitmap);

        // error example
        chapt3AsyncRx.threadBlockingHttpRequestError()
                .subscribe(Log::log, this::onError);
    }

    private void onError(Throwable throwable) {
        if (throwable instanceof NetworkException) {
            // do something for network errors
            // you can cast to NetworkException and use it
            NetworkException networkException = (NetworkException) throwable;
            log(networkException.getError().getMessage());
        } else {
            // anything else
        }
    }

    private void exampleMulticalls() {
        Observable.from("url1", "url2", "url3")
                .take(5)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(final String s) {
                        return s != null;
                    }
                })
                .subscribe(url -> System.out.println(url));
    }
}
