<%@ page import="com.Shop.Model.Good" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/29 0029
  Time: 上午 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>一内购-类别管理</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/components.css">

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

            <!-- [[ 左边导航 -->
            <ul class="navigation col-md-2">
                <li>
                    <a href="./../index.html"><div class="navItem"><span class="glyphicon glyphicon-home"></span>首页</div></a>
                </li>
                <li>
                    <div class="navItem"><span class="glyphicon glyphicon-user"></span>用户管理</div>
                    <ul class="subNav">
                        <li><a href="./../userManage/largeArea.html">大区列表</a></li>
                        <li><a href="./../userManage/role.html">角色列表</a></li>
                        <li><a href="./../userManage/shop.html">店家列表</a></li>
                    </ul>
                </li>
                <li>
                    <div class="navItem active"><span class="glyphicon glyphicon-tag"></span>商品管理</div>
                    <ul class="subNav">
                        <li class="active"><a href="./../productManage/productList.html">商品列表</a></li>
                        <li><a href="./../productManage/productVerify.html">商品审核</a></li>
                        <li><a href="./../productManage/productQuery.html">商品查询</a></li>
                    </ul>
                </li>
                <li>
                    <div class="navItem"><span class="glyphicon glyphicon-file"></span>订单管理</div>
                    <ul class="subNav">
                        <li><a href="./../orderManage/orderList.html">订单列表</a></li>
                        <li><a href="./../orderManage/orderCheck.html">订单查询</a></li>
                    </ul>
                </li>
                <li>
                    <div class="navItem"><span class="glyphicon glyphicon-credit-card"></span>财务管理</div>
                    <ul class="subNav">
                        <li><a href="./../financingManage/widthQuery.html">提现查询</a></li>
                        <li><a href="./../financingManage/memberCommissionWithdraw.html">会员佣金提现</a></li>
                        <li><a href="./../financingManage/portLoanWithdraw.html">端口货款提现</a></li>
                        <li><a href="./../financingManage/portCommissionWithdraw.html">端口佣金提现</a></li>
                        <li><a href="./../financingManage/partnerCommissionWithdraw.html">合伙人佣金提现</a></li>
                        <li><a href="./../financingManage/platformCommissionIncome.html">平台佣金收入</a></li>
                    </ul>
                </li>
                <li>
                    <div class="navItem"><span class="glyphicon glyphicon-wrench"></span>系统设置</div>
                    <ul class="subNav">
                        <li><a href="./../systemManage/indexCarousel.html">首页轮播</a></li>
                        <li><a href="./../systemManage/commissionParameter.html">佣金参数</a></li>
                    </ul>
                </li>
            </ul> <!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="content col-md-10">

                <div class="tab">
                    <button class="btn btn-primary">已上架</button>
                    <button class="btn btn-primary">已下架</button>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品编号</th>
                            <th>商品编号</th>
                            <th>商品状态</th>
                            <th>相关操作</th>
                            <th>销量</th>
                            <th>库存</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<Good> goods =(List<Good>)request.getAttribute("goods");
                            for(Good good:goods){
                        %>
                        <tr>
                            <td><%=good.getId()%></td>
                            <td><%=good.getName()%></td>
                            <td><%
                                if(good.getStatus() ==1){out.println("已上架");}
                                else{ out.println("已下架");}
                            %></td>
                            <td>
                                <a href="./editProduct.html" class="table_a">编辑</a>
                                <a href="" class="table_a">预览</a>
                                <a href="" class="table_a">下架</a>
                            </td>
                            <td><%=good.getSaleCount()%></td>
                            <td><%=good.getNum()%></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>

                <!-- [[分页-->
                <div class="paging">
                    <span class="prev btn btn-primary">上一页</span>
                    <span class="next btn btn-primary">下一页</span>
                </div>
                <!-- 分页]]-->

            </div><!-- 右边操作页面]] -->
        </div>

    </div><!-- 页面主体]] -->

</div><!-- wrapper]] -->

<!-- [[编辑弹出框 -->
<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <form class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="exit"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加分类</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="name" class="control-label">类别名称</label>
                    <input type="text" class="form-control" id="name">
                </div>
                <div class="form-group">
                    <label for="imgFile" class="control-label">类别图片</label>
                    <div class="imgBox">&#43;
                        <input type="file" accept="image/*" id="imgFile">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="cancel">取消</button>
                <button type="submit" class="btn btn-primary" id="sure">确定</button>
            </div>
        </form><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 编辑弹出框]] -->


<!-- [[确认删除模态弹出框 -->
<div class="modal fade bs-example-modal-sm" id="delModal">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">温馨提示</h4>
            </div>
            <div class="modal-body">
                <p>确认删除此类别么</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 确认删除模态弹出框]] -->

<!-- [[提示模态弹出框 -->
<div class="modal fade bs-example-modal-sm" id="tipModal">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">温馨提示</h4>
            </div>
            <div class="modal-body">
                <p></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="tipModalSure">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 提示模态弹出框]] -->

<script src="<%=request.getContextPath()%>/app/backStage/lib/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>

</body>
</html>
