package com.xgsama.ibatis;

import com.xgsama.ibatis.entity.Author;
import com.xgsama.ibatis.mapper.AuthorMapper;
import com.xgsama.ibatis.mapper.UserMapper;
import com.xgsama.ibatis.plugin.Page;
import com.xgsama.ibatis.plugin.PageInterceptor;
import com.xgsama.ibatis.util.DataSourceUtil;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * PageTest
 *
 * @author : xgSama
 * @date : 2021/12/23 20:07:28
 */
public class PageTest {

  Configuration configuration = null;
  SqlSessionFactory sqlSessionFactory = null;

  @Before
  public void init() {
    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    Environment environment = new Environment("development", transactionFactory, DataSourceUtil.getMysqlDataSource());
    configuration = new Configuration(environment);
    configuration.addMapper(AuthorMapper.class);
    configuration.addInterceptor(new PageInterceptor());
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
  }

  @Test
  public void find() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
    Page page = new Page(2, 2);
    List<Author> authors = mapper.selectByPage(page, 20);

    for (Author author : authors) {
      System.out.println(author);
    }

    System.out.println("返回数量: " + authors.size());
    System.out.println("总数: " + page.getTotal());
  }
}
