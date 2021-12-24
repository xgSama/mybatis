package com.xgsama.ibatis.mapper;

import com.xgsama.ibatis.entity.Author;
import com.xgsama.ibatis.plugin.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * AuthorMapper
 *
 * @author : xgSama
 * @date : 2021/12/11 21:36:15
 */
@Mapper
@CacheNamespace
public interface AuthorMapper {

  @Select("select * from author where id=#{1}")
  Author selectById(Integer id);

  @Select("select * from author where id=#{1}")
  @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE)
  Author selectByIdNoCache(Integer id);

  @Update("update author set age=#{arg1} where id=#{arg0}")
  void updateAgeById(Integer id, Integer age);

  @Select("select * from author where name=#{name}")
  Author selectByName(String name);

  @Select("select * from author where age > #{age}")
  List<Author> selectByPage(Page page, @Param("age") int age);


}
