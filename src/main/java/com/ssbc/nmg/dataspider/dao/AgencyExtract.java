package com.ssbc.nmg.dataspider.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class AgencyExtract extends Agency<AgencyExtract> {
    @TableField(value = "extract_time")
    private Date extractTime;

    @TableField(value = "extract_id")
    private String extractId;
}
