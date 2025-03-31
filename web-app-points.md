运行一个基于 Java Servlet 和 JSP 的 Web 应用项目，并使用 ServletContext 存储数据，主要涉及以下几个方面的工作：

1. 项目环境搭建
(1) 安装必需的工具
JDK（Java Development Kit）（推荐 JDK 8 或以上）

Apache Tomcat（推荐 9.0 以上）

IDE（推荐 Eclipse、IntelliJ IDEA、NetBeans）

Maven 或 Gradle（用于依赖管理，可选）

(2) 创建 Web 项目
如果使用 Eclipse：

新建 Dynamic Web Project

配置 Tomcat 服务器

确保 web.xml 存在（WEB-INF/web.xml）

2. 设计系统架构
(1) 数据存储
使用 ServletContext 作为存储：

用户列表

论坛帖子列表

数据结构示例：

```java
List<User> users = new ArrayList<>();
List<Post> posts = new ArrayList<>();
```
3. 编写核心 Servlet
(1) ServletContextListener（初始化数据）
在 contextInitialized 方法中初始化数据：

```java
@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("users", new ArrayList<User>());
        context.setAttribute("posts", new ArrayList<Post>());
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 可用于释放资源
    }
}
```
(2) LoginServlet（处理用户登录）
```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ServletContext context = getServletContext();
        List<User> users = (List<User>) context.getAttribute("users");

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect("home.jsp");
                return;
            }
        }
        response.sendRedirect("login.jsp?error=1");
    }
}
```
(3) PostServlet（处理发帖）
```java
@WebServlet("/post")
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String content = request.getParameter("content");
        Post newPost = new Post(user.getUsername(), content, new Date());

        ServletContext context = getServletContext();
        List<Post> posts = (List<Post>) context.getAttribute("posts");
        posts.add(newPost);

        response.sendRedirect("home.jsp");
    }
}
```
4. 设计 JSP 页面
(1) login.jsp（用户登录）

```jsp
<form action="login" method="post">
    用户名: <input type="text" name="username" required><br>
    密码: <input type="password" name="password" required><br>
    <input type="submit" value="登录">
</form>
<% if(request.getParameter("error") != null) { %>
    <p style="color:red;">用户名或密码错误</p>
<% } %>
```
(2) home.jsp（显示帖子）

```jsp
<%@ page import="java.util.List, model.Post" %>
<%@ page session="true" %>
<%
    List<Post> posts = (List<Post>) application.getAttribute("posts");
%>

<h2>帖子列表</h2>
<ul>
    <% for (Post post : posts) { %>
        <li><b><%= post.getUsername() %></b>: <%= post.getContent() %></li>
    <% } %>
</ul>

<form action="post" method="post">
    <textarea name="content" required></textarea><br>
    <input type="submit" value="发表">
</form>
```
5. 部署与运行
(1) 编译和打包
如果使用 Maven：
```
mvn clean package
```
(2) 部署到 Tomcat
将 target/your-project.war 放入 webapps 目录

启动 Tomcat

访问 http://localhost:8080/your-project/

总结
已实现的功能
用户登录 (LoginServlet)

帖子发布 (PostServlet)

帖子浏览 (home.jsp)

使用 ServletContext 共享数据

这种方式适用于小型项目，但 ServletContext 适用于全局数据，不适用于存储会话用户信息（应使用 HttpSession）。
如需持久化数据，建议使用数据库（MySQL、PostgreSQL）或 ORM 框架（如 Hibernate）。
