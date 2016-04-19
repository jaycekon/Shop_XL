<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.Roles" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/19 0019
  Time: 下午 2:29
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/withdraw.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/font/iconfont.css">

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>角色提现明细</h1>
</header>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">
        <li class="footerItem active"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/roleCenter'"></i><div>角色中心</div></li>
    </ul>
</footer>

<section class="ui-container">

    <table class="table ui-table ui-border ui-border-tb">
        <thead>
        <tr>
            <th>订单编号</th>
            <th>分佣金额（元）</th>
            <th>订单时间</th>
            <th>佣金状态</th>
        </tr>

        </thead>
        <tbody>
        <%
            List<Orders> orderses = (List<Orders>)request.getAttribute("orderses");
            for(Orders orders:orderses){
                if(session.getAttribute("roles")!=null){
        %>
        <tr>
            <td><%=orders.getUuid()%></td>
            <td><%=orders.getRolesProfit()%></td>
            <td><%=orders.getSetTime()%></td>
            <td><%
                if(orders.getD()==1){
                    out.println("已结算");
                }else{
                    out.println("待结算");
                }
            %></td>
        </tr>
        <%
                }
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
