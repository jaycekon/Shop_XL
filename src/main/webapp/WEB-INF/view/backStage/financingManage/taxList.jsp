<%@ page import="com.Shop.Model.CountOrder" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.Shop.Util.Page" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/5 0005
  Time: 下午 5:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-会费列表</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/components.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">

</head>
<body>

<div class="wrapper">

    <!-- [[头部 -->
    <%@ include file="../header.jsp"%>
    <!-- 头部]] -->

    <!-- [[页面主体 -->
    <div class="mainBody container-fluid">

        <div class="row">

            <!-- [[ 左边导航 -->
            <%@ include file="../left.jsp"%>
            <!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="content col-md-10 logisticsTrace">

                <!-- [[平台佣金提现列表 -->
                <div class="table-responsive">
                    <table class="table table-hover table-bordered tablestriped">
                        <tbody>
                        <tr>
                            <td>图像</td>
                            <td class="textLeft">昵称</td>
                            <td>缴费时间</td>
                            <td class="textRight">缴费金额（元）</td>
                            <td>状态</td>
                        </tr>
                        <%
                            String model = "yyyy-MM-dd HH:mm:ss";
                            SimpleDateFormat format=new SimpleDateFormat(model);
                            Page<CountOrder> pages = (Page<CountOrder>)request.getAttribute("page");
                            List<CountOrder> countOrders =pages.getList();
                            for(CountOrder countOrder:countOrders){
                        %>
                        <tr>
                            <td class="imgTd">
                                <img src="<%=countOrder.getUser().getImg()%>" alt="">
                            </td>
                            <td  class="textLeft"><%=countOrder.getUser().getUsername()%></td>
                            <td><%=format.format(countOrder.getDate())%></td>
                            <td class="textRight"><%=countOrder.getPrices()%></td>
                            <td><%
                                if(countOrder.getStatus()==0){
                                    out.println("未付款");
                                }else{
                                    out.println("已付款");
                                }
                            %></td>
                        </tr>

                        <%
                            }
                        %>
                        </tbody>
                        <input id="recordNum" hidden="hidden" value="<%=pages.getBeginIndex()%>" />
                    </table>
                </div><!-- [[平台佣金提现列表 -->

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