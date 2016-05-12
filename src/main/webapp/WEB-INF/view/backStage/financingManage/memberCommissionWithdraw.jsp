<%@ page import="com.Shop.Model.WithdrawalsOrder" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.Shop.Util.Page" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/6 0006
  Time: 下午 2:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>一内购-会员佣金提现</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/components.css">

</head>
<body>

<div class="wrapper">

    <!-- [[头部 -->
    <%@ include file="../header.jsp" %>
    <!-- 头部]] -->

    <!-- [[页面主体 -->
    <div class="mainBody container-fluid">

        <div class="row">

            <!-- [[ 左边导航 -->
            <%@ include file="../left.jsp" %>
            <!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="content col-md-10">

                <%
                    String status = (String) request.getAttribute("status");
                    if (status == "1") {
                %>
                <div class="order-type text-left">
                    <button class="btn btn-default" id="btn"
                            onclick="window.location.href='<%=request.getContextPath()%>/listAreaCommission'">所有订单
                    </button>
                    <button class="btn btn-default" id="btn0"
                            onclick="window.location.href='<%=request.getContextPath()%>/listAreaCommissionStatus?status=0'">
                        待审核
                    </button>
                    <button class="btn btn-default" id="btn1"
                            onclick="window.location.href='<%=request.getContextPath()%>/listAreaCommissionStatus?status=1'">
                        通过审核
                    </button>
                    <button class="btn btn-default" id="btn2"
                            onclick="window.location.href='<%=request.getContextPath()%>/listAreaCommissionStatus?status=2'">
                        未通过审核
                    </button>
                </div>
                <%
                } else if (status == "0") {
                %>
                    <div class="order-type text-left">
                    <button class="btn btn-default" id="btn"
                            onclick="window.location.href='<%=request.getContextPath()%>/listRoleCommission'">所有订单
                    </button>
                    <button class="btn btn-default" id="btn0"
                            onclick="window.location.href='<%=request.getContextPath()%>/listRoleCommissionStatus?status=0'">
                        待审核
                    </button>
                    <button class="btn btn-default" id="btn1"
                            onclick="window.location.href='<%=request.getContextPath()%>/listRoleCommissionStatus?status=1'">
                        通过审核
                    </button>
                    <button class="btn btn-default" id="btn2"
                            onclick="window.location.href='<%=request.getContextPath()%>/listRoleCommissionStatus?status=2'">
                        未通过审核
                    </button>
                    </div>
                <%
                    }
                    String model = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat format = new SimpleDateFormat(model);
                    Page<WithdrawalsOrder> pages = (Page<WithdrawalsOrder>)request.getAttribute("page");
                    List<WithdrawalsOrder> withdrawalsOrders = pages.getList();


                %>
                <!-- [[会员佣金提现列表 -->
                <div class="table-responsive">
                    <table class="table table-hover table-bordered tablestriped productQueryShow">
                        <tbody>
                        <tr>
                            <td>提现编号</td>
                            <td>用户昵称</td>
                            <td>金额</td>
                            <td>申请时间</td>
                            <td>审核时间</td>
                            <td>提现状态</td>
                            <td>操作</td>
                        </tr>
                        <%
                            for (WithdrawalsOrder withdrawalsOrder : withdrawalsOrders) {
                        %>
                        <tr>

                            <td><%=withdrawalsOrder.getUuid()%></td>
                            <td><%
                                if (withdrawalsOrder.getRoles() != null) {
                                    out.println(withdrawalsOrder.getRoles().getName());
                                } else if (withdrawalsOrder.getAreas() != null) {
                                    out.println(withdrawalsOrder.getAreas().getName());
                                }
                            %></td>
                            <td><%=withdrawalsOrder.getPrices()%>
                            <td><%=format.format(withdrawalsOrder.getDate())%>
                            </td>
                            <td>
                                <%
                                    if (withdrawalsOrder.getCommitDate() != null) {
                                        out.println(format.format(withdrawalsOrder.getCommitDate()));
                                    }
                                %>
                            </td>
                            <td><%
                                switch (withdrawalsOrder.getStatus()) {
                                    case 0:
                                        out.println("待审核");
                                        break;
                                    case 1:
                                        out.println("通过审核");
                                        break;
                                    case 2:
                                        out.println("未通过审核");
                                        break;
                                }
                            %></td>
                            <td> <%
                                if (withdrawalsOrder.getStatus() == 0) {
                            %>
                                <button type="submit" class="btn btn-primary"
                                        onclick="window.location.href='<%=request.getContextPath()%>/commissionWithdraw/<%=withdrawalsOrder.getId()%>'">
                                    审核通过
                                </button>
                                <button type="button" class="btn btn-danger"
                                        onclick="window.location.href='<%=request.getContextPath()%>/refuseWithdraw/<%=withdrawalsOrder.getId()%>'">
                                    审核不通过
                                </button>

                                <%
                                        }

                                %></td>
                            <%
                                }
                            %>
                        </tr>
                        </tbody>
                    </table>
                </div><!-- [[会员佣金提现列表 -->

                <input id="recordNum" hidden="hidden" value="<%=pages.getBeginIndex()%>" />
                <input id="status" hidden="hidden" value="<%=request.getAttribute("flag")%>" />
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
<script src="<%=request.getContextPath()%>/app/backStage/lib/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>
<script>

    var num = parseInt( $('#recordNum').val() );
    var flag =  parseInt( $('#status').val() );
    $("#prevPage").click(function(){
        var suffix = num-10;
// 返回url
        var url = handleURL(suffix);
// 跳转url
        if(flag ==9){
            window.location.href = url;
        }else{
            url+="?status="+flag;
            window.location.href = url;
        }
        window.location.href = url;
    });

    $("#nextPage").click(function(){
        var suffix = num+10;
// 返回url
        var url = handleURL(suffix);
// 跳转url
        if(flag ==9){
            window.location.href = url;
        }else{
            url+="?status="+flag;
            window.location.href = url;
        }
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

    $(document).ready(function () {
        var judge = window.location.href.split("?")[1];
        switch (judge) {
            case "status=0" :
                $('#btn0').addClass("btn-primary");
                    break;
            case "status=1" :
                $('#btn1').addClass("btn-primary");
                break;
            case "status=2" :
                $('#btn2').addClass("btn-primary");
                    break;
                default:
                        $('#btn').addClass("btn-primary");
        }
    });
</script>
</body>
</html>