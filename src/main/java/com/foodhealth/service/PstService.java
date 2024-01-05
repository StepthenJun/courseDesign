package com.foodhealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Pst;

public interface PstService extends IService<Pst> {
    Result getPst(Integer current);
}
