package com.ssbc.nmg.dataspider.wuchain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@TableName(value="enterprise")
@Data
public class Enterprise extends Model<Enterprise> {
    //企业中文名
    private String enterpriseNameCn;
    //企业英文名
    private String enterpriseNameEn;
    //企业信息码
    private String infocode;
    //组织机构代码
    private String organizationCode;
    //邮编
    private String zipCode;
    //联系电话
    private String telephoneNumber;
    //联系人
    private String contactPerson;
    //地址
    private String address;
    //邮箱
    private String email;
    //网站
    private String webSite;
    //区域
    private String region;



    @Override
    protected Serializable pkVal() {
        return this.getOrganizationCode();
    }
}
