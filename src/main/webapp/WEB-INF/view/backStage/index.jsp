<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/29 0029
  Time: 上午 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店倾销网平台</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/components.css">

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
            <%@ include file="left.jsp"%>
            <!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="col-md-9 col-lg-10 content">

                <div class="entrance_container container-fluid">
                    <div class="row">

                        <div class="col-xs-6 col-md-4">
                            <a class="selet_block selet_block1 row" href="<%=request.getContextPath()%>/listAreas">
                                <div class="col1 col-xs-4"><span class="glyphicon glyphicon-globe "></span></div>
                                <div class="col2 col-xs-8">大区</div>
                            </a>
                        </div>

                        <div class="col-xs-6 col-md-4">
                            <a class="selet_block selet_block2" href="<%=request.getContextPath()%>/listRoles">
                                <div class="col1 col-xs-4"><span class="glyphicon glyphicon-log-in"></span></div>
                                <div class="col2 col-xs-8">角色</div>
                            </a>
                        </div>

                        <div class="col-xs-6 col-md-4">
                            <a class="selet_block selet_block3" href="<%=request.getContextPath()%>/listUser">
                                <div class="col1 col-xs-4"><span class="glyphicon glyphicon-user "></span></div>
                                <div class="col2 col-xs-8">店家</div>
                            </a>
                        </div>

                        <div class="col-xs-6 col-md-4">
                            <a class="selet_block selet_block4" href="<%=request.getContextPath()%>/listOrder">
                                <div class="col1 col-xs-4"><span class="glyphicon glyphicon-list-alt"></span></div>
                                <div class="col2 col-xs-8">订单列表</div>
                            </a>
                        </div>

                        <div class="col-xs-6 col-md-4">
                            <a class="selet_block selet_block5">
                                <div class="col1 col-xs-4"><span class="glyphicon glyphicon-credit-card"></span></div>
                                <div class="col2 col-xs-8">平台收入</div>
                            </a>
                        </div>

                    </div>

                </div>

            </div><!-- 右边操作页面]] -->
        </div>

    </div><!-- 页面主体]] -->

</div><!-- wrapper]] -->
<script src="<%=request.getContextPath()%>/app/backStage/lib/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>
</body>
</html>
