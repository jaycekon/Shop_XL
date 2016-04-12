<%@ page import="com.Shop.Util.OrderPoJo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="com.Shop.Model.OrderProduct" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/6 0006
  Time: 上午 10:31
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/index.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/product.css">

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>我的订单</h1>
</header>

<%@include file="../footer.jsp"%>

<section class="ui-container">

    <!-- [[订单类别 -->
    <ul class="tab">
        <li>
            <a href="" class="ui-btn productBtn">待付款</a>
        </li>
        <li>
            <a href="" class="ui-btn">待发货</a>
        </li>
        <li>
            <a href="" class="ui-btn">待收货</a>
        </li>
        <li>
            <a href="" class="ui-btn">已完成</a>
        </li>
        <li>
            <a href="" class="ui-btn">待评价</a>
        </li>
        <li>
            <a href="" class="ui-btn">退款中</a>
        </li>
        <li>
            <a href="" class="ui-btn">已关闭</a>
        </li>
    </ul>
    <!-- 订单类别]] -->

    <!-- [[没有订单的情况 -->
    <!--  <section class="ui-notice">
          <i></i>
          <p>没有此类型的订单</p>
      </section>--><!-- [[没有订单的情况 -->
<%
    List<OrderPoJo> orderPoJos =(List<OrderPoJo>)request.getAttribute("orderPoJos");
    for(OrderPoJo orderPoJo:orderPoJos){
        Orders orders = orderPoJo.getOrders();
        List<OrderProduct> orderProducts = orderPoJo.getOrderProduct();
%>
    <ul class="order ui-list ui-list-text ui-border-tb uiListNoLink">
        <a href="<%=request.getContextPath()%>/userOrderDetail/<%=orders.getId()%>">
        <!-- [[订单头部 -->
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4 class="ui-nowrap">订单编号：<%=orders.getId()%></h4>
            </div>
            <div class="ui-list-action themeColor">使用中</div>
        </li><!-- 订单头部]] -->
        <%
            for(OrderProduct orderProduct:orderProducts){

        %>
        <!-- [[订单商品 -->
        <li class="ui-border-t productLink">
            <div class="ui-list-thumb">
                <img src="<%=request.getContextPath()%>/app/frontStage/image/1.jpg" alt="">
            </div>
            <div class="ui-list-info">
                <h4 class="ui-nowrap"><%=orderProduct.getDescribes()%></h4>
            </div>
            <div class="textRight">
                <p class="">&#165; <%=orderProduct.getPrices()%></p>
                <p class="unMarjorColor">X<%=orderProduct.getCount()%></p>
            </div>
        </li><!-- 订单商品]] -->

        <%
            }

        %>

    </ul>
    <div class="countBlock">
        <p>商品合计： <span class="themeColor">&#165; <%=orders.getPrices()%></span>（共<%=orders.getNumber()%>件）</p>
        <p>运费合计： <span class="themeColor">&#165; 10.00</span></p>
    </div>
    </a>
<%
    }

%>
</section>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>
<%--<script>--%>
    <%--$('.productLink').tap(function(){--%>
        <%--var $target = $(this);--%>
        <%--$target.addClass('clickActive');--%>
        <%--setTimeout(function(){--%>
            <%--$target.removeClass('clickActive');--%>
        <%--},150);--%>
        <%--window.location.href = "./orderDetail.html"--%>
    <%--})--%>
<%--</script>--%>

</body>
</html>