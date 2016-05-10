<%@ page import="com.Shop.Model.Roles" %>
<%@ page import="com.Shop.Model.Areas" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/19 0019
  Time: 下午 2:25
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/withdraw.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/font/iconfont.css">

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <h1>角色提现</h1>
    <button class="ui-btn" onclick="window.location.href='<%=request.getContextPath()%>/withdrawDetail'">提现明细</button>
</header>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">
        <%
            if(session.getAttribute("roles")!=null){
        %>
        <li class="footerItem active"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/roleCenter'"></i><div>角色中心</div></li>
        <%
            }
            else if(session.getAttribute("areas")!=null){
        %>
        <li class="footerItem active"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/areaCenter'"></i><div>大区中心</div></li>
        <%
            }
        %>
    </ul>
</footer>

<section class="ui-container">
    <%
        if(session.getAttribute("roles")!=null){
            Roles roles = (Roles)session.getAttribute("roles");
    %>
    <i class="icon iconfont withdrawIcon">&#xe604;</i>
    <p>总佣金：&#165; <%=roles.getTotalCommission()%></p>
    <p>可支配佣金：&#165; <%=roles.getExitCommission()%></p>
    <p>提现金额不能低于50元</p>
    <%
        }else if(session.getAttribute("areas")!=null){
            Areas areas = (Areas) session.getAttribute("areas");
            %>
    <i class="icon iconfont withdrawIcon">&#xe604;</i>
    <p>总佣金：&#165; <%=areas.getTotalCommission()%></p>
    <p>可支配佣金：&#165; <%=areas.getExitCommission()%></p>
    <p>提现金额不能低于50元</p>
    <%
        }
    %>
 <form action ="<%=request.getContextPath()%>/witdraw" method="post">
    <section class="inputWithdraw" >
        <div class="ui-input ui-border-radius">
            <input type="text" name="cout" value="" placeholder="输入提现金额">
        </div>
        <div class="ui-btn-wrap">
            <button class="ui-btn-lg" type="submit">提现</button>
        </div>
    </section>
</form>

</section>


<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>

</body>
</html>