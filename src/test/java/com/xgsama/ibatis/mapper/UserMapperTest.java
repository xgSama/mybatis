package com.xgsama.ibatis.mapper;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.xgsama.ibatis.entity.User;
import com.xgsama.ibatis.util.DataSourceUtil;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

/**
 * UserMapperTest
 *
 * @author : xgSama
 * @date : 2021/11/20 20:27:09
 */
public class UserMapperTest {

  @Test
  public void test() throws Exception {

    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    Environment environment = new Environment("development", transactionFactory, DataSourceUtil.getMysqlDataSource());
    Configuration configuration = new Configuration(environment);
    configuration.addMapper(UserMapper.class);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    SqlSession sqlSession = sqlSessionFactory.openSession();

    User user = sqlSession.selectOne("com.xgsama.ibatis.mapper.UserMapper.getById", 1);

    System.out.println(user);
  }
}
