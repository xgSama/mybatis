package com.xgsama.ibatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author
 *
 * @author : xgSama
 * @date : 2021/12/11 21:35:01
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Author implements Serializable {
  private Integer id;
  private String name;
  private Integer age;
}
