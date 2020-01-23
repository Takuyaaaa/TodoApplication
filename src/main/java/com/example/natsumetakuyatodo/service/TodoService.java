package com.example.natsumetakuyatodo.service;

import com.example.natsumetakuyatodo.entity.Todo;
import com.example.natsumetakuyatodo.exception.TodoNotFoundException;
import com.example.natsumetakuyatodo.form.TodoForm;
import com.example.natsumetakuyatodo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * ビジネスロジックを記述するServiceクラス
 *
 * @author Natsume Takuya
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository repository;

  /**
   * Todoの保存
   *
   * @param name Todoのname
   * @param deadline Todoのdeadline
   */
  // todo formクラスのフィールド修正
  public void saveTodo(String name, LocalDate deadline) {
    Todo todo = new Todo();
    todo.setName(name);
    todo.setSituation(false);
    todo.setDeadline(deadline);
    repository.save(todo);
  }

  /**
   * Todoの更新
   *
   * @param name Todoのname
   * @param deadline Todoのdeadline
   * @param id Todoのid
   */
  public void updateData(String name, LocalDate deadline, long id) {
    Todo todo = findById(id);
    todo.setName(name);
    todo.setDeadline(deadline);
    repository.save(todo);
  }

  /**
   * Todo一覧を取得
   *
   * @return List<Todo> 現在DBに保存されているTodo
   */
  public List<Todo> getDataOrderByCreatedDate() {
    return repository.findAllByOrderByCreatedDateDesc();
  }

  /**
   * idからTodoを特定
   *
   * @param id Todoのid
   * @return Todo Todoクラス
   */
  public Todo findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new TodoNotFoundException("id=" + id + "のToDoが見つかりませんでした"));
  }

  /**
   * NameからTodoを特定
   *
   * @param name Todoのname
   * @return Todo Todoクラス
   */
  public Todo findByName(String name) {
    return repository.findByName(name).orElse(null);
  }

  /**
   * NameとsituationからTodoを特定
   *
   * @param searchWord　検索フォームに入力された文字
   * @return List<Todo> 検索にヒットしたTodoのリスト
   */
  public List<Todo> findByNameAndSituation(String searchWord) {
    return repository.findByNameContainingAndSituation(searchWord, false);
  }

  /**
   * situationの完了/未完了を切り替え
   *
   * @param id Todoのid
   */
  public void changeSituation(long id) {
    Todo todo = findById(id);
    todo.setSituation(!todo.isSituation());
    repository.save(todo);
  }

  /**
   * 編集時のname重複に対するvalidation
   *
   * @param todoForm TodoFormクラス
   * @param id Todoのid
   * @return boolean validationを通過すればtrue
   */
  public boolean isNameValidaForEdit(TodoForm todoForm, long id) {
    String enteredName = todoForm.getName();
    Todo todo = findByName(enteredName);
    return (todo == null) || (todo.getId() == id);
  }
}
