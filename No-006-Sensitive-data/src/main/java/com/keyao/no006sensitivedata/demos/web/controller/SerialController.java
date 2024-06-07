package com.keyao.no006sensitivedata.demos.web.controller;

import com.keyao.no006sensitivedata.demos.web.entity.CarInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//  如何优雅的实现数据脱敏？
@RestController
public class SerialController {

    @GetMapping("/car/info")
    public CarInfoVO queryCarInfo() {
        CarInfoVO carInfoVO = new CarInfoVO();
        carInfoVO.setLicenseNumber("陕DB2890");
        carInfoVO.setOwnerMobile("17391870040");
        carInfoVO.setOwnerIdCard("610424200001010007");
        carInfoVO.setOwnerBankCardNo("6214856213978533");
        carInfoVO.setOwnerName("小明");
        return carInfoVO;
    }
}
