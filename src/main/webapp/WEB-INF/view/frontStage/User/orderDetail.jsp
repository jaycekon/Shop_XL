<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="com.Shop.Model.OrderProduct" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Util.OrderPoJo" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/6 0006
  Time: 上午 11:03
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/app/frontStage/font/iconfont.css">

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>订单详情</h1>
</header>

<%@include file="../footer.jsp"%>
<%
        OrderPoJo orderPojo = (OrderPoJo) request.getAttribute("orderPoJo");
        Orders orders =orderPojo.getOrders();
        List<OrderProduct> orderProducts = orderPojo.getOrderProduct();
%>
<section class="ui-container">

    <ul class="ui-list ui-list-text ui-border-tb">
        <li>
            <h4 class="ui-nowrap">订单编号:<%=orders.getId()%></h4>
        </li>
    </ul>
    <ul class="ui-list ui-list-text ui-border-b ui-list-link ui-list-active">
        <li>
            <h4 class="ui-nowrap"><i class="icon iconfont">&#xe7b8;</i> 查看物流信息</h4>
        </li>
    </ul>

    <section class="receiveInfo">
        <p><span><%=orders.getName()%></span><span style="float: right;"><%=orders.getPhone()%></span></p>
        <p style="color:#848689;"><%=orders.getAddress()%></p>
        <b class="borderT"></b>
        <b class="borderB"></b>
    </section>

    <%
        for(OrderProduct orderProduct:orderProducts){

    %>
    <ul class="order ui-list ui-list-text ui-border-tb uiListNoLink">
        <!-- [[订单商品 -->
        <li class="ui-border-b">
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
    <div class="countBlock ui-border-b">
        <p>商品合计： <span class="themeColor">&#165; <%=orders.getPrices()%></span>（共<%=orders.getNumber()%>件）</p>
        <p>运费合计： <span class="themeColor">&#165; 10.00</span></p>
    </div>
    <div class="countBlock ui-border-b">
        <p>下单时间： <span class=""><%=orders.getSetTime()%></span></p>
        <p>付款时间： <span class=""><%
            if(orders.getPayTime() ==null){
                out.println("未付款");
            }else{
                out.println(orders.getPayTime());
            }
        %></span></p>
        <p>发货时间： <span class=""><%
            switch(orders.getP()){
                case 0:out.println("未发货");
                        break;
                case 1:out.println("已发货");
                        break;
            }

        %></span></p>
    </div>

    <!-- [[联系热线 -->
    <div class="contact ui-row">
        <div class="ui-col ui-col-50">
            <a href="tel:telephonenumber" class="icon iconfont" id="phone" >&#xe672;</a>
            <label for="phone">拨打平台热线</label>
        </div>
        <div class="ui-col ui-col-50">
            <a href="tencent://message/?uin=2965366058" class="icon iconfont" id="qq">&#xe613;</a>
            <label for="qq">联系平台服务</label>
        </div>
    </div><!-- 联系热线]] -->

</section>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>

</body>
</html>