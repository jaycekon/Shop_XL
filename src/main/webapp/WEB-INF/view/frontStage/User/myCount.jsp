<%@ page import="com.Shop.Model.User" %>
<%@ page import="com.Shop.Model.CountOrder" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.Shop.Util.Page" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/6 0006
  Time: 下午 5:22
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/index.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/product.css">
    <style>
        #toRechargeBtn {
            margin: 32px 15px 0 0;
        }
    </style>

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>我的倾销币</h1>
</header>

<%@include file="../footer.jsp"%>

<section class="ui-container">

    <%
        User user =(User)session.getAttribute("loginUser");
        List<CountOrder> countOrders = (List<CountOrder>)request.getAttribute("countOrders");
        String model = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format=new SimpleDateFormat(model);
        Page p = (Page) request.getAttribute("page");
    %>
    <!-- [[统计 -->
    <ul class="ui-list ui-border-tb commissionTop">
        <li class="ui-border-t">
            <div class="ui-avatar">
                <img src="<%=user.getImg()%>" alt="">
            </div>
            <div class="ui-list-info">
                <h4 class="ui-nowrap">可使用：<span class="themeColor"><%=user.getCount()%></span></h4>
                <h4 class="ui-nowrap">已使用：<span class="themeColor"><%=user.getUsecount()%></span></h4>
            </div>
            <div>
                <a href="<%=request.getContextPath()%>/recharge" class="ui-btn productBtn" id="toRechargeBtn">去充值</a>
            </div>
        </li>
    </ul> <!-- 统计]] -->

    <!-- [[倾销币使用记录 -->
    <table class="table ui-table ui-border ui-border-tb">
        <thead>
        <tr>
            <th>时间</th>
            <th>类别</th>
            <th>数量</th>
            <th>操作</th>
        </tr>
        </thead>
        <%
            for(CountOrder countOrder:countOrders){
        %>
        <tbody>
        <tr>
            <td><%=format.format(countOrder.getDate())%></td>
            <td><%=countOrder.getType()%></td>
            <td><%=countOrder.getCount()%></td>
            <td><%
                switch(countOrder.getStatus()){
                    case 0: out.println("未付款");
                                break;
                    case 1:    out.println("已完成");
                                break;
                }
            %></td>

        </tr>

        </tbody>
        <%
            }
        %>
    </table><!-- 倾销币使用记录]] -->

    <div class="paging">
        <%
            if(p.getBeginIndex()!=0){
        %>
        <button class="ui-btn" onclick="window.location.href='<%=request.getContextPath()%>/myCount/<%=p.getBeginIndex()-10%>'">上一頁</button>
        <%
            }
            if(p.getBeginIndex()+10<p.getTotalCount()){
        %>
        <button class="ui-btn" onclick="window.location.href='<%=request.getContextPath()%>/myCount/<%=p.getBeginIndex()+10%>'">下一頁</button>
        <%
            }
        %>
    </div>

</section>

<%--<!-- [[充值-->--%>
<%--<div class="ui-dialog">--%>
    <%--<div class="ui-dialog-cnt">--%>
        <%--<header class="ui-dialog-hd ui-border-b">--%>
            <%--<h3>确定付款</h3>--%>
            <%--<i class="ui-dialog-close" data-role="button"></i>--%>
        <%--</header>--%>
        <%--<div class="ui-dialog-bd uiDialogBdL">--%>
            <%--<div>--%>
                <%--<p>倾销币数量：<span class="themeColor">123</span></p>--%>
                <%--<p>充值金额：<span class="themeColor">&#165;12.22</span></p>--%>
                <%--<ul class="ui-list ui-list-text ui-list-radio">--%>
                    <%--<li class="ui-border-t">--%>
                        <%--<label class="ui-radio" for="radio">--%>
                            <%--<input type="radio" name="radio">--%>
                        <%--</label>--%>
                        <%--<p>微信支付</p>--%>
                    <%--</li>--%>
                    <%--<li class="ui-border-t">--%>
                        <%--<label class="ui-radio" for="radio">--%>
                            <%--<input type="radio" checked name="radio">--%>
                        <%--</label>--%>
                        <%--<p>银联支付</p>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="ui-dialog-ft">--%>
            <%--<button type="button" data-role="button">取消</button>--%>
            <%--<button type="button" data-role="button">付款</button>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div><!-- 充值]] -->--%>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script class="demo-script">
    $(".ui-dialog").dialog("show");
</script>

</body>
</html>