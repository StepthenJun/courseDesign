package com.foodhealth.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foodhealth.constants.SystemConstants;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Pst;
import com.foodhealth.mapper.PstMapper;
import com.foodhealth.service.PstService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PstServiceImpl extends ServiceImpl<PstMapper, Pst> implements PstService {
    @Override
    public Result getPst(Integer current) {
        Page<Pst> page = query()
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        List<Pst> pstList = page.getRecords();
        return Result.ok(pstList);
    }
}
