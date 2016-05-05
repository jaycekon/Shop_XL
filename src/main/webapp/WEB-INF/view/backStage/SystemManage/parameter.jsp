<%@ page import="com.Shop.Model.Profit" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/15 0015
  Time: 下午 2:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-参数设置</title>
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

            <%@ include file="../left.jsp"%>
            <!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="content col-md-10 parameter">


                <%
                    Profit profit = (Profit)request.getAttribute("profit");
                %>
                <form action="<%=request.getContextPath()%>/updateProfit" method = "post">
                    <legend>倾销币参数</legend>
                    <div class="form-group">
                        <label >入会金额：</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name ="recordPrices" value ="<%=profit.getRecordPrices()%>" placeholder="Amount">
                            <div class="input-group-addon">元</div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>送币量：</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name ="dumpingCount" value = "<%=profit.getDumpingCount()%>" placeholder="Amount">
                        </div>
                    </div>

                    <div class="form-group">
                        <label >一个倾销币的金额：</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name ="countPrices" value ="<%=profit.getCountPrices()%>" placeholder="Amount">
                            <div class="input-group-addon">元</div>
                        </div>
                    </div>
                    <legend>佣金参数</legend>
                    <div class="form-group">
                        <label >大区抽佣：</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name ="area_count" value ="<%=profit.getArea_count()%>" placeholder="Amount">
                            <div class="input-group-addon">%</div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label >角色抽佣：</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name ="role_count" value ="<%=profit.getRole_count()%>" placeholder="Amount">
                            <div class="input-group-addon">%</div>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary">更新</button>
                </form>

            </div>
            <!-- 右边操作页面]] -->
        </div>

    </div>
    <!-- 页面主体]] -->
</div>
<!-- wrapper]] -->

<script src="<%=request.getContextPath()%>/app/backStage/lib/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>

</body>
</html>