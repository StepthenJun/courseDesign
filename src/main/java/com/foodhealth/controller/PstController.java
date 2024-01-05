package com.foodhealth.controller;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Pst;
import com.foodhealth.service.PstService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pst")
@Api(tags ="科普的接口")
public class PstController {

    @Autowired
    private PstService pstService;
//分页查询
    @ApiOperation("查看科普")
    @GetMapping
    public Result getPst(@RequestParam(value = "current", defaultValue = "1") Integer current){
        return pstService.getPst(current);
    }
//    根据模糊查询对应的内容
    @ApiOperation("根据种类查询科普内容")
    @PostMapping("/search")
    public Result search(@RequestParam String name){
        List<Pst> psts = pstService.query()
                .like("name", name)
                .list();
        return Result.ok(psts);
    }
//    生成查询树
    @ApiOperation("科普的种类")
    @GetMapping("/type")
    private Result type(){
        List<Pst> types = pstService.query().orderByAsc("id").list();
        return Result.ok(types);
    }
}
