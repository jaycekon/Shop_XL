<%@ page import="com.Shop.Util.OrderPoJo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="com.Shop.Model.OrderProduct" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/31 0031
  Time: 下午 4:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-订单列表</title>
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
                <div class="order-type text-left">
                    <a href ="<%=request.getContextPath()%>/listOrderByF/0"><button class="btn btn-default">待付款</button></a>
                    <a href ="<%=request.getContextPath()%>/listOrderByF/1"><button class="btn btn-default">待发货</button></a>
                    <a href ="<%=request.getContextPath()%>/listOrderByP/1"><button class="btn btn-default">待收货</button></a>
                    <a href ="<%=request.getContextPath()%>/listOrderByC/0"><button class="btn btn-default">待评价</button></a>
                    <a href ="<%=request.getContextPath()%>/listOrderByD/1"><button class="btn btn-default">已完成</button></a>
                    <a href ="<%=request.getContextPath()%>/listOrderByT/1"><button class="btn btn-default">退款中</button></a>
                    <a href ="<%=request.getContextPath()%>/listOrderByD/2"><button class="btn btn-default">已关闭</button></a>
                </div>
                <!-- [[订单-->
                <%
                    List<OrderPoJo> orderPoJos = (List<OrderPoJo>)request.getAttribute("orderPoJos");
                    for(OrderPoJo orderPoJo:orderPoJos){
                        Orders orders = orderPoJo.getOrders();
                        List<OrderProduct> orderProducts = orderPoJo.getOrderProduct();
                %>
                <div class="order">
                    <a href="<%=request.getContextPath()%>/orderDetail/<%=orders.getId()%>">
                        <div class="order-statue clearfix">
                            <div class="company pull-left">订单号：<%=orders.getId()%></div>
                            <div class="pull-right contact">订单状态：<%
                                if(orders.getD()==0){
                                    if(orders.getF()==0){
                                        out.println("未付款");
                                    }else if(orders.getT()==1){
                                        out.println("申请退款");
                                    }else if(orders.getT()==2){
                                        out.println("已退款");
                                    }
                                    else if(orders.getP()==0){
                                        out.println("未发货");
                                    }else if(orders.getP()==1){
                                        out.println("未收货");
                                    }
                                }else if(orders.getD()==1){
                                    out.println("已完成");
                                }else{
                                    out.println("已关闭");
                                }
                            %></div>
                        </div>
                        <%
                            for(OrderProduct orderProduct:orderProducts){
                        %>
                        <div class="product clearfix">
                            <div class="pull-left col-md-8">
                                <div class="col-md-4">
                                    <img src="<%=orderProduct.getImage()%>" alt=""/>
                                </div>
                                <div class="col-md-8 text-left">
                                    <span class="name"><%=orderProduct.getName()%></span><br /><br />
                                    <span class="specific">商品描述：<%=orderProduct.getDescribes()%></span><br />
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
                <%
                    }
                %>
                <!-- 订单]]-->
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