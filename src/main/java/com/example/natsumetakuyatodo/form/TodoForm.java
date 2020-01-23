package com.example.natsumetakuyatodo.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Todoデータの登録、編集時にデータの受け渡しを行うFormクラス
 *
 * @author Natsume Takuya
 */
@Data
public class TodoForm {

  @Size(max = 30, message = "ToDo名は1文字以上30文字以内で入力してください")
  @NotBlank(message = "ToDo名の入力は必須です")
  private String name;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @NotNull(message = "期日の入力は必須です")
  private LocalDate deadline;
}
