<%@ page import="com.Shop.Model.Profit" %><%--
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
            <div class="ui-form-item ui-form-item-r ui-border-b" id="myInput">
                <input type="number" placeholder="请输入要充值的倾销币数量" name = "count" id="rechargeInput"  oninput="checkRecharge(this)" onkeyup="setRechagrge(this)" />
                <!-- 若按钮不可点击则添加 disabled 类 -->
                <button type="submit" class="ui-border-l disabled"  id="sureRecharge">确定充值</button>
                <a href="#" class="ui-icon-close"></a>
            </div>
        </form>
        <div class="ui-tips ui-tips-info" style="text-align: left;">
            <i></i><span>所需金额<b class="themeColor" id="dumpingMoney"></b></span>
        </div>
    </div>

    <%
        Profit profit =(Profit)request.getAttribute("profit");
    %>
</section>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script>
    var $myInput = {
        inputBox: $('#myInput').find('input'),
        close: $('#myInput').find('.ui-icon-close'),
        btn: $('#myInput').find('button'),
    }
    $myInput.close.addClass('hide');
    var $dumpingMoney = $('#dumpingMoney');
    /* 实时监测文本框输入 */
    function checkRecharge(obj) {

        if( $(obj).val() == '') {
            $myInput.btn.addClass('disabled');
            $dumpingMoney.text("");
            $myInput.close.addClass('hide');
        }else {
            $myInput.btn.removeClass('disabled');
            $myInput.close.removeClass('hide');
            $dumpingMoney.text( ' ￥' +  parseInt( $(obj).val() ) * <%=profit.getCountPrices()%>  + '' );
        }
    }

    /* 限制文本框的输入规则 */
    function setRechagrge(obj) {
        var inputdata = $(obj).val().replace(/\D/g, '');
        if (inputdata != '' && inputdata < 1) {
            inputdata = 1;
        }
        $(obj).val(inputdata);
    }

    /* 删除输入内容 */
    $myInput.close.tap(function(){
        $myInput.inputBox.val("");
        $myInput.btn.addClass('disabled');
        $dumpingMoney.text("");
    });

    //    $myInput.btn.tap(function(){
    //        if( !$(this).hasClass('disabled') ){
    //            $.tips({
    //                content: "充值成功",
    //                stayTime: 2000,
    //                type: "success"
    //            })
    //        }
    //    });

</script>

</body>
</html>