package com.example.natsumetakuyatodo.exception;

/** Controllerクラスのeditメソッドで呼ばれる独自例外。 */
public class TodoNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -9179803581282922902L;

  public TodoNotFoundException(String message) {
    super(message);
  }
}
