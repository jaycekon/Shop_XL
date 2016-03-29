<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/28 0028
  Time: 下午 1:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<img src="<%=request.getContextPath()%>/app/img/good/14591478337861380884427174+741700057.jpg"/>
<form action="<%=request.getContextPath()%>/Good/upload" method="POST" enctype="multipart/form-data">

    商品详细图片:<input type="file" name="files"/><br>
    商品详细图片:<input type="file" name="files"/><br>
    商品详细图片:<input type="file" name="files"/><br>
    <input type="submit" value="添加"/>
    </form>
</body>
</html>
