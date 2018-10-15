package com.lush.lushPlayer.modles;

import org.springframework.stereotype.Component;

@Component
public class Pagination {

  /**
   * Data Total Row Count.
   */
  private long total;

  /**
   * Maximum expression per page.
   */
  private int per_page;

  /**
   * Current Page Number.
   */
  private int current_page;

  /**
   * Last Page Number.
   */
  private int last_page;

  /**
   * Next Page Number.
   */
  private int next_page;

  /**
   * Previous Page Number.
   */
  private int prev_page;

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public int getPer_page() {
    return per_page;
  }

  public void setPer_page(int per_page) {
    this.per_page = per_page;
  }

  public int getCurrent_page() {
    return current_page;
  }

  public void setCurrent_page(int current_page) {
    this.current_page = current_page;
  }

  public int getLast_page() {
    return last_page;
  }

  public void setLast_page(int last_page) {
    this.last_page = last_page;
  }

  public int getNext_page() {
    return next_page;
  }

  public void setNext_page(int next_page) {
    this.next_page = next_page;
  }

  public int getPrev_page() {
    return prev_page;
  }

  public void setPrev_page(int prev_page) {
    this.prev_page = prev_page;
  }
}
