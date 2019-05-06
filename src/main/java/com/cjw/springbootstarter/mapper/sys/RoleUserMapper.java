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
import com.cjw.springbootstarter.domain.sys.TSysRoleUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 〈角色-用户对应表〉
 *
 * @author yao
 * @create 2019/1/22
 * @since 1.0.0
 */
public interface RoleUserMapper extends BaseMapper<TSysRoleUser> {

    @Select("select sys_role_id from t_sys_role_user where sys_user_id = #{userid}")
    List<Long> findRoleIdsByUserId(@Param("userid") long userId);
}
