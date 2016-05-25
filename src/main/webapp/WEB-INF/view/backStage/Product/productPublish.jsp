<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/29 0029
  Time: 下午 3:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-发布商品</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/components.css">

</head>
<body>

<div class="wrapper">
    <!-- [[头部 -->
    <%@ include file="../header.jsp"%><!-- 头部]] -->

    <!-- [[页面主体 -->
    <div class="mainBody container-fluid">

        <div class="row">

            <!-- [[ 左边导航 -->
            <%@ include file="../left.jsp"%>
            <!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="content col-xs-9 publishProduct">
                <!-- [[商品信息-->
                <form class="product-message form-horizontal" action="addGoodDown" method="POST" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="productName" class="col-xs-2 control-label">商品名：</label>
                        <div class="col-xs-10 textShow">
                            <input  type="text" class="form-control" name="name" id="productName"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="retailPrice" class="col-xs-2 control-label">零售价：</label>
                        <div class="col-xs-10 textShow">
                            <div class="input-group">
                                <span class="input-group-addon">&#165;</span>
                                <input  type="text" class="form-control" name="productPrices" id="retailPrice"/>
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="tradePrice" class="col-xs-2 control-label">批发价：</label>
                        <div class="col-xs-10 textShow">
                            <div class="input-group">
                                <span class="input-group-addon">&#165;</span>
                                <input  type="text" class="form-control" name="wholesalePrices" id="tradePrice"/>
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dumpingPrice" class="col-xs-2 control-label">倾销价：</label>
                        <div class="col-xs-10 textShow">
                            <div class="input-group">
                                <span class="input-group-addon">&#165;</span>
                                <input  type="text" class="form-control" name="dumpingPrices" id="dumpingPrice"/>
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="PV" class="col-xs-2 control-label">出厂价：</label>
                        <div class="col-xs-10 textShow">
                            <div class="input-group">
                                <span class="input-group-addon">&#165;</span>
                                <input  type="text" class="form-control"name = "firmPrices" id="PV"/>
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dumpingMoney" class="col-xs-2 control-label">倾销币：</label>
                        <div class="col-xs-10 textShow">
                            <input  type="number" class="form-control" name="wPrices" id="dumpingMoney"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="startBatch" class="col-xs-2 control-label">起批量：</label>
                        <div class="col-xs-10 textShow">
                            <input  type="number" class="form-control" name ="wholesaleCount" id="startBatch"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="stock" class="col-xs-2 control-label">库存：</label>
                        <div class="col-xs-10 textShow">
                            <input  type="number" class="form-control" name = "num" id="stock"/>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label for="remark" class="col-xs-2 control-label">备注：</label>--%>
                        <%--<div class="col-xs-10 textShow">--%>
                            <%--<textarea name="" id="remark" class="form-control" style="resize: none;"></textarea>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group frontPage">
                        <label class="col-xs-2">商品封面(最多5张)：</label>
                        <div class="col-xs-10 imgArea" id="carouselImg">

                            <!-- [[添加图片 -->
                            <div class="col-xs-6 col-sm-2 col-lg-2">
                                <div class="img-content add-content">
                                    <input type="file" accept="image/*" class="fileInp" name="files"/>
                                    <span class="add">&#43;</span>
                                    <img src="" alt="" class="img-responsive" />
                                    <span class="cancel" >&#88;</span>
                                </div>
                            </div><!-- 添加图片]] -->

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="detail" class="col-xs-2 control-label">详情介绍：</label>
                        <div class="col-xs-10">
                            <textarea name="describes" class="form-control" id="detail" rows="10"></textarea>
                        </div>
                    </div>

                    <!-- [[商品附图 -->
                    <div class="form-group">
                        <label class="control-label col-xs-2">
                            商品附图（最多10张）：
                        </label>
                        <div class="col-xs-10 imgArea" id="illustration">

                            <!-- [[添加图片 -->
                            <div class="col-xs-6 col-sm-2 col-lg-2">
                                <div class="img-content add-content">
                                    <input type="file" accept="image/*" class="fileInp" name="detailfiles"/>
                                    <span class="add">&#43;</span>
                                    <img src="" alt="" class="img-responsive" />
                                    <span class="cancel" >&#88;</span>
                                </div>
                            </div><!-- 添加图片]] -->

                        </div>
                    </div><!-- [[商品附图 -->

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
