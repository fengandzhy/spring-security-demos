package org.zhouhy.jwt.mapper;

import org.apache.ibatis.annotations.Param;
import org.zhouhy.jwt.domain.UserPojo;

public interface UserMapper {
    UserPojo queryByUserName(@Param("userName") String userName);
}
