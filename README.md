このプロジェクトは、Servlet, JSP, MySQLを使ったミニブログアプリです。

ユーザーが登録・ログインし、テキストや画像付きの投稿ができる機能を実装しました。自分の投稿を削除することも可能です。
### フロント
<img src="https://github.com/saibin2023/mutter-app/blob/main/img/final.png" alt="Mutter App" width="900">

### 動作の仕組み
<img src="https://github.com/saibin2023/mutter-app/blob/main/img/web%20app%E3%81%AE%E4%BB%95%E7%B5%84%E3%81%BF.png" alt="Mutter App" width="900">


### プロジェクトの詳細
```
1. システム概要
   
「つぶやき」は、Servlet、JSP、MySQL を使って開発した 簡単なミニブログアプリ です。
ユーザーはアカウントを作成し、ログインして 投稿（画像＋テキスト） できます。投稿の削除も可能です。

2. 主な機能（主な機能）

ユーザー管理：

登録（新規ユーザー作成）

ログイン・ログアウト（セッション管理）

投稿機能：

投稿作成（テキスト＋画像のアップロード）

投稿削除（ユーザー自身の投稿のみ削除可能）

3. 技術スタック

バックエンド：Servlet（Tomcat）

フロントエンド：JSP（HTML, CSS, JavaScript）

データベース：MySQL（ユーザー情報、投稿データを保存）

画像保存：サーバーのファイルシステムにアップロード

4. 工夫した点

セッション管理でログイン状態を維持し、適切なアクセス制御を実装

投稿の所有者のみが削除可能なロジックを実装し、不正削除を防止

データのセキュリティ：

パスワードは ハッシュ化を使って保存

ユーザー目線での改善：

Google アカウントでの登録・ログイン（OAuth 2.0 認証）を導入し、利便性を向上

```
### プロジェクトを実行する方法

現在実行中のサービスを確認
```
brew services list
```
データベースにアクセス
```
brew services start mysql@8.0
```

```
mysql -u your_username -p
```
プロジェクトのビルドとサーバーの起動

プロジェクトディレクトリで以下のコマンドを実行してください。

```
mvn clean install
```

```
# Tomcatを停止
brew services stop tomcat

# 旧バージョンのデプロイを削除
sudo rm /usr/local/opt/tomcat/libexec/webapps/mutter-app.war
sudo rm -rf /usr/local/opt/tomcat/libexec/webapps/mutter-app

# 新しいWARファイルをデプロイ
sudo cp target/mutter-app.war /usr/local/opt/tomcat/libexec/webapps/

# Tomcatのキャッシュをクリア（推奨）
rm -rf /usr/local/opt/tomcat/libexec/work/*

# Tomcatを起動
brew services start tomcat
```
### アプリケーションにアクセス

http://localhost:8080/mutter-app


