package com.keyao.no006sensitivedata.demos.web.entity;

import com.keyao.no006sensitivedata.demos.web.anno.Desensitize;
import com.keyao.no006sensitivedata.demos.web.constant.DesensitizeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarInfoVO {
    // 车牌号
    @Desensitize(type = DesensitizeType.LICENSE_NUMBER)
    private String licenseNumber;

    // 手机号
    @Desensitize(type = DesensitizeType.MOBILE_PHONE)
    private String ownerMobile;

    // 身份证号
    @Desensitize(type = DesensitizeType.ID_CARD)
    private String ownerIdCard;

    // 银行卡
    @Desensitize(type = DesensitizeType.BANK_CARD)
    private String ownerBankCardNo;

    // 自定义
    @Desensitize(type = DesensitizeType.CUSTOM, startIndex = 0, endIndex = 1)
    private String ownerName;
}
