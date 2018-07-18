package com.lush.javaAggregator.exceptions;

import com.lush.javaAggregator.enums.ExceptionType;
import com.lush.javaAggregator.enums.ResponseStatusType;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

  /**
   * Create default serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Exceptoin status
   */
  private final ResponseStatusType status;

  /**
   * Exceptoin code
   */
  private final Integer code;

  /**
   * Exceptoin message
   */
  private final String message;

  /**
   * The default creator. (using default code and message)
   */
  public BaseException() {
    this.status = ResponseStatusType.FAIL;
    this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    this.message = "Internal server exception";
  }

  /**
   * The default creator. (User create message)
   *
   * @param handlerMessage
   */
  public BaseException(String handlerMessage) {
    this.status = ResponseStatusType.FAIL;
    this.code = 500;
    this.message = handlerMessage;
  }

  /**
   * The default creator. (User Created)
   */
  private BaseException(Integer code, String handlerMessage) {
    this.status = ResponseStatusType.FAIL;
    this.code = code;
    this.message = handlerMessage;
  }

  /**
   * Set common exception.
   *
   * @return CoreException
   */
  public BaseException setCommonExceptoin(ExceptionType exceptionType) {
    BaseException baseException = new BaseException(exceptionType.getCode(),
        exceptionType.getMassage());
    return baseException;
  }

  /**
   * Get Exception status
   */
  public ResponseStatusType getStatus() {
    return status;
  }

  /**
   * Get Exception code
   */
  public Integer getCode() {
    return code;
  }

  /**
   * Get Exception message
   */
  @Override
  public String getMessage() {
    return message;
  }
}
