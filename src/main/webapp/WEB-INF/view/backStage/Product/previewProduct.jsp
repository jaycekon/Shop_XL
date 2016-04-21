<%@ page import="com.Shop.Model.Good" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.Image" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/29 0029
  Time: 下午 4:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>一内购后台管理系统</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/components.css">

</head>
<body>

<div class="wrapper">
    <!-- [[头部 -->
    <header class="header">
        <img src="" alt="一内购" class="logo">
        <button class="btnExit" type="button"><span class="glyphicon glyphicon-off"></span>退出</button>
    </header>
    <!-- 头部]] -->

    <!-- [[页面主体 -->
    <div class="mainBody container-fluid">

        <div class="row">

            <!-- [[ 左边导航 -->
            <%@ include file="../left.jsp"%>
            <!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="content col-md-9 productDetail">

                <!-- [[商品信息-->
                <div class="table-responsive">
                    <table class="table table-hover table-bordered tablestriped tableType2">
                        <caption>商品信息</caption>
                        <%
                            Good good =(Good)request.getAttribute("good");
                        %>
                        <tbody>
                        <tr>
                            <td>商品编号：</td>
                            <td><%=good.getId()%></td>
                            <td>商品名：</td>
                            <td><%=good.getName()%></td>
                        </tr>
                        <tr>
                            <td>零售价：</td>
                            <td><%=good.getProductPrices()%>元</td>
                            <td>批发价：</td>
                            <td><%=good.getWholesalePrices()%>元</td>
                        </tr>
                        <tr>
                            <td>倾销价：</td>
                            <td><%=good.getDumpingPrices()%>元</td>
                            <td>倾销币：</td>
                            <td><%=good.getwPrices()%></td>
                        </tr>
                        <tr>
                            <td>库存：</td>
                            <td><%=good.getNum()%></td>
                            <td>起批量：</td>
                            <td><%=good.getWholesaleCount()%></td>
                        </tr>
                        <tr>
                            <td>运费：</td>
                            <td>0</td>
                            <td>销量：</td>
                            <td><%=good.getSaleCount()%></td>
                        </tr>
                        <tr>
                            <td>备注：</td>
                            <td colspan="3">这里是备注信息这里是备注信息这里是备注信息这里是备注信息</td>
                        </tr>
                        <tr>
                            <td>商品图片：</td>
                            <td colspan="3">
                                <%
                                    List<Image> images =(List)request.getAttribute("images");
                                    if(images !=null){

                                    for(Image image:images){
                                %>
                                <img src="<%=image.getAddress()%>" alt="">
                                <%
                                        }
                                    }
                                %>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <!-- [[商品介绍-评价-->
                <div class="introduce-evaluate text-left">
                    <div class="tab clearfix">
                        <span class="active">商品介绍</span>
                        <span class="">商品评价</span>
                    </div>
                    <div class="introduce-content">
                        <div class="introduce-msg">呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒呵呵哒</div>
                        <img src="<%=request.getContextPath()%>/app/backStage/image/carousel2.jpg" alt=""/>
                    </div>
                    <div class="evalute-content text-center" style="display: none;">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <td>用户昵称</td>
                                <td>产品规格</td>
                                <td>评价</td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>魔鬼的翅膀</td>
                                <td>规格1</td>
                                <td>真心不错啊</td>
                            </tr>
                            <tr>
                                <td>我是撒旦</td>
                                <td>规格2</td>
                                <td>真心黑啊</td>
                            </tr>
                            <tr>
                                <td>我是宙斯</td>
                                <td>规格3</td>
                                <td>骗你的啦</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- 商品介绍-评价]]-->
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
<script src="<%=request.getContextPath()%>/app/backStage/js/product.js"></script>
<script>
    var dom = {
        $span : $('.tab').find('span'),
        $introduce : $('.introduce-content'),
        $evalute : $('.evalute-content')
    };

    //*产品详情tab*//
    dom.$span.on('click',function(){
        $(this).siblings().removeClass('active');
        $(this).addClass('active');

        var index = $(this).index();

        if(index == 0){
            dom.$introduce.show();
            dom.$evalute.hide();
        }else{
            dom.$introduce.hide();
            dom.$evalute.show();
        }
    });

    /* 备注tooltip */
    $('#myRemark').bind('click',function(){
        $(this).siblings('.tooltip').toggle();
    });
    $('#myRemark').bind('mouseover',function(){
        $(this).siblings('.tooltip').fadeIn();
    });
    $('#myRemark').bind('mouseout',function(){
        $(this).siblings('.tooltip').fadeOut();
    });
</script>
</body>
</html>