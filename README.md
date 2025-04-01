# 项目图

<img src="https://github.com/saibin2023/mutter-app/blob/main/img/mutter-app.png" alt="Mutter App" width="900">



# mutter-app
```
我来帮您创建一个基于Java Servlet和JSP的Web应用项目。我们将使用ServletContext来存储数据，实现登录、发帖和浏览功能。

首先，让我们创建项目的基本结构。

很好，让我们开始创建项目结构。首先，我们需要创建一个基本的Maven项目结构，并设置必要的依赖。

我将创建以下文件：
pom.xml - Maven项目配置文件

基本的项目结构

必要的Java类和JSP页面
首先创建pom.xml：
现在让我们创建基本的项目结构和必要的文件。

首先创建用户模型类：
接下来创建帖子模型类：
现在创建登录Servlet：
创建帖子相关的Servlet：
现在创建登录页面：
创建帖子列表页面：
最后，让我们创建一个web.xml配置文件

让我们创建一个简单的错误页面：

创建了一个基本的Java Web应用程序，包含以下功能：

登录功能：

使用ServletContext存储用户信息
默认用户：admin/password
登录验证和错误提示

发帖和浏览功能：

使用ServletContext存储帖子信息
支持发布新帖子（标题和内容）
显示所有帖子列表
显示发帖时间和作者信息

错误处理：
404和500错误页面
表单验证和错误提示
会话超时处理

项目结构如下：
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── mutter/
│   │           ├── model/
│   │           │   ├── User.java
│   │           │   └── Post.java
│   │           └── servlet/
│   │               ├── LoginServlet.java
│   │               └── PostServlet.java
│   └── webapp/
│       └── WEB-INF/
│           ├── views/
│           │   ├── login.jsp
│           │   ├── posts.jsp
│           │   └── error.jsp
│           └── web.xml
└── pom.xml

```
# 要运行这个项目：

查看正在运行的所有服务
```
 brew services list
```
## 安装项目管理和服务器

安装Java 11或更高版本

安装Maven

安装Tomcat服务器

安装和运行数据库mySQL（在创建项目时需要配置好普通用户账户和权限，运行项目时需要启动数据库）

brew install mysql

brew services start mysql

访问数据库
```
mysql -u your_username -p
```

## 运行项目管理和服务器
```
    # In your project directory
    mvn clean install
    
    # Stop Tomcat
    brew services stop tomcat
    
    # Remove old deployment
    sudo rm /usr/local/opt/tomcat/libexec/webapps/mutter-app.war
    sudo rm -rf /usr/local/opt/tomcat/libexec/webapps/mutter-app
    
    # Deploy new WAR
    sudo cp target/mutter-app.war /usr/local/opt/tomcat/libexec/webapps/
    
    # Clean Tomcat cache (optional but recommended)
    rm -rf /usr/local/opt/tomcat/libexec/work/*
    
    # Start Tomcat
    brew services start tomcat
```
访问应用程序：

在浏览器中访问 http://localhost:8080/mutter-app，确保应用程序正常运行。

检查日志：查看 Tomcat 和应用程序日志，确保没有错误信息。

默认用户凭据：

用户名：admin

密码：password

确认上下文路径：
访问路径应该是 http://localhost:8080/mutter-app。

