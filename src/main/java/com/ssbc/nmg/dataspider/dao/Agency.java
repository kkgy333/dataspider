package com.ssbc.nmg.dataspider.dao;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@TableName(value="Agency")
public class Agency extends Model<Agency> {
    private static final long serialVersionUID = 1L;
    @TableId(value="id", type= IdType.INPUT)
    private String ID;
    //private String REMOTEID;
    private String AGEINSNAME;
    private String AGEINSTYPENAME;
    private String ECOTYPENAME;
    private String AREACODE;
    private String AREANAME;
    private String TEL;

    @Override
    protected Serializable pkVal() {
        return this.getID();
    }
}




//        "ID":"2f2c8e3938cc4adfaac5c4f3364e68c7",
//        "AGEINSID":"80",
//        "AGEINSORGCODE":"75666860-9",
//        "AGEINSNAME":"内蒙古存信招标有限责任公司",
//        "AGEINSTYPECODE":"231",
//        "AGEINSTYPENAME":"社会代理机构",
//        "ECOTYPECODE":"9",
//        "ECOTYPENAME":"其他",
//        "SUPERADMINSNAME":null,
//        "AREACODE":"150100",
//        "AREANAME":"呼和浩特市",
//        "ADMDIVCODE":"150000",
//        "ADMDIVNAME":"内蒙古自治区",
//        "OPEADD":"内蒙古呼和浩特市赛罕区大学东街亚辰商务中心14楼",
//        "ZIP":"010010",
//        "TEL":"18647158578",
//        "FAX":"0471-4675101",
//        "EMAIL":"nmcxzb@163.com",
//        "UNITLOG":"d:/gpmis-exec/agent/75666860-9/basInfo/unitLog.jpg",
//        "INDACOMMREGADD":"内蒙古呼和浩特市赛罕区大学东街亚辰商务中心14楼",
//        "REGFUNAMOUT":"1000",
//        "MAJORSCOPE":"工程招标代理服务；政府采购招标代理服务、政府采购咨询服务；中央投资项目招标代理服务；国际招标代理服务；药品、医疗器械招标代理服务。",
//        "CONCSCOPE":null,
//        "FULLTIMEPERNUM":"39",
//        "TECHSECSCHABOPERTOTAL":"39",
//        "INTETITABOPERTOTAL":"20",
//        "PAYTAXMARK":"1",
//        "PAYSECFUNMARK":"1",
//        "LASTTHROPERCOND":null,
//        "LASTHRYEAILLRECMARK":"0",
//        "OFFICEBUIDAREA":"1363",
//        "OFFICEEQUI":null,
//        "OFFICECOND":null,
//        "ENTARTATTACH":null,
//        "INTERNALINSTIATTACH":null,
//        "UNITINTRODUCTIONATTACH":"d:/gpmis-exec/agent/75666860-9/basInfo/unitIntroductionAttach.pdf",
//        "AGETYPECODE":null,
//        "AGETYPENAME":null,
//        "PURRELPERSTATECODE":null,
//        "PURRELPERSTATENAME":null,
//        "estdate":"2002-12-08",
//        "REGDATE":"2012-08-15 00:00:00",
//        "REMARK":"A188",
//        "subdate":"2018-02-07",
//        "SUBUSERCODE":"130008",
//        "SUBUSERNAME":"孙小岩（专家）",
//        "SUBSTATUS":"1",
//        "httpurl":null,
//        "filepath":null,
//        "releasetime":"0000-00-00 00:00:00",
//        "release":null,
//        "old_type":null,
//        "wp_status":"-2",
//        "wp_arc_content":null,
//        "wp_arc_id":"0",
//        "wp_mark_id":"19",
//        "publishStatus":"-2",
//        "createddate":"2018-02-07 16:42:14",
//        "ishistory":"0"