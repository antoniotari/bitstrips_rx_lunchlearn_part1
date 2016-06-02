package com.bitstrips.antoniotari.rxlunchandlearnpart1.exceptions;

import com.bitstrips.antoniotari.rxlunchandlearnpart1.models.MockHttpResponse.Error;

/**
 * Created by antonio.tari on 6/2/16.
 */
public class NetworkException extends Exception {

    private Error mError;

    public NetworkException(Error error){
        super(error.getMessage());
        mError = error;
    }

    public Error getError() {
        return mError;
    }
}
