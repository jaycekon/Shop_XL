<%@ page import="com.Shop.Util.ExpressBean" %>
<%@ page import="com.Shop.Util.ExpressContext" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="com.Shop.Model.ExitOrders" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/25 0025
  Time: 下午 2:35
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

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>物流追踪</h1>
</header>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">
        <li class="footerItem active" onclick="window.location.href='./index.html'"><i class="ui-icon-home"></i><div>首页</div></li>
        <li class="footerItem"><i class="ui-icon-cart" onclick="window.location.href='./shopCart.html'"></i><div>购物车</div></li>
        <li class="footerItem"><i class="ui-icon-personal" onclick="window.location.href='./storecenter.html'"></i><div>个人中心</div></li>
    </ul>
</footer>

<section class="ui-container">
    <%
        ExpressBean expressBean =(ExpressBean)request.getAttribute("expressBean");
        List<ExpressContext> datas = expressBean.getData();
        if(request.getAttribute("orders")!=null){
            Orders orders = (Orders)request.getAttribute("orders");
    %>
    <div class="countBlock ui-border-b">
        <p>运单编号：<%=orders.getCarriageCode()%></p>
        <p>物流公司：<%=orders.getLogistic().getLogis_comp_name()%></p>
    </div>
<%
    }else if(request.getAttribute("exitOrders")!=null){
        ExitOrders exitOrders =(ExitOrders) request.getAttribute("exitOrders");
        %>
    <div class="countBlock ui-border-b">
        <p>运单编号：<%=exitOrders.getCarriageCode()%></p>
        <p>物流公司：<%=exitOrders.getLogistic().getLogis_comp_name()%></p>
    </div>
    <%
    }
%>
    <table class="ui-table ui-border-tb logisticsInfo countBlock ui-border-tb">
        <tbody>
        <%
            for(ExpressContext expressContext:datas){
        %>
        <tr class="active">
            <td><%=expressContext.getTime()%></td>
            <td><%=expressContext.getContext()%></td>
        </tr>
       <%
           }
       %>
        </tbody>
    </table>

</section>


<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>

</body>
</html>
