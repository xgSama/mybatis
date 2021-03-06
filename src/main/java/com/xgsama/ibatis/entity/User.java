package com.xgsama.ibatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * User
 *
 * @author : xgSama
 * @date : 2021/11/20 19:59:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
  // id | username | address  | enabled | favorites
  private Integer id;
  private String username;
  private String address;
  private String enabled;
  private String favorites;

}
