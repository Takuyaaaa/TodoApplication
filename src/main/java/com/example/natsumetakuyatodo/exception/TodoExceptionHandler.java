package com.example.natsumetakuyatodo.exception;

import com.example.natsumetakuyatodo.controller.TodoController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * エラーハンドリングを行うためのクラス
 *
 * @author Natsume Takuya
 */
@ControllerAdvice(assignableTypes = TodoController.class)
@Slf4j
public class TodoExceptionHandler {

  /**
   * このクラスで指定されていないエラーが発生した場合に対応
   *
   * @param e Exceptionクラス
   * @return modealAndView 表示する画面
   */
  @ExceptionHandler(Exception.class)
  public ModelAndView handleGeneralException(Exception e) {
    log.error(e.getMessage(), e);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("error/generalError");
    modelAndView.addObject("errorMessage", "不明なエラーが発生しました");
    return modelAndView;
  }

  /**
   * HttpRequestMethodNotSupportedExceptionに対応
   *
   * @param e Exceptionクラス
   * @return String 表示する画面
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public String handle405(Exception e) {
    log.warn(e.getMessage(), e);
    return "error/405";
  }

  /**
   * NoSuchElementExceptionに対応
   *
   * @param e Exceptionクラス
   * @return String 表示する画面
   */
  @ExceptionHandler({TodoNotFoundException.class})
  public String handle500(Exception e) {
    log.warn(e.getMessage(), e);
    return "error/500";
  }

  /**
   * MissingServletRequestParameterExceptionに対応
   *
   * @param e Exceptionクラス
   * @return String 表示する画面
   */
  @ExceptionHandler({MissingServletRequestParameterException.class})
  public String handle400(Exception e) {
    log.warn(e.getMessage(), e);
    return "error/400";
  }
}
