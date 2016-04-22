<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/13 0013
  Time: 下午 5:19
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
    <style>
        .ui-container {
            /* 若推广码有背景图 */
            background-color: #f69;
            color: #fff;
            /*   background: url(./../image/2.jpg);
               background-size: contain;*/
            position: relative;
        }
        .center {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            -webkit-transform: translateY(-50%);
            width: 100%;
            text-align: center;
        }
        .centerImg {
            width: 50%;
        }
    </style>
</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>我的店家推广码</h1>
</header>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">
        <%
            if(session.getAttribute("roles")!=null){
        %>
        <li class="footerItem active"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/roleCenter'"></i><div>角色中心</div></li>
        <%
        }
        else if(session.getAttribute("areas")!=null){
        %>
        <li class="footerItem active"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/areaCenter'"></i><div>大区中心</div></li>
        <%
            }
        %></ul>
</footer>

<section class="ui-container">
<%
    String img = (String) request.getAttribute("code");
%>
    <div class="center">
        <img src="<%=img%>" alt="二维码" class="centerImg" />
    </div>

</section>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script>
    $(document).ready(function(){
        var mainHeight = $(window).height() - $('.ui-header').height() - $('ui-footer').height();
        $('.ui-container').height(mainHeight);
    })
</script>

</body>
</html>
