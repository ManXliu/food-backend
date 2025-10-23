package cn.superbase.demo.mapper;

import cn.superbase.common.mybatis.annotation.DataColumn;
import cn.superbase.common.mybatis.annotation.DataPermission;
import cn.superbase.common.mybatis.core.mapper.BaseMapperPlus;
import cn.superbase.demo.domain.TestTree;
import cn.superbase.demo.domain.vo.TestTreeVo;

/**
 * 测试树表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTree, TestTreeVo> {

}
