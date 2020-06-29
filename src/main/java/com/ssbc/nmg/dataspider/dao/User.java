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
@TableName(value="sys_user")
public class User extends Model<User> {
    private static final long serialVersionUID = 1L;
    @TableId(value="id", type= IdType.AUTO)
    private int id;
    //private String REMOTEID;
    @TableField(value = "account")
    private String userName;
    @TableField(value = "password")
    private String passWord;

    @Override
    protected Serializable pkVal() {
        return this.getId();
    }
}
