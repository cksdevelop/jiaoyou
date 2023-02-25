package com.cks.sso.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cks.sso.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}