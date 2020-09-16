package com.example.mail.utils.json;

import java.util.List;

/**
 * create by March21.Sunny on 2018/10/26
 * 建立JSON数组类ListObject
 */
public class ListObject extends AbstractJSON {
    // 列表对象
    private List<?> data;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
