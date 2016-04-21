<%@ page import="com.Shop.Model.Roles" %>
<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Util.OrderPoJo" %>
<%@ page import="com.Shop.Model.OrderProduct" %>
<%@ page import="com.Shop.Model.Areas" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/19 0019
  Time: 下午 3:02
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/product.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/font/iconfont.css">

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>分佣订单</h1>
</header>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">

        <li class="footerItem"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/areaCenter'"></i><div>大区中心</div></li>


    </ul>
</footer>

<section class="ui-container">
<%
    Areas areas = (Areas)session.getAttribute("areas");
%>
    <!-- [[佣金统计 -->
    <ul class="ui-list ui-border-tb commissionTop">
        <li class="ui-border-t">
            <div class="ui-avatar">
                <img src="<%=areas.getImg()%>" alt="">
            </div>
            <div class="ui-list-info">
                <h4 class="ui-nowrap">总佣金：<span class="themeColor">&#165; <%=areas.getTotalCommission()%></span></h4>
                <h4 class="ui-nowrap">可提现佣金：<span class="themeColor">&#165; <%=areas.getExitCommission()%></span></h4>
            </div>
        </li>
    </ul> <!-- 佣金统计]] -->

    <%
        List<OrderPoJo> orderPoJos = (List<OrderPoJo>) request.getAttribute("orderPoJos");
        for(OrderPoJo orderPoJo:orderPoJos){
            Orders orders =orderPoJo.getOrders();
            List<OrderProduct> orderProducts  =orderPoJo.getOrderProduct();
    %>
    <ul class="ui-border-tb ui-list">
        <!-- [[订单头部 -->
        <li class="ui-border-b">
            <div class="ui-list-info">
                <h4 class="ui-nowrap">时间：<%=orders.getSetTime()%></h4>
            </div>
            <div class="ui-list-info" style="text-align: right;">
                <div class="ui-list-action"><span class="themeColor"><%
                    if(orders.getD()==1){
                        out.println("已结算");
                    }else{
                        out.println("待结算");
                    }
                %></span></div>
            </div>
        </li><!-- 订单头部]] -->
        <!-- [[单个商品 -->
        <%
            for(OrderProduct orderProduct:orderProducts){


        %>
        <div class="productBox ui-border-b">

            <div class="col imgCol">
                <img src="<%=orderProduct.getImage()%>" alt="">
            </div>

            <div class="col describeCol">
                <p>昵称：<%=orders.getUser().getUsername()%></p>
                <p>金额：&#165; <%=orderProduct.getPrices() * orderProduct.getCount()%></p>
                <p>佣金：&#165; <%=orderProduct.getRoleProfit()%></p>
                <p>订单状态：待收货</p>
            </div>

        </div><!-- 单个商品]] -->
        <%
            }
        %>
    </ul>
<%
    }
%>



</section>


<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>

</body>
</html>
