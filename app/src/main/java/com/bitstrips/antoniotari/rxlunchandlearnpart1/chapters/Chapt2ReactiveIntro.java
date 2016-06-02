package com.bitstrips.antoniotari.rxlunchandlearnpart1.chapters;

import java.util.ArrayList;
import java.util.List;

import com.android.internal.util.Predicate;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.interfaces.FunctionalInterface1;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.models.Person;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.utils.Log;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.utils.Utils;

import rx.Observable;

import static com.bitstrips.antoniotari.rxlunchandlearnpart1.utils.Utils.createPeopleList;

/**
 * Created by antonio.tari on 6/2/16.
 */
public class Chapt2ReactiveIntro implements Runnable {

    /**
     * prints a list of people that matches the condition of the predicate, an interface provided by internal.util using observables
     */
    public void callPeople3(List<Person> people, Predicate<Person> predicate) {
        Observable.from(people)
                .filter(person -> predicate.apply(person))
                .subscribe(Log::log);
    }

    public void callPeople4(List<Person> people, FunctionalInterface1<Person> predicate) {
        Observable.from(people)
                .filter(predicate::test)
                .subscribe(Log::log);
    }

    /**
     * bad way of returning a list
     */
    public List<Person> callPeople5(List<Person> people, FunctionalInterface1<Person> predicate) {
        List<Person> resultList = new ArrayList<>();
        Observable.from(people)
                .filter(predicate::test)
                .doOnNext(person -> resultList.add(person))
                .subscribe();
        return resultList;
    }

    /**
     * right way of returning a list
     */
    public Observable<List<Person>> callPeople6(List<Person> people, FunctionalInterface1<Person> predicate) {
        return Observable.from(people)
                .filter(predicate::test)
                .toList();
    }

    /**
     * return a String of concatenated person objects
     */
    public Observable<String> callPeople7(List<Person> people, FunctionalInterface1<Person> predicate) {
        return Observable.from(people)
                .filter(predicate::test)
                .reduce("", (s, person) -> s + "\n" + person.toString());
    }

    /**
     * return a String of concatenated person objects
     */
    public Observable<String> callPeople7Better(List<Person> people, FunctionalInterface1<Person> predicate) {
        return callPeople6(people, predicate)
                .reduce("", (s, person) -> s + "\n" + person.toString());
    }

    @Override
    public void run() {
        List<Person> people = createPeopleList();
        callPeople3(people, person -> person.getAge() > 27);
        callPeople4(people, Utils::testPeopleOver27);
    }
}
