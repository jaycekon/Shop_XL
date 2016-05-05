<%@ page import="com.Shop.Model.CountOrder" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="com.Shop.Model.Profit" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/5 0005
  Time: 下午 5:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-货款列表</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/components.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">

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

            <!-- [[右边操作页面 -->
            <div class="content col-md-10 logisticsTrace">

                <!-- [[平台佣金提现列表 -->
                <div class="table-responsive">
                    <table class="table table-hover table-bordered tablestriped">
                        <tbody>
                        <tr>
                            <td>订单编号</td>
                            <td>订单付款时间</td>
                            <td class="textLeft">订单金额</td>
                            <td>订单货款</td>
                            <td class="textRight">订单PV</td>
                            <td>大区佣金</td>
                            <td>角色佣金</td>
                        </tr>
                        <%
                            String model = "yyyy-MM-dd HH:mm:ss";
                            SimpleDateFormat format=new SimpleDateFormat(model);
                            Profit profit =(Profit) request.getAttribute("profit");
                            List<Orders> orderses = (List<Orders>)request.getAttribute("orderses");
                            for(Orders orders:orderses){
                        %>
                        <tr>
                            <td><%=orders.getUuid()%></td>
                            <td><%=format.format(orders.getSetTime())%></td>
                            <td class="textLeft"><%=orders.getPrices()%></td>
                            <td><%=orders.getTotalProfit()%></td>
                            <td class="textRight"><%=orders.getTotalPV()%></td>
                            <%
                            if(orders.getRoles()!=null){%>
                                <td><%=(orders.getTotalPV()* profit.getArea_count())/100%></td>
                                <td><%=(orders.getTotalPV()* profit.getRole_count())/100%></td>

                            <%
                                }else{
                                    %>
                            <td>0</td>
                            <td>0</td>
                            <%
                                }
                            %>

                        </tr>

                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div><!-- [[平台佣金提现列表 -->

                <!-- [[分页-->
                <div class="paging">
                    <span class="prev btn btn-primary">上一页</span>
                    <span class="next btn btn-primary">下一页</span>
                </div>
                <!-- 分页]]-->

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