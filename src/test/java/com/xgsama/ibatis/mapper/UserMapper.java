package com.xgsama.ibatis.mapper;

import com.xgsama.ibatis.entity.User;

import java.util.List;
import java.util.Map;

/**
 * UserMapper
 *
 * @author : xgSama
 * @date : 2021/11/20 19:56:49
 */
public interface UserMapper {
  User getById(Integer id);

}
