package com.xgsama.ibatis.cache;

import com.xgsama.ibatis.entity.Author;
import com.xgsama.ibatis.mapper.AuthorMapper;
import com.xgsama.ibatis.mapper.UserMapper;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * SecondCacheTest
 *
 * @author : xgSama
 * @date : 2021/12/11 17:19:43
 */
public class SecondCacheTest {

  private Configuration configuration;
  private SqlSessionFactory factory;

  @Before
  public void init() throws Exception {
    SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
    factory = factoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
    configuration = factory.getConfiguration();
  }

  @Test
  public void test() {
    Cache cache = configuration.getCache("com.xgsama.ibatis.mapper.AuthorMapper");
    Author user = new Author(1, "cyz", 12);
    cache.putObject("test", user);
    cache.getObject("test");
  }

  @Test
  public void testCache1() {
    SqlSession session1 = factory.openSession();
    Object o = session1.selectOne("com.xgsama.ibatis.mapper.UserMapper.getById", 1);
    System.out.println(o);

  }


  @Test
  public void testCache() throws InterruptedException {
    SqlSession session1 = factory.openSession(true);
    AuthorMapper mapper1 = session1.getMapper(AuthorMapper.class);
    mapper1.selectById(1);

//    session1.commit();

    SqlSession session2 = factory.openSession(true);
    AuthorMapper mapper2 = session2.getMapper(AuthorMapper.class);
    mapper2.selectById(1);
  }

  @Test
  public void testNoUseCache() {
    SqlSession session1 = factory.openSession();
    AuthorMapper mapper1 = session1.getMapper(AuthorMapper.class);
    mapper1.selectByIdNoCache(1);
    session1.commit();

    SqlSession session2 = factory.openSession();
    AuthorMapper mapper2 = session2.getMapper(AuthorMapper.class);
    mapper2.selectByIdNoCache(1);
  }

  @Test
  public void testCacheNameSpace() {
    SqlSession session = factory.openSession(true);
    session.getMapper(AuthorMapper.class).selectById(1);
    session.getMapper(UserMapper.class).getById(1);
    System.out.println();

  }

  @Test
  public void testUpdateClearCache() {
    SqlSession sqlSession = factory.openSession();

    AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
    mapper.selectById(1);
    mapper.selectByName("曹雪芹");

    sqlSession.commit();

    mapper.updateAgeById(1,12);
    sqlSession.commit();

    System.out.println("--------------------");



    SqlSession sqlSession2 = factory.openSession();

    AuthorMapper mapper2 = sqlSession2.getMapper(AuthorMapper.class);
    mapper2.selectById(1);
    mapper2.selectByName("曹雪芹");

  }


}









