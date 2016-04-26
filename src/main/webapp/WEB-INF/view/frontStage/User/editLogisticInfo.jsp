<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/25 0025
  Time: 下午 5:33
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/address.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/font/iconfont.css">

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <h1>填写物流信息</h1>
</header>

<%@include file="../footer.jsp"%>

<section class="ui-container">

    <div class="ui-form ui-border-t">
        <form action="<%=request.getContextPath()%>/sendOrderProduct/<%=request.getAttribute("id")%>" method="post">
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="logisticCompany">快递公司</label>
                <input type="text" value="" placeholder="填写快递公司" name ="logisticCompany" id="logisticCompany">
            </div>
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="logisticCode">物流编号</label>
                <input type="text" value="" placeholder="填写物流编号" name ="logisticCode" id="logisticCode">
            </div>


            <div class="ui-btn-wrap">
                <button class="ui-btn-lg productBtn" type="submit">
                    保存
                </button>
                <button class="ui-btn-lg">
                    取消
                </button>
            </div>
        </form>
    </div>





</section>


<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>

</body>
</html>
