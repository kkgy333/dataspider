package com.ssbc.nmg.dataspider.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
    @Override
    protected Serializable pkVal() {
        return this.getId();
    }
}
