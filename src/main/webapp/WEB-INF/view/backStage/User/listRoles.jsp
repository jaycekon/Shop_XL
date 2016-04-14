<%@ page import="com.Shop.Model.Roles" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/14 0014
  Time: 下午 3:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-角色区表</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/userManager.css">

</head>
<body>

<div class="wrapper">
    <!-- [[头部 -->
    <header class="header container-fluid">
        <div class="row">
            <div class="col-xs-3">
                <img src="<%=request.getContextPath()%>/app/backStage/image/logo.jpg" alt="一内购" class="logo img-responsive">
                <button class="navbar-toggle collapsed" type="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="col-xs-6 header_center">
                <h1 class="websiteName">百城万店倾销网平台</h1>
            </div>
            <div class="col-xs-3">
                <button class="btnExit fr" type="button"><span class="glyphicon glyphicon-off"></span>退出</button>
            </div>
        </div>
    </header><!-- 头部]] -->

    <!-- [[页面主体 -->
    <div class="mainBody container-fluid">

        <div class="row">

            <!-- [[ 左边导航 -->
            <%@ include file="../left.jsp"%>
             <!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="content col-md-10 userManager-copartner">

                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <caption>角色列表</caption>
                        <thead>
                        <tr>
                            <td>头像</td>
                            <td class="textLeft">姓名</td>
                            <td>联系方式</td>
                            <td class="textLeft">常用邮箱</td>
                            <td class="textRight">可支配佣金（元）</td>
                            <td class="textRight">待收益佣金（元）</td>
                            <td class="textRight">冻结佣金（元）</td>
                        </tr>
                        </thead>

                        <tbody>
                        <%
                            List<Roles> roles = (List<Roles>)request.getAttribute("roles");
                            for(Roles r:roles){
                        %>
                        <tr>
                            <td class="imgTd">
                                <img src="<%=r.getImg()%>" alt="">
                            </td>
                            <td class="textLeft"><%=r.getName()%></td>
                            <td>2016-01-01 06:06:06</td>
                            <td class="textLeft">654472342@qq.com</td>
                            <td class="textRight">10.22</td>
                            <td class="textRight">9.99</td>
                            <td class="textRight">9.99</td>
                        </tr>

                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>

                <!-- [[分页-->
                <div class="paging">
                    <span class="prev btn btn-primary">上一页</span>
                    <span class="next btn btn-primary">下一页</span>
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