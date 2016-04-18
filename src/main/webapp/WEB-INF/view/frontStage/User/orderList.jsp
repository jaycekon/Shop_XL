<%@ page import="com.Shop.Util.OrderPoJo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.Orders" %>
<%@ page import="com.Shop.Model.OrderProduct" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/6 0006
  Time: 上午 10:31
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
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>我的订单</h1>
</header>

<%@include file="../footer.jsp" %>

<section class="ui-container">

    <!-- [[订单类别 -->
    <ul class="tab">
        <li>
            <a href="<%=request.getContextPath()%>/repayOrders"  id="daifukuan" class="ui-btn productBtn">待付款</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/resendOrders" id="daifahuo" class="ui-btn">待发货</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/sendOrders" id="daishouhuo" class="ui-btn">待收货</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/endOrders"  id="yiwancheng" class="ui-btn">已完成</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/reCommentOrders" id="daipingjia" class="ui-btn">待评价</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/exitOrders" id="tuikuanzhong" class="ui-btn">退款中</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>"  id="yiguanbi" class="ui-btn">已关闭</a>
        </li>
    </ul>

    <!-- 订单类别]] -->

    <!-- [[没有订单的情况 -->
    <!--  <section class="ui-notice">
          <i></i>
          <p>没有此类型的订单</p>
      </section>--><!-- [[没有订单的情况 -->


    <%
        List<OrderPoJo> orderPoJos =(List<OrderPoJo>)request.getAttribute("orderPoJos");
        if(orderPoJos!=null){
        for(OrderPoJo orderPoJo:orderPoJos){
            Orders orders = orderPoJo.getOrders();

    %>
    <!-- [[待付款 -->
    <ul class="ui-list ui-list-text ui-border-tb uiListNoLink gapT order">
        <!-- [[订单头部 -->
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4 class="ui-nowrap">订单编号：<%=orders.getId()%></h4>
            </div>
            <div class="ui-list-action themeColor">待付款</div>
        </li><!-- 订单头部]] -->
        <!-- [[订单商品 -->
        <%
            List<OrderProduct> orderProducts = orderPoJo.getOrderProduct();
            for(OrderProduct orderProduct:orderProducts){
        %>
        <li class="ui-border-t productLink">
            <div class="ui-list-thumb">
                <img src="<%=request.getContextPath()%>/app/frontStage/image/1.jpg" alt="">
            </div>
            <div class="ui-list-info">
                <h4 class="ui-nowrap"><%=orderProduct.getDescribes()%></h4>
            </div>
            <div class="textRight">
                <p class="">&#165; <%=orderProduct.getPrices()%></p>
                <p class="unMarjorColor">X<%=orderProduct.getCount()%></p>
            </div>
        </li><!-- 订单商品]] -->
       <%
            }
       %>
    </ul>

    <div class="block ui-border-b">
        <p>商品合计： <span class="themeColor">&#165; <%=orders.getPrices()%></span>（共<%=orders.getNumber()%>件）</p>
        <p>运费合计： <span class="themeColor">&#165; 0.00</span></p>
    </div>

    <div class="ui-border-b block operateBlock">
        <button class="ui-btn">取消订单</button>
        <button class="ui-btn ui-btn-danger" onclick="window.location.href='<%=request.getContextPath()%>/weixin/preparePayOrder'">去付款</button>
    </div>
    <!-- 待付款]] -->


<%
        }
    }
%>








</section>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script>
//    $('.productLink').tap(function(){
//        var $target = $(this);
//        $target.addClass('clickActive');
//        setTimeout(function(){
//            $target.removeClass('clickActive');
//        },150);
//        window.location.href = "./orderDetail.html"
//    });

    var $orderTabBtn = $('.orderTabBtn');
    $orderTabBtn.tap(function(){
        $orderTabBtn.removeClass('productBtn');
        $(this).addClass('productBtn');
    })


// 订单状态页面url解析
switch(window.location.pathname.split("/").pop())  {  // 条件根据具体情况设定
    /*待付款*/
    case "url":
        $daifukuan = $('#daifukuan');
        setActive($daifukuan);
        break;
    /*待发货*/
    case "resendOrders":
        $daifahuo = $('#daifahuo');
        setActive($daifahuo);
        break;
    /*待收货*/
    case "url":
        $daishouhuo = $('#daishouhuo');
        setActive($daishouhuo);
        break;
    /*已完成*/
    case "url":
        $yiwancheng = $('#yiwancheng');
        setActive($yiwancheng);
        break;
    /*待评价*/
    case "url":
        $daipingjia = $('#daipingjia');
        setActive($daipingjia);
        break;
    /*退款中*/
    case "url":
        $tuikuanzhong = $('#tuikuanzhong');
        setActive($tuikuanzhong);
        break;
    /*已关闭*/
    case "url":
        $yiguanbi = $('#yiguanbi');
        setActive($yiguanbi);
        break;
}


function setActive(objID){
    objID.addClass("productBtn");
}
</script>

</body>
</html>