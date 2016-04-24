<%@ page import="com.Shop.Util.OrderPoJo" %>
<%@ page import="com.Shop.Model.OrderProduct" %>
<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/1 0001
  Time: 下午 2:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>一内购-订单详情</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/order.css">

</head>
<body>

<div class="wrapper">
    <!-- [[头部 -->
    <header class="header container-fluid">
        <div class="row">
            <div class="col-xs-3">
                <img src="<%=request.getContextPath()%>/app/backStage/image/logo.jpg" alt="一内购"
                     class="logo img-responsive">
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

            <%@ include file="../left.jsp" %>

            <%
                OrderPoJo orderPojo = (OrderPoJo) request.getAttribute("orderPojo");
                Orders orders = orderPojo.getOrders();
                List<OrderProduct> orderProducts = orderPojo.getOrderProduct();
                String model = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat format = new SimpleDateFormat(model);
            %>
            <!-- [[右边操作页面 -->
            <div class="content col-md-10 orderDetail">
                <div class="order">
                    <div class="order-statue clearfix">
                        <div class="order-statue clearfix">
                            <div class="order-number pull-left">订单号：<%=orders.getId()%>
                            </div>
                            <div class="pull-right statue">
                                <%
                                    if (orders.getD() == 0) {
                                        if (orders.getF() == 0) {
                                            out.println("未付款");
                                        } else if (orders.getP() == 0) {
                                            out.println("未发货");
                                        } else if (orders.getP() == 1) {
                                            out.println("未收货");
                                        }
                                    } else if (orders.getD() == 1) {
                                        out.println("已完成");
                                    } else {
                                        out.println("已关闭");
                                    }
                                %>
                            </div>
                        </div>
                        <div class="receive-message text-left">
                            <div class="receive-title">收货人信息</div>
                            <div class="name"><%=orders.getName()%>
                            </div>
                            <div class="phone"><%=orders.getPhone()%>
                            </div>
                            <div class="address"><%=orders.getAddress()%>
                            </div>
                        </div>
                        <%
                            for (OrderProduct orderProduct : orderProducts) {


                        %>
                        <div class="product clearfix">
                            <div class="pull-left col-md-8">
                                <div class="col-md-4">
                                    <img src="<%=orderProduct.getImage()%>" alt=""/>
                                </div>
                                <div class="col-md-8 text-left">
                                    <span class="name"><%=orderProduct.getName()%></span><br/><br/>
                                    <span class="specific">描述：<%=orderProduct.getDescribes()%></span><br/>
                                </div>
                            </div>
                            <div class="pull-right width-2 text-right">
                                <span class="price">￥<%=orderProduct.getPrices()%></span><br/>
                                <span class="number">X <%=orderProduct.getCount()%></span><br/>

                            <%
                                if(orderProduct.getStauts()==1){
                            %>
                                <button class="btn btn-warning"
                                        onclick="window.location.href='<%=request.getContextPath()%>/exitToUser/<%=orderProduct.getId()%>'">
                                    同意退款
                                </button>
                                <button class="btn btn-warning"
                                        onclick="window.location.href='<%=request.getContextPath()%>/exitToUser/<%=orderProduct.getId()%>'">
                                    拒绝退款
                                </button>
                            <%
                                }else if(orderProduct.getStauts()==2){
                                    %>
                                <span class="status">已退款</span><br/>
                                <%
                                }else if(orderProduct.getExitStatus()==1){
                                %>
                                <button class="btn btn-warning"
                                        onclick="window.location.href='<%=request.getContextPath()%>/exitGood/<%=orderProduct.getId()%>'">
                                    同意退货
                                </button>
                                <button class="btn btn-warning"
                                        onclick="window.location.href='<%=request.getContextPath()%>/disagreeExit/<%=orderProduct.getId()%>'">
                                    拒绝退货
                                </button>

                                <%
                                }else if(orderProduct.getExitStatus()==2){
                                    %>
                                <button class="btn btn-warning"
                                        onclick="window.location.href='<%=request.getContextPath()%>/exitToUser/<%=orderProduct.getId()%>'">
                                    签收
                                </button>
                                <%
                                }
                            %>
                            </div>
                        </div>
                        <%
                            }
                        %>
                        <div class="order-total clearfix text-left">
                            <div class="pull-left">
                                <div class="product-total">商品合计：￥<%=orders.getPrices()%>（共<%=orders.getNumber()%>件）
                                </div>
                                <div class="product-total">大区盈利：￥<%=orders.getAreaProfit()%>
                                </div>
                                <div class="product-total">角色盈利：￥<%=orders.getRolesProfit()%>
                                </div>
                                <div class="freight">运费合计：￥0.0</div>
                            </div>
                            <%
                                if(orders.getStatus()==0){
                                if (orders.getF() == 1) {
                                    if (orders.getP() == 0) {
                            %>
                            <div class="pull-right">
                                <button class="btn btn-warning"
                                        onclick="window.location.href='<%=request.getContextPath()%>/sendOrder/<%=orders.getId()%>'">
                                    发货
                                </button>
                            </div>

                            <%
                            } else if(orders.getP()==1){
                            %>
                            <div class="product-total pull-right">已发货
                            </div>
                            <%
                                    }else if(orders.getP()==2){
                                        %>
                            <div class="product-total pull-right">已收货
                            </div>
                            <%
                                    }

                                    }
                            }%>
                        </div>


                        <div class="order-time text-left">
                            <div class="placeOrder">下单时间：<%=format.format(orders.getSetTime())%>
                            </div>
                            <div class="payOrder">付款时间：<%
                                if (orders.getPayTime() == null) {
                                    out.println("未付款");
                                } else {
                                    out.println(format.format(orders.getPayTime()));
                                }

                            %></div>
                            <div class="sendOrder">发货时间：<%
                                if (orders.getSentTime() == null) {
                                    out.println("未发货");
                                } else {
                                    out.println(format.format(orders.getSentTime()));
                                }

                            %></div>
                        </div>
                        <div class="logisticsTrace clearfix">
                            <%
                                if (orders.getP() == 1) {
                            %>
                            <a href="./logisticsTrace.html">
                                <button class="btn btn-primary">物流追踪</button>
                            </a>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>
                <!-- 右边操作页面]] -->
            </div>

        </div>
        <!-- 页面主体]] -->
    </div>
    <!-- wrapper]] -->

    <script src="<%=request.getContextPath()%>/app/backStage/lib/jquery/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>

</div>
</body>
</html>