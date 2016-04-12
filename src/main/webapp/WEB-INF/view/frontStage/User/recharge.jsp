<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/6 0006
  Time: 下午 5:52
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

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>倾销币充值</h1>
</header>

<section class="ui-container">

    <div class="ui-form ui-border-tb">
        <form action="<%=request.getContextPath()%>/recharge" method="post">
            <div class="ui-form-item ui-form-item-r ui-border-b">
                <input type="text" placeholder="请输入要充值的倾销币数量" name ="count">
                <!-- 若按钮不可点击则添加 disabled 类 -->
                <button type="submit" class="ui-border-l">确定充值</button>
            </div>
        </form>
        <%--<div class="ui-tips ui-tips-info" style="text-align: left;">--%>
            <%--<i></i><span>所需金额<b class="themeColor"> xx </b>元</span>--%>
        <%--</div>--%>
    </div>


</section>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>

</body>
</html>
