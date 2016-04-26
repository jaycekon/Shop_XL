<%@ page import="com.Shop.Model.User" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/15 0015
  Time: 下午 3:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="format-detection" content="telephone=no, email=no">
    <title>百城万店倾销网</title>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/lib/css/frozen.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/index.css">

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <h1>店家列表</h1>
</header>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">
        <li class="footerItem"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/roleCenter'"></i><div>角色中心</div></li>
    </ul>
</footer>

<section class="ui-container">

    <!-- [[店家列表 -->
    <table class="table ui-table ui-border ui-border-tb">
        <thead>
        <tr>
            <th>头像</th>
            <th class="textLeftTd">昵称</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<User> users = (List<User>)request.getAttribute("users");
            for(User user:users){
        %>
        <tr>
            <td class="imgTd">
                <img src="<%=user.getImg()%>" alt="">
            </td>
            <td class="textLeftTd"><%=user.getUsername()%></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table><!-- 店家列表]] -->

    <div class="paging">
        <button class="ui-btn">上一頁</button>
        <button class="ui-btn">下一頁</button>
    </div>

</section>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script class="demo-script">
    $(".ui-dialog").dialog("show");
</script>

</body>
</html>
