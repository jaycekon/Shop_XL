<%@ page import="com.Shop.Model.Image" %>
<%@ page import="java.util.List" %><%--
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
    <%@ include file="../header.jsp"%><!-- 头部]] -->

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
                    <%
                        List<Image> images = (List<Image>)request.getAttribute("images");
                        for(Image image:images){
                    %>
                    <form class="col-md-6 img-form" action="<%=request.getContextPath()%>/setImage/<%=image.getId()%>" enctype="multipart/form-data" method="post">
                        <img src="<%=image.getAddress()%>" alt=""/>
                        <div class="oprate clearfix">
                            <input type="file" accept="image/*" name ="files" class="col-md-4 selFile" />
                            <span class="col-md-4">图一</span>
                            <span class="col-md-4 text-right">
                                <button type="submit" class="btn btn-primary">更新</button>
                            </span>
                        </div>
                    </form>

                    <%
                        }
                    %>
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
