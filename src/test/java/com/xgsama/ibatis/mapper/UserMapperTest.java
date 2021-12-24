package com.xgsama.ibatis.mapper;

import com.xgsama.ibatis.entity.User;
import com.xgsama.ibatis.plugin.Page;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * UserMapperTest
 *
 * @author : xgSama
 * @date : 2021/11/20 20:27:09
 */
public class UserMapperTest {

  public Configuration configuration;
  public SqlSessionFactory factory;


  @Before
  public void init() throws IOException {
    SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
    factory = factoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
    configuration = factory.getConfiguration();

  }

  @Test
  public void testPagePlugin() {
    SqlSession sqlSession = factory.openSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    User u = new User();
    u.setId(1);
    List<User> users = mapper.getUsers(u, new Page(2, 1));

    for (User user : users) {
      System.out.println(user);
    }
    sqlSession.commit();

    SqlSession sqlSession2 = factory.openSession();
    UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);

    User u2 = new User();
    u2.setId(1);
    List<User> users2 = mapper.getUsers(u2, new Page(2, 1));

    for (User user : users2) {
      System.out.println(user);
    }


    System.out.println();
  }
}
