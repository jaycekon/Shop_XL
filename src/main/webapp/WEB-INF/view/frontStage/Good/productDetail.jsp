<%@ page import="com.Shop.Model.Good" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/2 0002
  Time: 上午 10:39
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/index.css?12">
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <h1>商品详情</h1>
</header>

<%@include file="../footer.jsp" %>

<section class="ui-container">

    <!-- [[轮播图 -->
    <div class="ui-slider" id="productDetailSlider">
        <ul class="ui-slider-content" style="width: 300%">
            <li><img src="<%=request.getContextPath()%>/app/frontStage/image/1.jpg" alt=""></li>
            <li><img src="<%=request.getContextPath()%>/app/frontStage/image/2.jpg" alt=""></li>
            <li><img src="<%=request.getContextPath()%>/app/frontStage/image/3.jpg" alt=""></li>
        </ul>
    </div><!-- 轮播图]] -->

    <%
        Good good = (Good)request.getAttribute("good");


    %>
    <!-- [[商品参数 -->
    <div class="ui-row productSpecBlock">
        <p class="specText"><span>零售价：</span><span class="themeColor">&#165;<%=good.getProductPrices()%></span></p>
        <p class="specText"><span>批发价：</span><span class="themeColor">&#165;<%=good.getWholesalePrices()%></span></p>

        <p class="specText"><span>倾销价：
        <%
            if(request.getAttribute("watchProduct")!=null){
        %>
        </span><span class="themeColor">&#165;<%=good.getDumpingPrices()%></span></p>
        <%
            }else{
                %>
        <p class="ui-col ui-flex  ui-flex-align-center">
            <button class="ui-btn productBtn watchPrice" onclick="window.location.href='<%=request.getContextPath()%>/watchGood/<%=good.getId()%>'">查看倾销价</button>
        </p>
        <%
            }
        %>
        <p class="specText"><span>运费：</span>0.00</p>
        <p class="specText"><span>库存：</span><span id="stock"><%=good.getNum()%></span></p>
        <p class="specText"><span>起批量：</span><%=good.getWholesaleCount()%></p>
        <p class="specText"><span>销量：</span><%=good.getSaleCount()%></p>
        <button class="ui-btn addCartBtn" id="addCartBtn"><i class="ui-icon-cart"></i></button>
        <form action = "<%=request.getContextPath()%>/buyGood" method = "post">
            <input type = "number" name="count"/>
            <input type = "hidden" value ="<%=good.getId()%>" name ="good_id"/>
            <%--<div class="ui-poptips ui-poptips-success addTip">--%>
            <%--<div class="ui-poptips-cnt"><i></i>1件商品成功加入购物车</div>--%>
            <input type = "submit" value ="购买"/>
            <%--</div>--%>
        </form>

    </div>
    <!-- 商品参数]] -->

    <!-- [[商品介绍和评价-->
    <div class="ui-tab bottomBlock">
        <ul class="ui-tab-nav ui-border-b">
            <li class="current">商品介绍</li>
            <li>商品评价</li>
        </ul>
        <ul class="ui-tab-content" style="width:300%">
            <!-- [[商品介绍内容 -->
            <li class="productIntro"><!--
                <p class="content">这是正文内容</p>-->
                <img src="<%=request.getContextPath()%>/app/frontStage/image/1.jpg" alt="">
                <img src="<%=request.getContextPath()%>/app/frontStage/image/1.jpg" alt="">
            </li><!-- 商品介绍内容]] -->
            <!-- [[商品评价内容 -->
            <li class="productComment">
                <ul class="ui-list ui-list-pure ui-border-tb">
                    <li class="ui-border-t">
                        <p><span>1.faycheng </span><span class="date"> 2月12日</span></p>
                        <h4>这本书太赞了，每次看都有不一样的体会和感悟，超级喜欢！期待大结局。</h4>
                    </li>
                    <li class="ui-border-t">
                        <p><span>2.faycheng </span><span class="date"> 2月12日</span></p>
                        <h4>这本书太赞了，每次看都有不一样的体会和感悟，超级喜欢！期待大结局。</h4>
                    </li>
                    <li class="ui-border-t">
                        <p><span>3.faycheng </span><span class="date"> 2月12日</span></p>
                        <h4>这本书太赞了，每次看都有不一样的体会和感悟，超级喜欢！期待大结局。</h4>
                    </li>
                </ul>
            </li><!-- 商品评价内容]] -->
        </ul>
    </div>
    <!-- 商品介绍和评价]]-->
    <!--
        <button class="ui-btn" onclick="location.reload()">刷新</button>-->

</section>

<!-- [[选择商品数量-->
<section class="ui-actionsheet">
    <div class="rise_div">
        <div class="rise_div_top">
            <span class="ui-icon-close-page fr" id="rise_div_close"></span>
        </div>
        <div class="rise_div_mid select_num">
            购买数量
            <div class="selectBlock selectNum">
                <button class="plus">&#43;</button>
                <input type="number" value="1" id="num">
                <button class="minus">&#45;</button>
            </div>
        </div>
        <div class="rise_div_bottom">
            <button class="lg_btn" id="addCartSure">确定</button>
        </div>
    </div>

</section><!-- 选择商品数量]] -->

<!-- [[点击查看需要倾销 -->
<div class="ui-dialog">
    <div class="ui-dialog-cnt">
        <div class="ui-dialog-bd">
            <div>查看需要支付XX倾销币</div>
        </div>
        <div class="ui-dialog-ft">
            <button type="button" data-role="button">取消</button>
            <button type="button" data-role="button">支付查看</button>
        </div>
    </div>
</div><!-- 点击查看需要倾销]] -->

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js?12"></script>
<script>
    /*    wx.config({
     debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
     appId: 'wx0c8e5793aa3e836b', // 必填，公众号的唯一标识
     timestamp: 1459498527595, // 必填，生成签名的时间戳
     nonceStr: 'qml', // 必填，生成签名的随机串
     signature: '8d1a43795fc75a6ac64cbc1698578bdff098d153',// 必填，签名，见附录1
     jsApiList: ['previewImage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
     });*/


    /*    $('#productDetailSlider img').tap(function(){
     wx.previewImage({
     current: 'http://lanlan.tunnel.qydev.com/multipleShop/frontStage/page/productDetail.html', // 当前显示图片的http链接
     urls: [
     'http://lanlan.tunnel.qydev.com/multipleShop/frontStage/image/1.jpg',
     'http://lanlan.tunnel.qydev.com/multipleShop/frontStage/image/2.jpg',
     'http://lanlan.tunnel.qydev.com/multipleShop/frontStage/image/3.jpg',
     ] // 需要预览的图片http链接列表
     });
     })*/

</script>

</body>
</html>
