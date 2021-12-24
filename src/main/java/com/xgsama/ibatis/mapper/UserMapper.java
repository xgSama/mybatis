package com.xgsama.ibatis.mapper;

import com.xgsama.ibatis.entity.User;
import com.xgsama.ibatis.plugin.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * UserMapper
 *
 * @author : xgSama
 * @date : 2021/11/20 19:56:49
 */
@Mapper
@CacheNamespace
public interface UserMapper {
  User getById(Integer id);

  void updateName(Integer id, String name);

  List<User> getUsers(@Param("user") User user, Page page);

}
