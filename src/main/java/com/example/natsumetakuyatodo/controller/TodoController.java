package com.example.natsumetakuyatodo.controller;

import com.example.natsumetakuyatodo.entity.Todo;
import com.example.natsumetakuyatodo.form.TodoForm;
import com.example.natsumetakuyatodo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// Todo エラーハンドリングのバグ、　編集のバグ
/**
 * TodoアプリのControllerクラス
 *
 * @author Natsume Takuya
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  /**
   * URL"/"を表示
   *
   * @param todoForm TodoFormクラス
   * @param modelAndView ModelAndViewクラス
   * @return modelAndView　表示する画面
   */
  @GetMapping("/")
  public ModelAndView index(
      @ModelAttribute("todoForm") TodoForm todoForm, ModelAndView modelAndView) {

    modelAndView.setViewName("index");
    List<Todo> todoList = todoService.getDataOrderByCreatedDate();
    modelAndView.addObject("datalist", todoList).addObject("message", "Todoアプリ");
    return modelAndView;
  }

  /**
   * 新規のTodoを登録
   *
   * @param todoForm TodoFormクラス
   * @param bindingResult BindingResultクラス
   * @param modelAndView ModelAndViewクラス
   * @return modelAndView 処理後のリダイレクト先
   */
  @PostMapping("/register")
  public ModelAndView register(
      @Validated @ModelAttribute("todoForm") TodoForm todoForm,
      BindingResult bindingResult,
      ModelAndView modelAndView) {
    if (bindingResult.hasErrors()) {
      modelAndView.setViewName("index");
      List<Todo> todoList = todoService.getDataOrderByCreatedDate();
      modelAndView.addObject("datalist", todoList).addObject("message", "Todoアプリ");
      return modelAndView;
    }
    if (todoService.findByName(todoForm.getName()) != null) {
      modelAndView.setViewName("index");
      bindingResult.rejectValue("name", null, "同じ名前のToDoがすでに登録されています");
      List<Todo> todoList = todoService.getDataOrderByCreatedDate();
      modelAndView.addObject("datalist", todoList).addObject("message", "Todoアプリ");
      return modelAndView;
    }

    todoService.saveTodo(todoForm.getName(), todoForm.getDeadline());
    return new ModelAndView("redirect:/");
  }

  /**
   * Todoの編集画面
   *
   * @param todoForm TodoFormクラス
   * @param modelAndView ModelAndViewクラス
   * @return modelAndView 表示する画面
   */
  @GetMapping("/{id}/edit")
  public ModelAndView edit(
      @ModelAttribute("todoForm") TodoForm todoForm,
      ModelAndView modelAndView,
      @PathVariable long id) {
    modelAndView.setViewName("edit");
    modelAndView
        .addObject("message", "ToDoの内容を変更します")
        .addObject("todoForm", todoService.findById(id));
    return modelAndView;
  }

  /**
   * 編集内容を保存
   *
   * @param todoForm TodoFormクラス
   * @param bindingResult BindingResultクラス
   * @param modelAndView ModelAndViewクラス
   * @return modelAndView validation結果に基づき画面を遷移
   */
  @PostMapping("/{id}/update")
  public ModelAndView update(
      @Validated @ModelAttribute("todoForm") TodoForm todoForm,
      BindingResult bindingResult,
      @PathVariable("id") long id,
      ModelAndView modelAndView) {

    if (bindingResult.hasErrors()) {
      modelAndView.setViewName("edit");
      modelAndView.addObject("message", "ToDoの内容を変更します");
      return modelAndView;
    }
    // 編集画面では期限のみが変更され名前はそのままのケースを想定し、ここで入力された名前(enteredName)が元々の名前(originalName)と一致する場合にはvalidation通過
    if (!todoService.isNameValidaForEdit(todoForm, id)) {
      bindingResult.rejectValue("name", null, "同じ名前のToDoがすでに登録されています");
      modelAndView.setViewName("edit");
      modelAndView.addObject("message", "ToDoの内容を変更します");
      return modelAndView;
    }

    todoService.updateData(todoForm.getName(), todoForm.getDeadline(), id);
    return new ModelAndView("redirect:/");
  }

  /**
   * Todoの検索画面
   *
   * @param modelAndView ModelAndViewクラス
   * @return modelAndView 表示する画面
   */
  @GetMapping("/search")
  public ModelAndView search(ModelAndView modelAndView) {
    modelAndView.setViewName("search");
    return modelAndView;
  }

  /**
   * Todoの検索結果を取得
   *
   * @param searchWord 検索フォームに入力されたワード
   * @param modelAndView ModelAndViewクラス
   * @return modelAndVie 表示する画面
   */
  @GetMapping("/search/result")
  public ModelAndView result(
      @RequestParam("searchWord") String searchWord, ModelAndView modelAndView) {
    modelAndView.setViewName("search");
    List<Todo> searchTodoList = todoService.findByNameAndSituation(searchWord);
    if (searchWord.isEmpty() || searchTodoList.size() == 0) {
      modelAndView.addObject("resultComment", "対象のTodoは見つかりません");
    } else {
      modelAndView.addObject("resultComment", "ToDoが" + searchTodoList.size() + "件見つかりました");
    }

    modelAndView.addObject("todoList", searchTodoList).addObject("searchWord", searchWord);
    return modelAndView;
  }

  /**
   * 完了・未完了を切り替える
   *
   * @param id Todoのid
   * @return modelAndVie 完了・未完了変更後の遷移先
   */
  @PatchMapping("/{id}/toggle_situation")
  public ModelAndView toggleSituation(@PathVariable("id") long id) {
    todoService.changeSituation(id);
    return new ModelAndView("redirect:/");
  }
}
