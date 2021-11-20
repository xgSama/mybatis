package com.xgsama.ibatis;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.xgsama.ibatis.entity.User;
import com.xgsama.ibatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import java.io.InputStream;

/**
 * AlphaTest
 *
 * @author : xgSama
 * @date : 2021/11/19 18:59:56
 */
public class AlphaTest {

  @Test
  public void testXml() throws Exception {
    String resource = "org/mybatis/example/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
  }

  @Test
  public void test() throws Exception {
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setURL("jdbc:mysql://xgsama:3307/test");
    dataSource.setUser("root");
    dataSource.setPassword("cyz19980815");


    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    Environment environment = new Environment("development", transactionFactory, dataSource);
    Configuration configuration = new Configuration(environment);
    configuration.addMapper(UserMapper.class);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    SqlSession sqlSession = sqlSessionFactory.openSession();

    User user = sqlSession.selectOne("com.xgsama.ibatis.mapper.UserMapper.getById", 1);

    System.out.println(user);
  }
}
