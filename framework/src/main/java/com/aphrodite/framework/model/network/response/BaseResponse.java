package com.aphrodite.framework.model.network.response;

/**
 * Created by Aphrodite on 2019/5/20.
 */
public abstract class BaseResponse {
    private Boolean error;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

}
