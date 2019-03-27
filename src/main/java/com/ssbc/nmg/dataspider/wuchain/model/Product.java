package com.ssbc.nmg.dataspider.wuchain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@TableName(value="product")
@Data
public class Product extends Model<Product> {
    //产品名称
    private String productName;
    //产量类别
    private String category;
    //注册商标
    private String trademark;
    //批准产量
    private String approvedOutput;
    //标志编号
    private String markNumber;
    //有效期
    private String validity;


    @Override
    protected Serializable pkVal() {
        return this.getMarkNumber();
    }
}
