/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserMapper
 * Author:   yao
 * Date:     2019/1/22 15:54
 * Description: 用户信息查询
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjw.springbootstarter.domain.sys.TSysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 〈用户信息查询〉
 *
 * @author yao
 * @create 2019/1/22
 * @since 1.0.0
 */
public interface UserMapper extends BaseMapper<TSysUser> {


    /**
     * 这是一个测试多表查询的接口
     *
     * @param page 分页查询
     * @return 多表关联查询
     */
    @Select("select perRole.id, role_id,permission_id,name,descripion,url,pid,perms,type,icon,order_num  " +
            "from t_sys_permission_role perRole left join t_sys_premission per " +
            "on perRole.permission_id=per.id where role_id in  " +
            "(select sys_role_id from t_sys_role_user where sys_user_id=#{id})")
    List<Map<String, Object>> testMutiTableGetUserList(Page<Map<String, Object>> page, @Param("id") long id);

}
