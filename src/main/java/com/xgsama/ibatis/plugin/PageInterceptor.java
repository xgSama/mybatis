package com.xgsama.ibatis.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * 分页拦截器
 *
 * @author : xgSama
 * @date : 2021/12/23 19:38:33
 */
@Intercepts(@Signature(
  type = StatementHandler.class,
  method = "prepare",
  args = {Connection.class, Integer.class})
)
public class PageInterceptor implements Interceptor {
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    /*
     * 不做任何处理的方式:
     * invocation.proceed() =
     * invocation.getMethod().invoke(invocation.getTarget(), invocation.getArgs());
     */

    // 1.检测当前是否满足分页条件: 带有分页参数
    StatementHandler handler = (StatementHandler) invocation.getTarget();
    BoundSql sql = handler.getBoundSql();
    Object parameterObject = sql.getParameterObject();
    Page page = null;
    if (parameterObject instanceof Page) {
      page = ((Page) parameterObject);
    } else if (parameterObject instanceof Map) {
      page = (Page) ((Map<?, ?>) parameterObject).values().stream()
        .filter(v -> v instanceof Page)
        .findFirst().orElse(null);
    }

    if (page == null) {
      return invocation.proceed();
    }

    // 2.设置总行数: select count(*) from user
    int count = selectCount(invocation);
    page.setTotal(count);
    System.out.println("总行数: " + count);

    // 3.修改原有sql: select * from ser offset 0, limit 50
    String newSql = String.format("%s limit %s offset %s", sql.getSql(), page.getSize(), page.getOffset());
    SystemMetaObject.forObject(sql).setValue("sql", newSql);

    return invocation.getMethod().invoke(invocation.getTarget(), invocation.getArgs());
  }

  private int selectCount(Invocation invocation) throws SQLException {
    StatementHandler target = (StatementHandler) invocation.getTarget();
    String countSql = String.format("select count(*) from ( %s ) as _page", target.getBoundSql().getSql());

    Connection connection = (Connection) invocation.getArgs()[0];
    PreparedStatement preparedStatement = connection.prepareStatement(countSql);
    target.getParameterHandler().setParameters(preparedStatement);
    ResultSet resultSet = preparedStatement.executeQuery();

    int count = -1;
    if (resultSet.next()) {
      count = resultSet.getInt(1);
    }

    resultSet.close();
    preparedStatement.close();

    return count;
  }

  @Override
  public Object plugin(Object target) {
    return Interceptor.super.plugin(target);
  }


  @Override
  public void setProperties(Properties properties) {
    Interceptor.super.setProperties(properties);
  }
}
