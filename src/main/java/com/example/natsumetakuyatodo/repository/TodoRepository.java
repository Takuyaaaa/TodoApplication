package com.example.natsumetakuyatodo.repository;

import com.example.natsumetakuyatodo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Todoに対応するRepositoryクラス
 *
 * @author Natsume Takuya
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  /**
   * NameとSituationを指定してTodoを特定
   *
   * @param name Todo名
   * @param situation Todoのsituation
   * @return List<Todo> 該当Todo
   */
  List<Todo> findByNameContainingAndSituation(String name, boolean situation);

  /**
   * idを指定してTodoを検索
   *
   * @param id Todoのid
   * @return Optional<Todo> 該当Todo
   */
  @Override
  Optional<Todo> findById(Long id);

  /**
   * nameを指定してTodoを検索
   *
   * @param name Todoのname
   * @return nullの可能性あり
   */
  Optional<Todo> findByName(String name);

  /**
   * Todoの一覧を取得
   *
   * @return List<Todo>　現在登録されているTodoの一覧
   */
  List<Todo> findAllByOrderByCreatedDateDesc();
}
