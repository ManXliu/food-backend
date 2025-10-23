package cn.superbase.workflow.service.impl;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import cn.superbase.common.translation.annotation.TranslationType;
import cn.superbase.common.translation.core.TranslationInterface;
import cn.superbase.workflow.common.ConditionalOnEnable;
import cn.superbase.workflow.common.constant.FlowConstant;
import cn.superbase.workflow.service.IFlwCategoryService;
import org.springframework.stereotype.Service;

/**
 * 流程分类名称翻译实现
 *
 * @author AprilWind
 */
@ConditionalOnEnable
@Slf4j
@RequiredArgsConstructor
@Service
@TranslationType(type = FlowConstant.CATEGORY_ID_TO_NAME)
public class CategoryNameTranslationImpl implements TranslationInterface<String> {

    private final IFlwCategoryService flwCategoryService;

    @Override
    public String translation(Object key, String other) {
        return flwCategoryService.selectCategoryNameById(Convert.toLong(key));
    }
}
