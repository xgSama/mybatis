package com.xgsama.ibatis.plugin;

/**
 * Page
 *
 * @author : xgSama
 * @date : 2021/12/23 19:35:04
 */
public class Page {
  private int total;
  private int size;
  private int index;

  public Page() {
  }

  public Page(int size, int index) {
    this.size = size;
    this.index = index;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getOffset() {
    return size * (index - 1);
  }

  @Override
  public String toString() {
    return "Page{" +
      "total=" + total +
      ", size=" + size +
      ", index=" + index +
      '}';
  }
}
