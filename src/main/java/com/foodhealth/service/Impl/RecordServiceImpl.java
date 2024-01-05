package com.foodhealth.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Record;
import com.foodhealth.mapper.RecordMapper;
import com.foodhealth.service.RecordService;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
    @Override
    public Result record(Long userId) {
//        先获取表里的这个用户
        Record user = query()
                .eq("user_id", userId)
                .one();
//        判断是否浏览过这个主题
        if (user == null){
//            如果没有就创建并且记录
            Record record = new Record();
            record.setUserId(userId)
                    .setSkimNumber(1L);
            save(record);
            return Result.ok();
        }else {
//        如果有就更新它的浏览相关主题的数量
            user.setSkimNumber(user.getSkimNumber() + 1);
            update().setSql("skim_number = skim_number + 1")
                    .eq("user_id",userId)
                    .update();
            return Result.ok();
        }
    }
}
