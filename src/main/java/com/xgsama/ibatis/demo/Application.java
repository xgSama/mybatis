package com.xgsama.ibatis.demo;

import com.xgsama.ibatis.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Application
 *
 * @author : xgSama
 * @date : 2021/11/23 20:58:45
 */
public class Application {
  public static void main(String[] args) {

    UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(Application.class.getClassLoader(), new Class<?>[]{UserMapper.class}, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Select annotation = method.getAnnotation(Select.class);

        if (annotation != null) {
          String[] value = annotation.value();
          String sql = parseSql(value[0], buildArgsMap(method, args));
        }
        return null;
      }
    });

    userMapper.selectOne(1, "a");
  }

  public static String parseSql(String sql, Map<String, Object> argsMap) {
    for (Map.Entry<String, Object> arg : argsMap.entrySet()) {
      String name = arg.getKey();
      sql = sql.replace("#{" + name + "}", "'" + arg.getValue().toString() + "'");
    }
    sql += ";";
    return sql;
  }

  public static Map<String, Object> buildArgsMap(Method method, Object[] args) {
    Map<String, Object> argsMap = new HashMap<>();
    Parameter[] parameters = method.getParameters();
    if (parameters.length != args.length) {
      throw new RuntimeException("error");
    }

    for (int i = 0; i < parameters.length; i++) {
      Parameter parameter = parameters[i];
      argsMap.put(parameters[i].getName(), args[i]);
      Param annotation = parameter.getAnnotation(Param.class);
      if (annotation != null) {
        String value = annotation.value();
        argsMap.put(value, args[i]);
      }
    }
    return argsMap;
  }

}

interface UserMapper {

  @Select("select * from user")
  List<User> selectUserList();

  @Select("select * from user where id=#{id} and name=#{name}")
  User selectOne(@Param("id") Integer id, @Param("name") String name);
}
