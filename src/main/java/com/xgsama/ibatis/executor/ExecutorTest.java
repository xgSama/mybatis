package com.xgsama.ibatis.executor;

import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExecutorTest
 *
 * @author : xgSama
 * @date : 2021/12/10 23:01:32
 */
public class ExecutorTest {

  private Configuration configuration;
  private Transaction transaction;

  @Before
  public void init() throws Exception {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    configuration = sqlSessionFactory.getConfiguration();

    Connection connection = DriverManager.getConnection("jdbc:mysql://xgsama:3307/test?useSSL=false", "root", "cyz19980815");
    transaction = new JdbcTransaction(connection);
  }

  @Test
  public void simpleExecutor() throws SQLException {
    SimpleExecutor executor = new SimpleExecutor(configuration, transaction);

    MappedStatement ms = configuration.getMappedStatement("com.xgsama.ibatis.mapper.UserMapper.getById");
    executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
    executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
  }

  @Test
  public void reuseExecutor() throws SQLException {
    ReuseExecutor executor = new ReuseExecutor(configuration, transaction);

    MappedStatement ms = configuration.getMappedStatement("com.xgsama.ibatis.mapper.UserMapper.getById");
    List<Object> objects = executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
    executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));

  }

  @Test
  public void batchExecutor() throws Exception {
    BatchExecutor executor = new BatchExecutor(configuration, transaction);
    MappedStatement ms = configuration.getMappedStatement("com.xgsama.ibatis.mapper.UserMapper.updateName");
    Map<String, Object> param = new HashMap<>();
    param.put("arg0", 10);
    param.put("arg1", "mybatis2");

    executor.doUpdate(ms, param);
    executor.doUpdate(ms, param);

    executor.commit(true);
  }
}
