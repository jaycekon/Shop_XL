<%@ page import="com.Shop.Model.Good" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.WatchProduct" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/2 0002
  Time: 上午 10:12
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

</head>
<body>

<%@include file="footer.jsp" %>

<section class="ui-container">

    <!-- [[轮播图 -->
    <div class="ui-slider" id="carouselContainer">
        <ul class="ui-slider-content" style="width: 300%">
            <li><img src="<%=request.getContextPath()%>/app/frontStage/image/carousel1.jpg" alt=""></li>
            <li><img src="<%=request.getContextPath()%>/app/frontStage/image/carousel2.jpg" alt=""></li>
            <li><img src="<%=request.getContextPath()%>/app/frontStage/image/carousel3.jpg" alt=""></li>
        </ul>
    </div><!-- 轮播图]] -->

    <div class="productListWrapper">
        <%
            List<Good> goods = (List<Good>) request.getAttribute("goods");
            List<WatchProduct> watchProducts = (List<WatchProduct>) request.getAttribute("watchProducts");
            for (Good good : goods) {
        %>
        <!-- [[产品列表 -->
        <div class="productList">
            <div class="ui-row-flex">
                <div class="ui-col ui-col-2">
                    <a href="<%=request.getContextPath()%>/Detail/<%=good.getId()%>"><img
                            src="http://localhost:55555/Shop_XL1459415582486.jpg" alt="" class="productImg"/></a>
                </div>
                <div class="ui-col ui-col-3 ui-row-flex ui-whitespace ui-row-flex-ver productCol2">
                    <p class="ui-col ui-nowrap ui-flex-align-center productName"><a
                            href="<%=request.getContextPath()%>/Detail/<%=good.getId()%>"><%=good.getDescribes()%>
                    </a></p>
                    <p class="ui-col ui-flex  ui-flex-align-center productSpec">销售价：<span
                            class="productMoney">&#165;<%=good.getProductPrices()%></span></p>
                    <p class="ui-col ui-flex  ui-flex-align-center productSpec">批发价：<span
                            class="productMoney">&#165;<%=good.getWholesalePrices()%></span></p>
                    <%
                        if (watchProducts != null) {
                            int flag =1;
                            for (WatchProduct watchProduct : watchProducts) {
                                if (watchProduct.getGood().getId() == good.getId()) {
                                    flag = 0;
                    %>
                    <p class="ui-col ui-flex  ui-flex-align-center productSpec">倾销价：<span
                            class="productMoney">&#165;<%=good.getDumpingPrices()%></span></p>
                    <%
                        }
                    }if(flag == 1){
                        %>
                    <p class="ui-col ui-flex  ui-flex-align-center">
                        <button class="ui-btn productBtn watchPrice" onclick="window.location.href='<%=request.getContextPath()%>/watchGood/<%=good.getId()%>'">查看倾销价</button>
                    </p>
                    <%
                    }
                } else {
                %>
                    <p class="ui-col ui-flex  ui-flex-align-center">
                        <button class="ui-btn productBtn watchPrice" onclick="window.location.href='<%=request.getContextPath()%>/watchGood/<%=good.getId()%>'">查看倾销价</button>
                    </p>
                    <%

                        }
                    %>


                </div>
            </div>
        </div><!-- 产品列表]] -->
        <%
            }
        %>
    </div>

</section>

<!-- [[点击查看需要倾销 -->
<div class="ui-dialog">
    <div class="ui-dialog-cnt">
        <div class="ui-dialog-bd">
            <div>查看需要支付XX倾销币</div>
        </div>
        <div class="ui-dialog-ft">
            <button type="button" data-role="button">取消</button>
            <button type="button" data-role="button" onclick="window.location.href='recharge.html'">支付查看</button>
        </div>
    </div>
</div><!-- 点击查看需要倾销]] -->

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js?1"></script>

</body>
</html>