package com.tibame.group1.common.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Page<T> {

    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> results;
}
