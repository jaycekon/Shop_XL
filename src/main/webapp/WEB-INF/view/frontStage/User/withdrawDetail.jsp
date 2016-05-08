<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.Roles" %>
<%@ page import="com.Shop.Model.WithdrawalsOrder" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
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
    <h1>角色提现明细</h1>
</header>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">
        <%
            if(session.getAttribute("roles")!=null){
        %>
        <li class="footerItem active"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/roleCenter'"></i><div>角色中心</div></li>
        <%
        }
        else if(session.getAttribute("areas")!=null){
        %>
        <li class="footerItem active"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/areaCenter'"></i><div>大区中心</div></li>
        <%
            }
        %>
    </ul>
</footer>

<section class="ui-container">

    <table class="table ui-table ui-border ui-border-tb">
        <thead>
        <tr>
            <th>订单时间</th>
            <th>提现金额（元）</th>
            <th>佣金状态</th>
        </tr>

        </thead>
        <tbody>
        <%
           List<WithdrawalsOrder> withdrawalsOrders =(List<WithdrawalsOrder>)request.getAttribute("withdrawalsOrders");
            String model = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat format=new SimpleDateFormat(model);
            if(withdrawalsOrders !=null){
            for(WithdrawalsOrder withdrawalsOrder:withdrawalsOrders){
        %>
        <tr>
            <td><%=format.format(withdrawalsOrder.getDate())%></td>
            <td><%=withdrawalsOrder.getPrices()%></td>
            <td><%
                switch(withdrawalsOrder.getStatus()){
                    case 0:out.println("待审核");
                        break;
                    case 1:out.println("通过审核");
                        break;
                    case 2:out.println("未通过审核");
                        break;
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
