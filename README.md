# Todoアプリケーション
### 概要
Todoタスクの追加、検索などが行えるアプリケーションです。
一覧・Todoの追加ページ, Todoの編集ページ, Todoの検索ページに加えて、errorハンドリング用のページ2つの5つの画面から構成されます。
### 使用した技術要素 (Name - ver)
Java - 11  
MySQL - 5.7  
Spring boot - 2.2  
thymleaf - 3.0   
lombok - 1.18   



### 全体の設計・構成についての説明
上述のように、五つの画面から構成されています。  
index.htmlがホーム画面となり、ここに登録されているTodoの一覧が表示されます。上部のフォームより名前とその期日を入力することでTodoの追加を行うこともできます。  
それぞれの項目の「編集」リンクをクリックすることでedit.htmlに遷移し、Todoの名前およびその期日を編集できます。  
また、それぞれのTodoのステータスを示すボタンをクリックすることで、該当項目の切り替えが可能です。
search.htmlではTodoの名称（部分一致）から検索を行うことができ、検索結果はindex.htmlと同様の形式で表示されます。また、ここで検索対象となるTodoはsituationが「未完了」のもののみとなります。  
画面上部のナビバーはそれぞれのページを通して固定となっていて、左上の「TODOリスト」はindex.htmlへのリンクに、右上の「検索」はsearch.htmlへのリンクとなっています。
下記がTodoControllerクラスにおけるURLと対応htmlの一覧になります。  

* / (GET): index.htmlに対応し、Todoの一覧を表示します
* /register (POST): index.htmlに対応し、入力されたTodoを新規登録します
* /{id}/edit (GET): edti.htmlに対応し、index.htmlで編集リンクをクリックされたTodoを、idを基に表示します
* /{id}/update (POST): edit.htmlに対応し、validationに問題がなければ編集された情報を保存し、index.htmlに遷移します
* /search (GET): search.htmlに対応し、検索のフォームを表示します
* /result (GET): search.htmlに対応し、検索のフォームに入力された内容を基に結果を一覧で表示します  

なお、データベースのテーブル構成は下記になります。 (Field - type - Null - Key)  

id - int(11) - NOT NULL - PRI  
name - varchar(20) - NO - NO   
deadline - date - NO - NO  
created_date - date - NO - NO  
situation - tinyint(1) - NO - NO  
|
### 開発環境のセットアップ手順
* SDKMANをhttps://sdkman.io/installからインストールし、  

``$ sdk list java`` 

のコマンドをターミナルで実行。  
AdoptOpenJDK の 11.xx.xx.hs-adpt(xxはマイナーバージョン を探し,  

``$ sdk install java 11.0.4.hs-adpt ``（バージョンはその時の最新ものものを選択）  

としてjava をインストール。  

MySQLを  

``  $ brew install mysql@5.7  ``    

としてインストール。  

``~/.bash_profileに[export PATH=/usr/local/bin:$PATH``  
``export PATH=“/usr/local/opt/mysql@5.7/bin:$PATH``  

の二行を追加して、  

`` $ source ~/.bash_profile ``  

よりその変更を読み込む。また、~/.my.cnfに下記のように追加し、サーバーの設定を行う。  
[client]  
default-character-set = utf8mb4  
[mysql]  
default-character-set = utf8mb4  
[mysqld]  
character-set-server = utf8mb4  
collation-server = utf8mb4_unicode_ci  
init-connect=‘SET NAMES utf8mb4;SET AUTOCOMMIT=0’  
skip-character-set-client-handshake  
lower_case_table_names=1  
sql_mode=NO_ENGINE_SUBSTITUTION  
max_allowed_packet=32MB  