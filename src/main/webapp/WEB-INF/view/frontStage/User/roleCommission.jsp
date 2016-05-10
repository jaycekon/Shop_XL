<%@ page import="com.Shop.Model.Roles" %>
<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Util.OrderPoJo" %>
<%@ page import="com.Shop.Model.OrderProduct" %>
<%@ page import="com.Shop.Model.Profit" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
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
    <h1>分佣订单</h1>
</header>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">
        <li class="footerItem"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/roleCenter'"></i><div>角色中心</div></li>
    </ul>
</footer>

<section class="ui-container">
<%
    Roles roles = (Roles)session.getAttribute("roles");
    Profit profit = (Profit)request.getAttribute("profit");
%>
    <!-- [[佣金统计 -->
    <ul class="ui-list ui-border-tb commissionTop">
        <li class="ui-border-t">
            <div class="ui-avatar">
                <img src="<%=roles.getImg()%>" alt="">
            </div>
            <div class="ui-list-info">
                <h4 class="ui-nowrap">总佣金：<span class="themeColor">&#165; <%=roles.getTotalCommission()%></span></h4>
                <h4 class="ui-nowrap">可提现佣金：<span class="themeColor">&#165; <%=roles.getExitCommission()%></span></h4>
                <h4 class="ui-nowrap">待结算佣金：<span class="themeColor">&#165; <%=roles.getWaitCommission()%></span></h4>
            </div>
        </li>
    </ul> <!-- 佣金统计]] -->

    <%
        List<OrderPoJo> orderPoJos = (List<OrderPoJo>) request.getAttribute("orderPoJos");
        String model = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format=new SimpleDateFormat(model);
        for(OrderPoJo orderPoJo:orderPoJos){
            Orders orders =orderPoJo.getOrders();
            List<OrderProduct> orderProducts  =orderPoJo.getOrderProduct();
    %>
    <ul class="ui-border-tb ui-list">
        <!-- [[订单头部 -->
        <li class="ui-border-b">
            <div class="ui-list-info">
                <h4 class="ui-nowrap">时间：<%=format.format(orders.getSetTime())%></h4>
            </div>
            <div class="ui-list-info" style="text-align: right;">
                <div class="ui-list-action"><span class="themeColor"><%
                    if (orders.getD() == 0) {
                        out.println("待结算");
                    } else if (orders.getD() == 1) {
                        out.println("已结算");
                    } else {
                        out.println("结算失败");
                    }
                %></span></div>
            </div>
        </li><!-- 订单头部]] -->
        <!-- [[单个商品 -->
        <%
        %>
        <div class="productBox ui-border-b">
            <div class="col describeCol">
                <p>昵称：<%=orders.getUser().getUsername()%></p>
                <p>金额：&#165; <%=orders.getPrices()%></p>
                <p>佣金：&#165; <%=(orders.getTotalPV()* profit.getRole_count())/100%></p>
                <p>订单状态：<%
                    if (orders.getD() == 0) {
                        if (orders.getF() == 0) {
                            out.println("未付款");
                        } else if (orders.getP() == 0) {
                            out.println("未发货");
                        } else if (orders.getP() == 1) {
                            out.println("未收货");
                        } else if(orders.getP()==2){
                            out.println("已收货");
                        }
                    } else if (orders.getD() == 1) {
                        out.println("已完成");
                    } else {
                        out.println("已关闭");
                    }
                %></p>
            </div>
        </div><!-- 单个商品]] -->
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
