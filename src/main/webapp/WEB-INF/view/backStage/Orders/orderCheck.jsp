<%@ page import="com.Shop.Util.OrderPoJo" %>
<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.OrderProduct" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/1 0001
  Time: 下午 3:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-订单查询</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/order.css">

</head>
<body>

<div class="wrapper">
    <!-- [[头部 -->
    <header class="header container-fluid">
        <div class="row">
            <div class="col-xs-3">
                <img src="<%=request.getContextPath()%>/app/backStage/image/logo.jpg" alt="一内购" class="logo img-responsive">
                <button class="navbar-toggle collapsed" type="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="col-xs-6 header_center">
                <h1 class="websiteName">百城万店倾销网平台</h1>
            </div>
            <div class="col-xs-3">
                <button class="btnExit fr" type="button"><span class="glyphicon glyphicon-off"></span>退出</button>
            </div>
        </div>
    </header><!-- 头部]] -->

    <!-- [[页面主体 -->
    <div class="mainBody container-fluid">

        <div class="row">

            <%@ include file="../left.jsp"%>

            <!-- [[右边操作页面 -->
            <div class="content col-md-10 orderManager">
                <div class="order-check text-left clearfix">
                    <form action = "orderCheck" method = "POST">
                        <label class="col-md-4">
                         <input type="text" name = "id" class="form-control" placeholder="请输入要查询的订单编号（精确查询）"/>
                        </label>
                         <input type ="submit" value ="查询"/>
                        <%--<button class="btn btn-primary">查询</button>--%>
                    </form>
                </div>
                <!-- [[订单-->
                <%
                    if(request.getAttribute("orderPoJo") != null){
                        OrderPoJo orderPojo = (OrderPoJo)request.getAttribute("orderPoJo");
                        Orders orders = orderPojo.getOrders();
                        List<OrderProduct> orderProducts = orderPojo.getOrderProduct();

                %>
                <div class="order">
                    <a href="<%=request.getContextPath()%>/orderDetail/<%=orders.getId()%>">
                        <div class="order-statue clearfix">
                            <div class="company pull-left">订单号：<%=orders.getId()%></div>
                            <div class="pull-right contact">
                                <%
                                    switch(orders.getD()){
                                        case 0 :out.println("未确认");
                                            break;
                                        case 1 :out.println("已确认");
                                            break;
                                        case 2 :out.println("已完成");
                                            break;
                                        case 3 :out.println("已作废");
                                            break;
                                        case 4 :out.println("已取消");
                                            break;
                                    }
                                %>
                            </div>
                        </div>
                        <%
                            for(OrderProduct orderProduct :orderProducts){


                        %>
                        <div class="product clearfix">
                            <div class="pull-left col-md-8">
                                <div class="col-md-4">
                                    <img src="<%=request.getContextPath()%>/app/backStage/image/user.jpg" alt=""/>
                                </div>
                                <div class="col-md-8 text-left">
                                    <span class="name"><%=orderProduct.getName()%></span><br /><br />
                                    <span class="specific">详情：<%=orderProduct.getDescribes()%></span><br />
                                </div>
                            </div>
                            <div class="pull-right width-2 text-right">
                                <span class="price">￥<%=orderProduct.getPrices()%></span><br /><br />
                                <span class="number">X <%=orderProduct.getCount()%></span>
                            </div>
                        </div>
                        <%
                            }
                        %>
                        <div class="order-total clearfix text-left">
                            <div class="product-total">商品合计：￥<%=orders.getPrices()%>（共<%=orders.getNumber()%>件）</div>
                            <div class="freight">运费合计：￥0.00</div>
                        </div>
                    </a>
                </div>
                <!-- 订单]]-->
                <%
                    }
                %>
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
