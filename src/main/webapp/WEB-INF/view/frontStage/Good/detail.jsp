<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.Shop.Model.*" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/20 0020
  Time: 下午 7:43
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/product.css?12">

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <h1>商品详情</h1>
</header>

<%@include file="../footer.jsp"%>

<section class="ui-container">

    <!-- [[轮播图 -->
    <div class="ui-slider" id="productDetailSlider">

        <ul class="ui-slider-content" style="width: 300%">
            <%
                String model = "yyyy-MM-dd";
                SimpleDateFormat format=new SimpleDateFormat(model);
                User user = (User)session.getAttribute("loginUser");
                List<Image> images =(List<Image>)request.getAttribute("images");
                if(images!=null){
                    for(Image image:images){
            %>
            <li><img src="<%=image.getAddress()%>" alt=""></li>
        <%
                }
            }else{
        %>
            <li><img src="<%=request.getContextPath()%>/app/frontStage/image/1.jpg" alt=""></li>
            <li><img src="<%=request.getContextPath()%>/app/frontStage/image/2.jpg" alt=""></li>
            <li><img src="<%=request.getContextPath()%>/app/frontStage/image/3.jpg" alt=""></li>
            <%
                }
            %>
        </ul>
    </div><!-- 轮播图]] -->

    <!-- [[商品参数 -->
    <div class="ui-row productSpecBlock">
        <input type="hidden" name="sign" id="sign" value="<%=user.getSign()%>"/>
        <%
            Good good = (Good) request.getAttribute("good");
        %>
        <p class="specText"><span>零售价：</span><span class="themeColor">&#165;<%=good.getProductPrices()%></span></p>

        <p class="specText"><span>市场价：</span><span class="themeColor">&#165;<%=good.getWholesalePrices()%></span></p>
        <%if(request.getAttribute("watchProduct")!=null){
        %>
        <p class="specText"><span>倾销价：</span><span class="themeColor">&#165;<%=good.getDumpingPrices()%></span></p>

        <%
        }
        %>
        <p class="specText"><span>库存：</span><span id="stock"><%=good.getNum()%></span></p>
        <p class="specText"><span>起批量：</span><%=good.getWholesaleCount()%></p>
        <p class="specText"><span>销量：</span><%=good.getSaleCount()%></p>
        <%
            if(request.getAttribute("watchProduct")==null){
        %>
        <p class="ui-col ui-flex  ui-flex-align-center">
            <button class="ui-btn productBtn watchPrice" onclick="displayPrice(<%=good.getwPrices()%>,<%=good.getId()%>)">查看倾销价</button>
        </p>
        <%
            }
        %>

        <form action="<%=request.getContextPath()%>/buyGood" method = "post">
            <%
                if(request.getAttribute("watchProduct")!=null){
            %>
            <button class="ui-btn addCartBtn" id="addCartBtn" type="submit"><i class="ui-icon-cart"></i></button>
            <%
                }
            %>
            <input type ="hidden" value="<%=good.getId()%>" name="good_id" id="id"/>

        <div class="selectNum">
            <button class="reduce minus" type="button">&#45;</button>
            <input type="number" value="<%=good.getWholesaleCount()%>"  name="count" id ="count" />
            <button class="add plus" type="button">&#43;</button>
        </div>

        </form>
        <div class="ui-poptips ui-poptips-success addTip">
            <div class="ui-poptips-cnt"><i></i>1件商品成功加入购物车</div>
        </div>

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
            <li class="productIntro">
                <p class="content">${good.getDescribes()}</p>
                <%
                    List<Image> iamge = (List<Image>) request.getAttribute("detailImages");
                    if(iamge!=null){
                    for(Image image :iamge){
                %>
                <img src="<%=image.getAddress()%>" alt="">
                <%
                        }
                    }
                %>
            </li><!-- 商品介绍内容]] -->
            <!-- [[商品评价内容 -->
            <li class="productComment">
                <ul class="ui-list ui-list-pure ui-border-tb">
                    <%
                        List<Comment> comments =(List<Comment>)request.getAttribute("comments");
                        for(Comment comment:comments){
                    %>
                    <li class="ui-border-t">
                        <p><span><%=comment.getUsername()%></span><span class="date" style="float: right;"><%
                            if(comment.getDate()!=null){
                                out.println(format.format(comment.getDate()));
                            }%></span></p>

                        <h4><%=comment.getText()%></h4>
                    </li>
                    <%
                        }
                    %>

                </ul>
            </li><!-- 商品评价内容]] -->
        </ul>
    </div>
    <!-- 商品介绍和评价]]-->
    <!--
        <button class="ui-btn" onclick="location.reload()">刷新</button>-->

</section>

<!-- [[点击查看需要倾销 -->
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

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js?12"></script>
<script>

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

    /* 加入购物车 */
    $('#addCartBtn').tap(function(){
        $('.ui-actionsheet').addClass('show');
    });
    $('#rise_div_close, #addCartSure').tap(function(){
        $('.ui-actionsheet').removeClass('show');
    });
    $('#addCartSure').tap(function(){
        $('.addTip').show();
        setTimeout(function(){
            $('.addTip').hide()
        },2000);
    });

    /* 商品数量选择组件 */
    var max = <%=good.getNum()%>/*parseInt( $('#stock').text() )*/;   /*库存量*/
    var min = <%=good.getWholesaleCount()%>

    $('.minus').tap(function(){
        var $num = $(this).siblings('input');
        var num =  parseFloat( $num.val() );
        if ( num > min ) {          // 修改数量显示
            $num.val( num-1 );
        }

    });

    $('.plus').tap(function(){
        var $num = $(this).siblings('input');
        var num =  parseFloat( $num.val() );
        if( num < max ) {
            $num.val( num+1 );
        }
    });

    $('.selectNum input').keyup(function(){
        if( parseInt( $(this).val() ) > max ) {
            $(this).val(max);
        }
    });

    $('.selectNum input').blur(function(){
        if( parseInt( $(this).val() ) < min||$(this).val()==null ) {
            $(this).val(min);
        }
    });


    /* tab 《商品介绍和评价》 */
    window.addEventListener('load', function(){

        var tab = new fz.Scroll('.bottomBlock', {
            role: 'tab',
            interval: 3000
        });

    });



</script>

</body>
</html>