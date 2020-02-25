package com.example.song.mapper;

import com.example.song.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    public void insert(User user);//这里是类对象，所以#{}会直接对应类属性
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//这里不是类对象，所以需要@Param注解

    @Select("select * from user where id = #{id}")
    User findByID(@Param("id") Integer id);
}

