package com.bitstrips.antoniotari.rxlunchandlearnpart1.models;

import java.util.List;

/**
 * Created by antonio.tari on 6/2/16.
 */
public class MockHttpResponse {

    private int code;
    private List<Person> people;
    private Error error;

    public MockHttpResponse(final int code, final List<Person> people) {
        this.code = code;
        this.people = people;
    }

    public int getCode() {
        return code;
    }

    public List<Person> getPeople() {
        return people;
    }

    public Error getError() {
        return error;
    }

    public void setError(final Error error) {
        this.error = error;
    }

    public static class Error {
        String errorMessage;
        String errorCode;
        Object whatever;

        public Error(final String errorMessage, final String errorCode) {
            this.errorMessage = errorMessage;
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return errorMessage;
        }
    }
}
