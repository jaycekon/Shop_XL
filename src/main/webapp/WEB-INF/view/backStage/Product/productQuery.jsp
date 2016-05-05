<%@ page import="com.Shop.Model.Good" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/1 0001
  Time: 下午 4:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-商品查询</title>
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
            <%@include file="../left.jsp"%>
             <!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="content col-md-10">

                    <div class="form-group">
                        <form action ="checkGood" method="POST">
                        <div class="col-xs-10 col-md-11">
                            <input type="text" name ="id" class="form-control"  placeholder="请输入要查询的商品编号......（精确查询)">
                        </div>
                        <div class="col-xs-2 col-md-1">
                            <input type="submit" value ="查询"/>
                        </div>
                        </form>
                    </div>



                <!-- [[查询结果 无 -->
                <div class="alert alert-warning alert-dismissible productQueryShow" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>Sorry!</strong> 抱歉，没有查询到相关的商品，请检查输入内容是否正确
                </div>
                <!-- 查询结果 无]] -->

                <%
                    if(request.getAttribute("good") != null){
                        Good good =(Good)request.getAttribute("good");
                %>
                <!-- [[查询结果 有 -->
                <div class="table-responsive">
                    <table class="table table-hover table-bordered tablestriped tableType2">
                        <caption>商品信息</caption>
                        <tbody>
                        <tr>
                            <td>商品编号：</td>
                            <td><%=good.getId()%></td>
                        </tr>
                        <tr>
                            <td>商品名：</td>
                            <td><%=good.getName()%></td>
                        </tr>
                        <tr>
                            <td>商品状态：</td>
                            <td id="productState">
                                <%
                                    switch(good.getStatus()){
                                        case 0:out.println("已下架");
                                                    break;
                                        case 1:out.println("已上架");
                                                    break;
                                    }
                                %>
                            </td>
                        </tr>
                        <tr>
                            <td>销量：</td>
                            <td><%=good.getSaleCount()%></td>
                        </tr>
                        <tr>
                            <td> 操作： </td>
                            <td>
                                <a href="editGood/<%=good.getId()%>" class="table_a">编辑</a>
                                <a href="goodDetail/<%=good.getId()%>" class="table_a">预览</a>
                                <a href = "changeStatus/<%=good.getId()%>" data-toggle="modal" data-target="#undercarriageModal" class="table_a undercarriage">
                                   <%
                                       if (good.getStatus() == 1) {
                                    out.println("下架");
                                    } else {
                                    out.println("上架");
                                    }
                                    %>
                                </a>
                            </td>
                        </tr>
                        </tbody>
                    </table><!-- 查询结果 有]] -->
                    <%
                        }
                    %>
                </div>

            </div><!-- 右边操作页面]] -->
        </div>

    </div><!-- 页面主体]] -->

</div><!-- wrapper]] -->

<!-- [[确认删除模态弹出框 -->
<div class="modal fade bs-example-modal-sm" id="undercarriageModal">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">温馨提示</h4>
            </div>
            <div class="modal-body">
                <p>确认要下架此商品么</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary sureBtn">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 确认删除模态弹出框]] -->

<script src="<%=request.getContextPath()%>/app/backStage/lib/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>
<script>
    /*模态弹出框*/
    var thisA;
    $('.undercarriage').click(function(){
        thisA = $(this);
    })
    $("#undercarriageModal .sureBtn").click(function(){
        $('#undercarriageModal').modal('hide');
        thisA.remove();
        $('#productState').text('已下架')
    })
</script>

</body>
</html>