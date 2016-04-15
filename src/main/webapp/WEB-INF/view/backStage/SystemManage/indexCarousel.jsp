<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/15 0015
  Time: 下午 2:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-首页轮播</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/systemManager.css">

</head>
<body>

<div class="wrapper">

    <!-- [[头部 -->
    <header class="header container-fluid">
        <div class="row">
            <div class="col-xs-3">
                <img src="../image/logo.jpg" alt="一内购" class="logo img-responsive">
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
            <div class="content col-md-10 index-carousel">

                <!-- [[图片上传要求 -->
                <div class="alert alert-warning alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong> 注意：</strong>上传的图片尺寸要求为2:1
                </div><!-- 图片上传要求]] -->

                <div class="row">
                    <form class="col-md-6 img-form">
                        <img src="<%=request.getContextPath()%>/app/backStage/image/carousel2.jpg" alt=""/>
                        <div class="oprate clearfix">
                            <input type="file" accept="image/*" class="col-md-4 selFile" />
                            <span class="col-md-4">图一</span>
                            <span class="col-md-4 text-right">
                                <button type="submit" class="btn btn-primary">更新</button>
                            </span>
                        </div>
                    </form>
                    <form class="col-md-6 img-form">
                        <img src="<%=request.getContextPath()%>/app/backStage/image/carousel2.jpg" alt=""/>
                        <div class="oprate clearfix">
                            <input type="file" accept="image/*" class="col-md-4 selFile"/>
                            <span class="col-md-4">图二</span>
                            <span class="col-md-4 text-right">
                                <button type="submit" class="btn btn-primary">更新</button>
                            </span>
                        </div>
                    </form>
                </div>
                <div class="row">
                    <form class="col-md-6 img-form">
                        <img src="<%=request.getContextPath()%>/app/backStage/image/carousel2.jpg" alt=""/>
                        <div class="oprate clearfix">
                            <input type="file" accept="image/*" class="col-md-4 selFile"/>
                            <span class="col-md-4">图三</span>
                            <span class="col-md-4 text-right">
                                <button type="submit" class="btn btn-primary">更新</button>
                            </span>
                        </div>
                    </form>
                    <form class="col-md-6 img-form">
                        <img src="<%=request.getContextPath()%>/app/backStage/image/carousel2.jpg" alt=""/>
                        <div class="oprate clearfix">
                            <input type="file" accept="image/*" class="col-md-4 selFile"/>
                            <span class="col-md-4">图四</span>
                            <span class="col-md-4 text-right">
                                <button type="submit" class="btn btn-primary">更新</button>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
            <!-- 右边操作页面]] -->
        </div>

    </div>
    <!-- 页面主体]] -->
</div>
<!-- wrapper]] -->

<script src="<%=request.getContextPath()%>/app/backStage/lib/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/lib/jquery/ajaxfileupload.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/systemManager.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>

</body>
</html>
