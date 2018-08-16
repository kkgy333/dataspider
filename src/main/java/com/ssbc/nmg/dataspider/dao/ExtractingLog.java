package com.ssbc.nmg.dataspider.dao;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
@TableName(value="extracting_log")
public class ExtractingLog extends Model<ExtractingLog> {
    private static final long serialVersionUID = 1L;
    @TableId(value="id", type= IdType.AUTO)
    private int id;
    //private String REMOTEID;
    @TableField(value = "agency_id")
    private String agencyId;
    @TableField(value = "ageins_name")
    private String ageinsName;
    @TableField(value = "extract_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date extractTime;


    @Override
    protected Serializable pkVal() {
        return this.getId();
    }
}
