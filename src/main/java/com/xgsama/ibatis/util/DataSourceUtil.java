package com.xgsama.ibatis.util;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

/**
 * DataSourceUtil
 *
 * @author : xgSama
 * @date : 2021/11/20 20:28:03
 */
public class DataSourceUtil {

  public static DataSource getMysqlDataSource() {
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setURL(DataSourceConfig.MYSQL_URL);
    dataSource.setUser(DataSourceConfig.MYSQL_USERNAME);
    dataSource.setPassword(DataSourceConfig.MYSQL_PASSWORD);

    return dataSource;
  }

}
