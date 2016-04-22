<%@ page import="com.Shop.Model.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/6 0006
  Time: 下午 2:19
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
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>店家中心</h1>
</header>

<%@include file="../footer.jsp"%>

<%
    User user =(User)session.getAttribute("loginUser");
%>
<section class="ui-container">

    <ul class="ui-list ui-list-text ui-border-b bottomBlock">
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4>头像</h4>
            </div>
            <div class="ui-list-action">
                <img src="<%=user.getImg()%>" alt="" class="picture">
            </div>
        </li>
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4>昵称</h4>
            </div>
            <div class="ui-list-action"><%=user.getUsername()%></div>
        </li>
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4>ID</h4>
            </div>
            <div class="ui-list-action"><%=user.getId()%></div>
        </li>
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4>我的推荐人</h4>
            </div>
<%
            if(user.getRoles()==null){
                out.println("");
            }else{
                out.println(user.getRoles().getName());
            }
%>        </li>
    </ul>

    <ul class="ui-list ui-list-text ui-list-link ui-border-tb bottomBlock  ui-list-active ui-list-cover gapTB">
        <li class="ui-border-t"  onclick="window.location.href='<%=request.getContextPath()%>/userOrders'">
            <h4>我的订单</h4>
        </li>
        <li class="ui-border-t" onclick="window.location.href='<%=request.getContextPath()%>/listAddress'">
            <h4>我的收货地址</h4>
        </li>
        <li class="ui-border-t"  onclick="window.location.href='<%=request.getContextPath()%>/myCount/0'">
            <h4 >我的倾销币</h4>
            <div class="ui-txt-info"><%=user.getCount()%></div>
        </li>
        <li class="ui-border-t" onclick="window.location.href='<%=request.getContextPath()%>/personSign'">
            <h4 class="ui-nowrap">会员认证</h4>
            <div class="ui-txt-info"><%
                if(user.getSign()==0){
                    out.println("未验证");
                }else{
                    out.println("已验证");
                }
            %></div>
        </li>
    </ul>

</section>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>

</body>
</html>
