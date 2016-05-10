<%@ page import="com.Shop.Model.Address" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.OrderProduct" %>
<%@ page import="com.Shop.Model.Cart" %>
<%@ page import="javax.persistence.criteria.Order" %>
<%@ page import="com.Shop.Model.Area" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/2 0002
  Time: 上午 11:47
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
    <h1>结算中心</h1>
</header>

<%@include file="../footer.jsp" %>

<section class="ui-container">

    <!-- [[收货地址 -->

    <%
        Address address =(Address) request.getAttribute("address");
        List<OrderProduct> orderProducts =(List<OrderProduct>) request.getAttribute("orderProducts");
        Cart cart = (Cart)request.getAttribute("cart");
        if(address == null){
    %>
    <!-- 若没有收货地址-->

    <ul class="ui-list ui-border-tb ui-list-link ui-list-active">
        <li class="ui-list-info" onclick="window.location.href='<%=request.getContextPath()%>/myAddress'">选择收货地址</li>
    </ul>
    <%
        }
        else{
    %>
        <section class="receiveInfo" onclick="window.location.href='<%=request.getContextPath()%>/myAddress'">
        <p><span><%=address.getUsername()%></span><span style="float: right;"><%=address.getPhone()%></span></p>
            <%
                if(address.getA()!=null){
                Area city = address.getA().getArea();
                Area s = city.getArea();
            %>
        <p style="color:#848689;"><%=s.getName()+city.getName()+address.getA().getName()+address.getAddress()%></p>
        <b class="borderT"></b>
        <b class="borderB"></b>
            <%
                }else{
            %>
            <p style="color:#848689;"><%=address.getAddress()%></p>
            <b class="borderT"></b>
            <b class="borderB"></b>
            <%

                }
            %>

        </section><!-- [[收货地址 -->
    <%
            }
    %>

    <div class="productsBox ui-border-tb">
        <%
            for(OrderProduct orderProduct :orderProducts){


        %>
        <!-- [[单个商品 -->
        <div class="productBox ui-border-b">

            <div class="col imgCol">
                <img src="<%=request.getContextPath()%>/app/frontStage/image/1.jpg" alt="">
            </div>

            <div class="col describeCol">
                <h4 class="intwoline productName"><%=orderProduct.getName()%></h4>
                <p class="setBottom"><span class="unMarjorColor">x<%=orderProduct.getCount()%></span><span>&#165; <%=orderProduct.getPrices()%></span></p>
            </div>

        </div><!-- 单个商品]] -->
        <%
            }
        %>
    </div>

    <!-- [[价格合计 -->
    <ul class="countBlock ui-border-b">
        <li>商品合计： <sapn class="themeColor">&#165; <%=cart.getTotalPrices()%></sapn>（共<%=cart.getCount()%>件）</li>
        <li>运费合计： <sapn class="themeColor">&#165; 10.00</sapn></li>
    </ul><!-- 价格合计]] -->

    <div class="ui-btn-wrap" >
        <%
            if(address ==null){
                %>
        <%--<button class="ui-btn-lg productBtn" onclick="window.location.href='<%=request.getContextPath()%>/createOrder'" >--%>
            <%--提交订单--%>
        <%--</button>--%>
        <%
            }else{
        %>
        <button class="ui-btn-lg productBtn" onclick="window.location.href='<%=request.getContextPath()%>/createOrder/<%=address.getId()%>'" >
            提交订单
        </button>
        <%
            }
        %>
    </div>
</section>


<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>

</body>
</html>