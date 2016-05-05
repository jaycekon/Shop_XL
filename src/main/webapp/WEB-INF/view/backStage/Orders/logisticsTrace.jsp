<%@ page import="com.Shop.Util.ExpressBean" %>
<%@ page import="com.Shop.Util.ExpressContext" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/25 0025
  Time: 下午 4:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>一内购-物流追踪</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/order.css">

</head>
<body>

<div class="wrapper">
    <!-- [[头部 -->
    <%@ include file="../header.jsp"%>
    <!-- 头部]] -->

    <!-- [[页面主体 -->
    <div class="mainBody container-fluid">

        <div class="row">

            <!-- [[ 左边导航 -->
            <%@ include file="../left.jsp"%>
            <!-- 左边导航]] -->

            <%
                ExpressBean expressBean =(ExpressBean)request.getAttribute("expressBean");
                List<ExpressContext> datas = expressBean.getData();
            %>
            <!-- [[右边操作页面 -->
            <div class="content col-md-10 logisticsTrace">
                <div class="logistics-detail">
                    <%
                        for(ExpressContext expressContext:datas){
                    %>
                    <div class="step clearfix">
                        <div class="pull-left time"><%=expressContext.getTime()%></div>
                        <div class="pull-left bgc start"></div><!--start 物流开始-->
                        <div class="pull-left place"><%=expressContext.getContext()%></div>
                    </div>

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
<script src="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>

</body>
</html>
