<%@ page import="com.Shop.Model.Good" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/29 0029
  Time: 下午 4:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-商品详情</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/components.css">

</head>
<body>

<div class="wrapper">
    <!-- [[头部 -->
    <header class="header container-fluid">
        <div class="row">
            <div class="col-xs-3">
                <img src="<%=request.getContextPath()%>/app/backStage/image/bg1.jpg" alt="一内购" class="logo img-responsive">
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

            <%@ include file="../left.jsp"%>

            <%
                Good good =(Good)request.getAttribute("good");
            %>
            <!-- [[右边操作页面 -->
            <div class="content col-xs-9 publishProduct">
                <!-- [[商品信息-->
                <form class="product-message form-horizontal" action = "<%=good.getId()%>" method = POST enctype="multipart/form-data">


                    <div class="form-group">
                        <label class="col-xs-2 control-label" for="number" >商品编号：</label>
                        <div class="col-xs-10 textShow">
                            <input disabled="disabled" type="text" value="这些是显示商品编号的" class="form-control" id="number"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productName" class="col-xs-2 control-label">商品名：</label>
                        <div class="col-xs-10 textShow">
                            <input  type="text" class="form-control" value="<%=good.getName()%>" name="name" id="productName"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="retailPrice" class="col-xs-2 control-label">零售价：</label>
                        <div class="col-xs-10 textShow">
                            <div class="input-group">
                                <span class="input-group-addon">&#165;</span>
                                <input  type="text" class="form-control" value="<%=good.getProductPrices()%>" name="productPrices" id="retailPrice"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="tradePrice" class="col-xs-2 control-label">批发价：</label>
                        <div class="col-xs-10 textShow">
                            <div class="input-group">
                                <span class="input-group-addon">&#165;</span>
                                <input  type="text" class="form-control" value="<%=good.getWholesalePrices()%>"name="wholesalePrices" id="tradePrice"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dumpingPrice" class="col-xs-2 control-label">倾销价：</label>
                        <div class="col-xs-10 textShow">
                            <div class="input-group">
                                <span class="input-group-addon">&#165;</span>
                                <input  type="text" class="form-control" value="<%=good.getDumpingPrices()%>" name="dumpingPrices"id="dumpingPrice"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dumpingMoney" class="col-xs-2 control-label">倾销币：</label>
                        <div class="col-xs-10 textShow">
                            <input  type="number" class="form-control" value="<%=good.getwPrices()%>" name="wPrices" id="dumpingMoney"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="PV" class="col-xs-2 control-label">PV值：</label>
                        <div class="col-xs-10 textShow">
                            <div class="input-group">
                                <span class="input-group-addon">&#165;</span>
                                <input  type="number" class="form-control" value="<%=good.getPv()%>" name ="pv" id="PV"/>
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="startBatch" class="col-xs-2 control-label">起批量：</label>
                        <div class="col-xs-10 textShow">
                            <input  type="number" class="form-control" value="<%=good.getWholesaleCount()%>" name="wholesaleCount" id="startBatch"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="stock" class="col-xs-2 control-label">库存：</label>
                        <div class="col-xs-10 textShow">
                            <input  type="number" class="form-control" value="<%=good.getNum()%>" name="num" id="stock"/>
                        </div>
                    </div>
                    <div class="form-group frontPage">
                        <label class="col-xs-2">商品封面(最多5张)：</label>
                        <div class="col-xs-10 img-content">
                            <div class="col-xs-2 add">
                                <span class="addIcon">+</span>
                                <span class="cancel">X</span>
                                <input  type="file" accept="image/*" name="files" class="file"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="detail" class="col-xs-2 control-label">详情介绍：</label>
                        <div class="col-xs-10">
                            <textarea name="describes" class="form-control" value ="<%=good.getDescribes()%>" id="detail" rows="10"></textarea>
                        </div>
                    </div>
                    <div class="from-group">
                        <div class="col-xs-offset-2 col-xs-10">
                            <button class="btn btn-primary"  type="submit">保存</button>
                        </div>
                    </div>
                </form>
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

</body>
</html>