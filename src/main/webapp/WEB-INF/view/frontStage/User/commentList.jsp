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
    <h1>我的订单</h1>
</header>

<%@include file="../footer.jsp" %>

<section class="ui-container">

    <!-- [[订单类别 -->
    <ul class="tab">
        <li>
            <a href="<%=request.getContextPath()%>/repayOrders" id="daifukuan" class="ui-btn">待付款</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/resendOrders" id="daifahuo" class="ui-btn">待发货</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/sendOrders" id="daishouhuo" class="ui-btn">待收货</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/getOrders" id="yishouhuo" class="ui-btn">已收货</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/reCommentOrders" id="daipingjia" class="ui-btn">待评价</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/exitOrders" id="tuikuanzhong" class="ui-btn">退款中</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/exitGoods" id="tuihuozhong" class="ui-btn">退货中</a>
        </li>
        <li>
            <a href="<%=request.getContextPath()%>/closeOrders" id="yiguanbi" class="ui-btn">已关闭</a>
        </li>
    </ul>

    <!-- 订单类别]] -->

    <!-- [[没有订单的情况 -->
    <!--  <section class="ui-notice">
          <i></i>
          <p>没有此类型的订单</p>
      </section>--><!-- [[没有订单的情况 -->


    <ul class="order ui-list ui-list-text ui-border-tb uiListNoLink">
        <%
            List<OrderProduct> orderProducts = (List<OrderProduct>)request.getAttribute("orderProducts");
            for(OrderProduct orderProduct:orderProducts){
        %>
        <!-- [[订单商品 -->
        <li class="ui-border-b gapT">
            <div class="ui-list-thumb">
                <%
                    if(orderProduct.getImage()==null){
                %>
                <img src="<%=request.getContextPath()%>/app/frontStage/image/1.jpg" alt="">
                <%
                    }else{
                        %>
                <img src="<%=orderProduct.getImage()%>" alt="">
                <%
                    }
                %>
            </div>
            <div class="ui-list-info textMid">
                <h4 class="ui-nowrap"><%=orderProduct.getDescribes()%></h4>
            </div>
            <div class="textRight">
                <p class="">&#165; <%=orderProduct.getPrices()%></p>
                <p class="unMarjorColor">X<%=orderProduct.getCount()%></p>
                <button class="ui-btn productBtn commentBtn" data-comment="">评论</button>
                <input type ="hidden" name="id" class = "orderProductId" value="<%=orderProduct.getId()%>"/>
            </div>
        </li><!-- 订单商品]] -->

        <%
            }
        %>
    </ul>
</section>

<section class="ui-input-wrap ui-border-t hide myComment">
    <div class="ui-input ui-border-radius">
        <input type="text" name="" value="" placeholder="写下你对产品的评论吧...">
    </div>
    <button class="ui-btn" id="submitComment">评论</button>
</section>

<input type="hidden" class="id" name ="id" value=""/>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script>
    // 填写评论
    var $myComment = $('.myComment');
    var $commentInput = $('.myComment input');
    var $hasComment;
    var index;

    $('.commentBtn').each(function(i,value){
        value.i = i;
        $(this).tap(function(){
            var id= $(this).next().val();
            $('.id').val(id);
            if( this.i == index){
                $myComment.toggleClass('hide');
                $(this).attr("data-comment", $commentInput.val());
            }else {
                $commentInput.val( $(this).attr("data-comment") );
                $myComment.removeClass('hide');
                $hasComment = $(this);
                index = this.i;
            }

        });

    });

    // 提交评论
    $('#submitComment').tap(function(e){

        var comment =  $.trim($('.myComment input').val());
        index = $('.id').val();
        if( comment == "") {
            el=$.tips({
                content:' 评论内容不能为空哦~ ',
                stayTime:2000,
                type:"success"
            });
        }else {
            $.ajax({
                type: 'POST',
                data:{id:index,text:comment},
                url: '<%=request.getContextPath()%>/commentProduct',
                dataType: "json",
                success: function(data){
                    console.log(data);
                    el=$.tips({
                        content:' 评论成功',
                        stayTime:2000,
                        type:"success"
                    });

                    // 防止点透
                    setTimeout(function(){
                        $('.myComment').addClass('hide');
                        // 删除评论按钮
                        $hasComment.remove();
                    },300);

                },
                error: function(){
                    el=$.tips({
                        content:' 评论失败',
                        stayTime:2000,
                        type:"success"
                    });
                }
            })
        }
    });

    // 触摸隐藏评论
    $(".ui-container").on('touchmove',function(e){
        $('.myComment').addClass('hide');
    });


    var $orderTabBtn = $('.orderTabBtn');
    $orderTabBtn.tap(function () {
        $orderTabBtn.removeClass('productBtn');
        $(this).addClass('productBtn');
    })


    // 订单状态页面url解析
    switch (window.location.pathname.split("/").pop()) {  // 条件根据具体情况设定
        /*待付款*/
        case "repayOrders":
            $daifukuan = $('#daifukuan');
            setActive($daifukuan);
            break;
        /*待发货*/
        case "resendOrders":
            $daifahuo = $('#daifahuo');
            setActive($daifahuo);
            break;
        /*待收货*/
        case "sendOrders":
            $daishouhuo = $('#daishouhuo');
            setActive($daishouhuo);
            break;
        /*已完成*/
        case "getOrders":
            $yiwancheng = $('#yishouhuo');
            setActive($yiwancheng);
            break;
        /*待评价*/
        case "reCommentOrders":
            $daipingjia = $('#daipingjia');
            setActive($daipingjia);
            break;
        /*退款中*/
        case "exitOrders":
            $tuikuanzhong = $('#tuikuanzhong');
            setActive($tuikuanzhong);
            break;
        /*已关闭*/
        case "closeOrders":
            $yiguanbi = $('#yiguanbi');
            setActive($yiguanbi);
            break;
        /*退货中*/
        case "exitGoods":
            $yiguanbi = $('#tuihuozhong');
            setActive($yiguanbi);
            break;
    }


    function setActive(objID) {
        objID.addClass("productBtn");
    }
</script>

</body>
</html>