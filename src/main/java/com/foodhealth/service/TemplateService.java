package com.foodhealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Template;

public interface TemplateService extends IService<Template> {
    Result getTemplates();
}
