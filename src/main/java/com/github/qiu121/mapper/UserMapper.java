package com.github.qiu121.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qiu121.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author to_Geek
* @since 2023/12/16
* @version 1.0
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
