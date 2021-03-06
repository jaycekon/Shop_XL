<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/1 0001
  Time: 下午 4:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- [[ 左边导航 -->
<ul class="col-md-3 col-lg-2 navigation" aria-expanded="true">
    <li>
        <div class="navItem"><a href="<%=request.getContextPath()%>/backStage"><span class="glyphicon glyphicon-home"></span>首页</a></div>
    </li>
    <li>
        <div class="navItem"><span class="glyphicon glyphicon-user"></span>用户管理</div>
        <ul class="subNav">
            <li><a href="<%=request.getContextPath()%>/listAreas">大区列表</a></li>
            <li><a href="<%=request.getContextPath()%>/listRoles">角色列表</a></li>
            <li><a href="<%=request.getContextPath()%>/listUser">店家列表</a></li>
        </ul>
    </li>
    <li>
        <div class="navItem"><span class="glyphicon glyphicon-tag"></span>商品管理</div>
        <ul class="subNav">
            <li><a href="<%=request.getContextPath()%>/listGood">商品列表</a></li>
            <li><a href="<%=request.getContextPath()%>/addGood">发布商品</a></li>
            <li><a href="<%=request.getContextPath()%>/checkGood">商品查询</a></li>
        </ul>
    </li>
    <li>
        <div class="navItem"><span class="glyphicon glyphicon-file"></span>订单管理</div>
        <ul class="subNav">
            <li><a href="<%=request.getContextPath()%>/listOrder">订单列表</a></li>
            <li><a href="<%=request.getContextPath()%>/orderCheck">订单查询</a></li>
        </ul>
    </li>
    <li>
        <div class="navItem"><span class="glyphicon glyphicon-credit-card"></span>财务管理</div>
        <ul class="subNav">
            <li><a href="<%=request.getContextPath()%>/listMoney">贷款列表</a></li>
            <li><a href="<%=request.getContextPath()%>/listCharge">充值列表</a></li>
            <li><a href="<%=request.getContextPath()%>/listMember">会费列表</a></li>
        </ul>
    </li>
    <li>
        <div class="navItem"><span class="glyphicon glyphicon-open"></span>提现管理</div>
        <ul class="subNav">
            <li><a href="<%=request.getContextPath()%>/listAreaCommission">大区佣金提现</a></li>
            <li><a href="<%=request.getContextPath()%>/listRoleCommission">角色佣金提现</a></li>
        </ul>
    </li>
    <li>
        <div class="navItem"><span class="glyphicon glyphicon-wrench"></span>系统设置</div>
        <ul class="subNav">
            <li><a href="<%=request.getContextPath()%>/indexCarousel">首页轮播</a></li>
            <li><a href="<%=request.getContextPath()%>/updateProfit">参数设置</a></li>
        </ul>
    </li>
</ul> <!-- 左边导航]] -->
