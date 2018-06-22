package com.lush.javaAggregator.eums;

/**
 * Exception handler
 * Manage exception messages in common.
 */
public enum ExceptionType {

  /**
   * Not found data exception.
   */
  NOT_FOUND_DATA(404, "no data found"),

  /**
   * Not found file exception.
   */
  NOT_FOUND_FILE(404, "no file found"),

  /**
   * Duplicated data exception.
   */
  DUPLICATED_DATA(412, "already duplicated data"),

  /**
   * Don't allow file type exception.
   */
  FILE_TYPE_EXCEPTION(415, "don't allow File type"),

  /**
   * File upload failed exception.
   */
  FILE_UPLOAD_FAILED_EXCEPTION(502, "file upload failed."),

  /**
   * Invalid ID value exception.
   */
  INVALID_ID_VALUE(412, "ID must be greater than 0.");

  /**
   * Exception Code
   */
  private final int code;

  /**
   * Exception Message
   */
  private final String massage;

  /**
   * Default creator
   *
   * @param code
   * @param massage
   */
  ExceptionType(int code, String massage) {
    this.code = code;
    this.massage = massage;
  }

  /**
   * Get Exception code
   */
  public int getCode() {
    return code;
  }

  /**
   * Get Exception message
   */
  public String getMassage() {
    return massage;
  }

}
