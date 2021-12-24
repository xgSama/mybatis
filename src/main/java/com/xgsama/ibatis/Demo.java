package com.xgsama.ibatis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Demo
 *
 * @author : xgSama
 * @date : 2021/11/23 20:58:22
 */
public class Demo {
  public static void main(String[] args) {
    String sql = "select * from user where id=#{id} and name=#{name}";
    int start = 0, end = 0;
    start = sql.indexOf("#{", 0);
    end = sql.indexOf("}", start);
    System.out.println(sql.substring(start + 2, end));


  }
}
