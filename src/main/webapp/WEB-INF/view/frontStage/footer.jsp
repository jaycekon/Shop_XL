<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">
        <li class="footerItem" onclick="window.location.href='<%=request.getContextPath()%>/'"><i class="ui-icon-home"></i><div>首页</div></li>
        <li class="footerItem"><i class="ui-icon-cart" onclick="window.location.href='<%=request.getContextPath()%>/myCart'"></i><div>购物车</div></li>
        <li class="footerItem"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/personCenter'"></i><div>个人中心</div></li>
    </ul>
</footer>