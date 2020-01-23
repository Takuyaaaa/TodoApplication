package com.example.natsumetakuyatodo.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Todoを保存するテーブルを定義
 *
 * @author Natsume Takuya
 */
@Entity
@Table(name = "todo")
@Data
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private LocalDate deadline;

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDate createdDate;

  @Column(nullable = false)
  private boolean situation;
}
