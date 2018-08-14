package com.ssbc.nmg.dataspider.entity;

import com.ssbc.nmg.dataspider.dao.Agency;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class JsonList<T> {
    private List<T> dataSource;
    private int total;
}
