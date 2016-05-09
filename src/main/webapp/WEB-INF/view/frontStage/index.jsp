<%@ page import="com.Shop.Model.Good" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.WatchProduct" %>
<%@ page import="com.Shop.Model.Image" %>
<%@ page import="com.Shop.Model.User" %><%--
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
            <%
                List<Image> images = (List<Image>)request.getAttribute("images");
                User user =(User)session.getAttribute("loginUser");
                for(Image image:images){
            %>
            <li><img src="<%=image.getAddress()%>" alt=""></li>
            <%
                }
            %>
        </ul>
    </div><!-- 轮播图]] -->
    <input type="hidden" name="sign" id="sign" value="<%=user.getSign()%>"/>
    <div class="productListWrapper">
        <%
            List<Good> goods = (List<Good>) request.getAttribute("goods");
            List<WatchProduct> watchProducts = (List<WatchProduct>) request.getAttribute("watchProducts");
            for (Good good : goods) {
        %>
        <!-- [[产品列表 -->
        <div class="productList" title="<%=good.getId()%>">
            <div class="ui-row-flex">

                <div class="ui-col ui-col-2">

                    <input type ="hidden" name="wprice" id="price" value="<%=good.getwPrices()%>"/>
                    <a href="<%=request.getContextPath()%>/Detail/<%=good.getId()%>">
                        <%
                            if(good.getImg()==null){
                        %>
                        <img src="http://115.29.141.108/abdfasdf.jpg" alt="" class="productImg"/></a>
                    <%
                        }else{
                            %>
                    <img src="<%=good.getImg()%>" alt="" class="productImg"/></a>
                    <%
                        }
                    %>
                </div>
                <div class="ui-col ui-col-3 ui-row-flex ui-whitespace ui-row-flex-ver productCol2">
                    <p class="ui-col ui-nowrap ui-flex-align-center productName"><a
                            href="<%=request.getContextPath()%>/Detail/<%=good.getId()%>"><%=good.getName()%>
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
                        <button class="ui-btn productBtn watchPrice" onclick="displayPrice(<%=good.getwPrices()%>,<%=good.getId()%>)">查看倾销价</button>
                    </p>
                    <%
                    }
                } else {
                %>
                    <p class="ui-col ui-flex  ui-flex-align-center">
                        <button class="ui-btn productBtn watchPrice">查看倾销价</button>
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
<input type="hidden" name="id" class="id" id="id"/>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js?1"></script>
<div class="ui-dialog" id="goPayDialog">
    <div class="ui-dialog-cnt">
        <div class="ui-dialog-bd">
            <div></div>
        </div>
        <div class="ui-dialog-ft">
            <button type="button" data-role="button">取消</button>
            <button type="button" data-role="button">确认支付</button>
        </div>
    </div>
</div><!-- 点击查看需要倾销]] -->


<div class="ui-dialog" id="goSignDialog">
    <div class="ui-dialog-cnt">
        <div class="ui-dialog-bd">
            <div></div>
        </div>
        <div class="ui-dialog-ft">
            <button type="button" data-role="button">取消</button>
            <button type="button" data-role="button">去认证</button>
        </div>
    </div>
</div><!-- 点击查看需要倾销]] -->
<script>

        // 跳转到详情页
        $('.productList').click(function (e) {
            var pId = $(this).attr("title")
            if ($(e.target).attr("class").indexOf('watchPrice') == -1) {
                window.location.href = "<%=request.getContextPath()%>/Detail/"+pId;
            }
        });

    function displayPrice(price,id){
        var sign = $("#sign").val();
        if(sign == 1){
            $(".ui-dialog-bd").find("div").html("查看需要支付"+price+"倾销币");
            $("#id").val(id);
            watchPrice();
        }else if(sign ==0){
            $(".ui-dialog-bd").find("div").html("您当前尚未认证会员，请前往认证");
            signUser();
        }
    }
</script>
</body>
</html>