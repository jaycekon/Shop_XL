<%@ page import="com.Shop.Model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Util.Page" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/14 0014
  Time: 下午 3:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>一内购后台管理系统</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/userManager.css">

</head>
<body>

<div class="wrapper">

    <!-- [[头部 -->
    <%@ include file="../header.jsp"%><!-- 头部]] -->

    <!-- [[页面主体 -->
    <div class="mainBody container-fluid">

        <div class="row">

            <!-- [[ 左边导航 -->
            <%@ include file="../left.jsp"%>
            <<!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="content col-md-10 userManager-port">

                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <caption>店家列表</caption>
                        <thead>
                        <tr>
                            <td>头像</td>
                            <td>姓名</td>
                            <td>关注时间</td>
                            <td>会员认证</td>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            Page<User> pages = (Page<User>)request.getAttribute("page");
                            List<User> users = pages.getList();
                            for(User user :users){
                        %>
                        <tr>
                            <td class="imgTd">
                                <img src="<%=user.getImg()%>" alt="">
                            </td>
                            <td><%=user.getUsername()%></td>
                            <td>2016-01-01 06:06:06</td>
                            <td><%
                                switch(user.getSign()){
                                    case 1:out.println("已认证");break;
                                    case 0:out.println("未认证");break;
                                }
                            %></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>

                <!-- [[分页-->
                <div class="paging">
                    <%
                        if(pages.getBeginIndex()!=0){
                    %>
                    <span class="prev btn btn-primary" onclick="window.location.href='<%=request.getContextPath()%>/listUser/<%=(pages.getBeginIndex()-10)%>'">上一页</span>
                    <%
                        }

                        if((pages.getBeginIndex()+10) < pages.getTotalCount()){
                    %>

                    <span class="next btn btn-primary"  onclick="window.location.href='<%=request.getContextPath()%>/listUser/<%=(pages.getBeginIndex()+10)%>'">下一页</span>
                    <%
                        }
                    %>
                </div>
                <!-- 分页]]-->

            </div>
            <!-- 右边操作页面]] -->
        </div>

    </div>
    <!-- 页面主体]] -->
</div>
<!-- wrapper]] -->

<script src="<%=request.getContextPath()%>/app/backStage/lib/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>

</body>
</html>
