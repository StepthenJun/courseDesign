package com.foodhealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Record;

public interface RecordService extends IService<Record> {
    Result record(Long userId);
}
