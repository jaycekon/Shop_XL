<%@ page import="com.Shop.Model.OrderProduct" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.Cart" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/2 0002
  Time: 上午 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
    <h1>购物车</h1>
</header>

<%@include file="../footer.jsp" %>

<section class="ui-container">

    <%
        if(request.getAttribute("cart")!=null){
        Cart cart = (Cart) request.getAttribute("cart");
        List<OrderProduct> orderProducts = (List<OrderProduct>) request.getAttribute("orderProducts");
    %>


    <!-- [[有商品 -->
    <div id="hasProductWrapper">
        <%

            for (OrderProduct orderProduct : orderProducts) {
        %>
        <div class="productsBox ui-border-tb ">

            <!-- [[单个商品 -->
            <div class="productBox ui-border-b" data-unitPrice="<%=orderProduct.getPrices()%>" id="<%=orderProduct.getId()%>">
                <div class="col imgCol">
                    <a href="<%=request.getContextPath()%>/Detail/<%=orderProduct.getGood_id()%>">
                        <%
                            if(orderProduct.getImage()!=null){
                                %>

                        <img src="<%=orderProduct.getImage()%>" alt="">
                        <%
                            }else{
                        %>
                            <img src="<%=request.getContextPath()%>/app/frontStage/image/1.jpg" alt="">
                        <%
                            }
                        %>
                    </a>
                </div>
            <%--<input type="hidden" class="id" value="<%=orderProduct.getId()%>"/>--%>
                <div class="col describeCol">
                    <h4 class="intwoline productName"><%=orderProduct.getName()%>
                    </h4>
                    <div>
                        <div class="selectNum">
                            <button class="reduce minus">&#45;</button>
                            <input type="number" class="productNum" value="<%=orderProduct.getCount()%>"
                                   data-min="<%=orderProduct.getWholeSaleCount()%>" data-max="<%=orderProduct.getMaxCount()%>"
                                        title="number"/>
                            <button class="add plus">&#43;</button>
                        </div>
                    </div>
                </div>

                <div class="col rightCol">
                    <%--<a href="<%=request.getContextPath()%>/removeProduct/<%=orderProduct.getId()%>">删除</a>--%>
                            <button onclick="delProduct(this,<%=orderProduct.getId()%>)"><i class="ui-icon-delete productDel"></i></button>
                    <p class="price">&#165; <%=orderProduct.getPrices()%>
                    </p>
                </div>

            </div><!-- 单个商品]] -->


        </div>
        <%
            }


        %>
            <!-- [[价格合计 -->
            <ul class="countBlock ui-border-b">
                <li>商品合计： <span class="themeColor"
                                id="amount">&#165; <fmt:formatNumber value = "<%=cart.getTotalPrices()%>" pattern="#0.00"/></span>（共<span id="num"><%=cart.getCount()%></span>件）

                </li>
                <li>运费合计： <span class="themeColor" id="freight">&#165; 0.00</span></li>
            </ul><!-- 价格合计]] -->

        <div class="ui-btn-wrap">
            <button class="ui-btn-lg productBtn"
                    onclick="window.location.href='<%=request.getContextPath()%>/createOrder'">
                去结算
            </button>
        </div>
            <%
                 } else {
            %>



        <!-- [[没有商品 -->
        <section class="ui-notice" id="noProductWrapper">
            <i></i>
            <p>购物车还是空的</p>
            <div class="ui-notice-btn">
                <button class="ui-btn-lg productBtn" onclick="window.location.href='<%=request.getContextPath()%>/'">去购买</button>
            </div>
        </section>
        <%
            }
        %>


    </div><!-- 没有商品]] -->

</section>


<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/shopCart.js"></script>

</body>
</html>
