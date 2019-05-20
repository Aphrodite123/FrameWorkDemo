package com.aphrodite.demo.model.network;

import com.aphrodite.demo.model.bean.BeautyBean;
import com.aphrodite.framework.model.network.response.BaseResponse;

import java.util.List;

/**
 * Created by Aphrodite on 2019/5/20.
 */
public class BeautyResponse extends BaseResponse {
    private List<BeautyBean> results;

    public List<BeautyBean> getResults() {
        return results;
    }

    public void setResults(List<BeautyBean> results) {
        this.results = results;
    }
}
