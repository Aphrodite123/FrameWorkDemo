package com.aphrodite.demo.model.network;

import com.aphrodite.demo.model.bean.BeautyBean;
import com.aphrodite.framework.model.network.response.BaseResponse;

import java.util.List;

/**
 * Created by Aphrodite on 2019/5/20.
 */
public class BeautyResponse extends BaseResponse {
    private List<BeautyBean> data;

    public List<BeautyBean> getData() {
        return data;
    }

    public void setData(List<BeautyBean> data) {
        this.data = data;
    }
}
