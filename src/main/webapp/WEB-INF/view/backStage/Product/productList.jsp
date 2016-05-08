<%@ page import="com.Shop.Model.Good" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Util.Page" %><%--
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/components.css">

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
            <div class="content col-md-10">

                <div class="tab">
                    <a href="<%=request.getContextPath()%>/listGood"><button class="btn">所有商品</button></a>
                    <a href="<%=request.getContextPath()%>/listGoodUp/0"><button class="btn">已上架</button></a>
                    <a href="<%=request.getContextPath()%>/listGoodDown/0"><button class="btn">已下架</button></a>
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
                            Page<Good> pages = (Page<Good>)request.getAttribute("page");
                            List<Good> goods =pages.getList();
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
                                <a href="editGood/<%=good.getId()%>" class="table_a">编辑</a>
                                <a href="goodDetail/<%=good.getId()%>" class="table_a">预览</a>
                                <a href="changeStatus/<%=good.getId()%>" class="table_a"><%
                                    if (good.getStatus() == 1) {
                                        out.println("下架");
                                    } else {
                                        out.println("上架");
                                    }
                                %></a>
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
                <input id="recordNum" hidden="hidden" value="<%=pages.getBeginIndex()%>" />
                <!-- [[分页-->
                <div class="paging">
                    <%
                        if(pages.getBeginIndex()!=0){
                    %>
                    <span class="prev btn btn-primary" id="prevPage" >上一页</span>
                    <%
                        }

                        if((pages.getBeginIndex()+10) < pages.getTotalCount()){
                    %>

                    <span class="next btn btn-primary" id="nextPage">下一页</span>
                    <%
                        }
                    %>
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
                <button type="button" class="btn btn-primary" >确定</button>
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

<script>
var num = parseInt( $('#recordNum').val() );
$("#prevPage").click(function(){
var suffix = num-10;
// 返回url
var url = handleURL(suffix);
// 跳转url
window.location.href = url;
});

$("#nextPage").click(function(){
var suffix = num+10;
// 返回url
var url = handleURL(suffix);
// 跳转url
window.location.href = url;
});

// 处理url的函数
function handleURL(suffix){    // suffix 后缀 为int类型
var pathname = window.location.pathname.split('/');
    if(pathname.length==2){
        pathname = pathname.concat(suffix);
    }else{
        pathname.pop();
        pathname = pathname.concat(suffix);
    }
return url = pathname.join('/');
}
</script>

</body>
</html>
