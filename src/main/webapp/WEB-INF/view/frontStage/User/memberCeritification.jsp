<%@ page import="com.Shop.Model.Profit" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/6 0006
  Time: 下午 3:51
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

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>会员认证</h1>
</header>

<%@include file="../footer.jsp" %>

<section class="ui-container">

    <%
        Profit profit =(Profit) request.getAttribute("profit");
    %>
    <section class="ui-notice noticeClear">
        <i></i>
        <p>您还未认证会员，请交 <span class="themeColor">&#165; <%=profit.getRecordPrices()%></span>认证费用</p>
        <p>会员认证成功将赠送 <span class="themeColor"><%=profit.getDumpingCount()%></span>倾销币</p>
        <div class="ui-notice-btn ui-btn-wrap">
            <a href="<%=request.getContextPath()%>/personSignSuccess" class=" ui-btn-lg productBtn" id="certificationBtn">认证</a>
        </div>
    </section>

</section>

<!-- [[认证 -->
<div class="ui-dialog">
    <div class="ui-dialog-cnt">
        <header class="ui-dialog-hd ui-border-b">
            <h3>认证会员</h3>
            <i class="ui-dialog-close" data-role="button"></i>
        </header>
        <div class="ui-dialog-bd uiDialogBdL">
            <div>认证金额： <span class="themeColor">&#165; 12.22</span></div>
            <ul class="ui-list ui-list-text ui-list-radio ui-border-tb">
                <li class="ui-border-t">
                    <label class="ui-radio" for="wechat">
                        <input type="radio" name="radio" id="wechat">
                    </label>
                    <p>微信支付</p>
                </li>
                <li class="ui-border-t">
                    <label class="ui-radio" for="unionpay">
                        <input type="radio" checked name="radio" id="unionpay">
                    </label>
                    <p>银联支付</p>
                </li>
            </ul>
        </div>
        <div class="ui-dialog-ft">
            <button type="button" data-role="button">取消</button>
            <button type="button" data-role="button">确定</button>
        </div>
    </div>
</div><!-- [[认证 -->

<script class="demo-script">
    $(".ui-dialog").dialog("show");
</script>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script>
    $('#certificationBtn').tap(function(){
        $(".ui-dialog").dialog("show");
    })
</script>

</body>
</html>