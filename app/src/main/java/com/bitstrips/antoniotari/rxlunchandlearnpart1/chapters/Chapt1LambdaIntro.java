package com.bitstrips.antoniotari.rxlunchandlearnpart1.chapters;

import java.util.ArrayList;
import java.util.List;

import com.android.internal.util.Predicate;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.interfaces.FunctionalInterface1;
import com.bitstrips.antoniotari.rxlunchandlearnpart1.models.Person;

import static com.bitstrips.antoniotari.rxlunchandlearnpart1.utils.Log.log;
import static com.bitstrips.antoniotari.rxlunchandlearnpart1.utils.Utils.createPeopleList;

/**
 * Created by antonio.tari on 6/2/16.
 */
public class Chapt1LambdaIntro implements Runnable {

    /**
     * prints a list of people that matches the condition of our interface
     */
    public void callPeople1(List<Person> people, FunctionalInterface1<Person> predicate) {
        for (Person person : people) {
            if (predicate.test(person)) {
                log(person);
            }
        }
    }

    /**
     * prints a list of people that matches the condition of the predicate, an interface provided by internal.util
     */
    public List<Person> callPeople2(List<Person> people, Predicate<Person> predicate) {
        List<Person> resultList = new ArrayList<>();
        for (Person person : people) {
            if (predicate.apply(person)) {
                resultList.add(person);
            }
        }
        return resultList;
    }

    @Override
    public void run() {
        List<Person> people = createPeopleList();

        callPeople1(people, new FunctionalInterface1<Person>() {
            @Override
            public boolean test(final Person person) {
                return person.getAge() > 27;
            }
        });

//        callPeople1(people, person -> person.getAge() > 27);
//        callPeople1(people, Utils::testPeopleOver27);
    }
}
