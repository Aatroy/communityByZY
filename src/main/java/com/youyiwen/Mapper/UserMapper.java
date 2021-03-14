package com.youyiwen.Mapper;

import com.youyiwen.Bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: zhaoyang
 * @Date: 2021/03/04
 */
@Mapper
public interface UserMapper {
    @Select("select password from user where userName = #{userName} ")
    public String searchPasswordByName(String userName);

    @Select("select role from user where userName = #{userName}")
    public String searchRole(String userName);

    @Select("select * from user where userName = #{userName} ")
    public User searchUserByName(String userName);

    @Insert("insert into user (`userName`,`password`,`fullName`,`telephone`,`city`,`job`,`gender`,`Email`,`role`) " +
            "values (#{userName},#{password},#{fullName},#{telephone},#{city},#{job},#{gender},#{Email},#{role})")
    public int insertUser(User user);

}
