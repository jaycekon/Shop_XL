
var thisProduct;
/* 商品删除 */
$('.productDel').tap(function(){
    var $targetDel = $(this).parents('.productBox');
    /* 弹窗 */
    var dia=$.dialog({
        content:'确定删除该商品么',
        button:["确认","取消"]
    });

    dia.on("dialog:action",function(e){
        /*确定删除*/
        if(e.index == 0) {
            $targetDel.remove();
        }
        if( $('.productBox').size() == 0 ) {
            $('#noProductWrapper').removeClass('hide');
            $('#hasProductWrapper').empty();
        }
    });

});

$('.minus').tap(function(){
    $thisProduct = $(this).parents('.productBox');
    var $num = $(this).siblings('input');         //  当前选择数量
    var num =  parseFloat( $num.val() ).toFixed(2);
    if ( num >= 2 ) {          // 修改数量显示
        $num.val( num-1 );
        editPrice($thisProduct,'reduce');
    }
});

/* 商品数量编辑 */
$('.plus').tap(function(){

    $thisProduct = $(this).parents('.productBox');

    /*数量的改变*/
    var $num = $thisProduct.find('input');              // 当前选择数量
    var max = parseInt( $num.attr('data-max') );    // 最大库存
    var num =  parseInt ( $num.val() );
    if( num < max ) {
        $num.val( num+1 );
        editPrice($thisProduct,'add');
    }else {
        /*达到最大库存量信息提示*/
        $.tips({
            content:'已达到最大库存量',
            stayTime:2000,
            type:"success"
        })
    }
});

$('.selectNum input').tap(function() {

    $thisProduct = $(this).parents('.productBox');
    var $num = $(this);         //  当前选择数量
    var max = parseInt( $num.attr('data-max') );    // 最大库存

    /*弹框编辑数量*/
    var dia=$.dialog({
        title:'输入商品数量',
        content: "<p>库存量" + max + "</p>" +
        "<input type='text' id='myNum' class='ui-input' />",
        button:["取消","确认"]
    });

    var $myNum = $('#myNum');
    $myNum.focus();
    $myNum.val( $num.val() );

    dia.on("dialog:action",function(e){
        if(e.index == 1 ) {
            var num = $myNum.val();
            if( $myNum.val() == '' ) {
                num = 1;
            }
            $num.val( num );
        }
        editPrice($thisProduct,'edit');
    });

    /* 弹出框的商品数量填写限制 */
    $('#myNum').keyup(function () {

        var $num = $thisProduct.find('input');              // 当前选择数量
        var max = parseInt( $num.attr('data-max') );    // 最大库存

        var inputdata = $(this).val().replace(/\D/g, '');
        if (inputdata != '' && inputdata < 1) {
            inputdata = 1;
        }
        if (inputdata != '' && inputdata > max) {
            inputdata = max;
        }
        $(this).val(inputdata);
    });


    /*      var max = parseInt( $(this).attr('data-max'));
     if( parseInt( $(this).val() ) > max ) {
     $(this).val(max);
     }
     if( parseInt( $(this).val() ) < 1 ) {
     $(this).val(1);
     }*/

});

/*价格改变*/
function editPrice($thisProduct,type) {

    var $num = $thisProduct.find('input');                       // 数量
    var $unitPrice = $thisProduct.attr('data-unitPrice') ;      // 单价
    var $price =  $thisProduct.find('.price');                  // 总价

    var unitPrice = parseFloat($unitPrice);
    var price;
    if(type == 'add') {
        price = ( parseFloat( $price.text().substr(1)) + unitPrice ).toFixed(2);     // 商品的价格
    }else if(type == 'reduce'){
        price = ( parseFloat( $price.text().substr(1) ) - unitPrice ).toFixed(2);     // 商品的价格
    }else {
        price =  ( unitPrice * parseFloat( $num.val() ) ).toFixed(2);     // 商品的价格
    }

    $price.html('&#165; ' + price);
    acountPrice();
}

/*总价的计算*/
function acountPrice() {
    var totalPrice = 0;
    $('.productBox .price').each(function(){
        var price = parseFloat( $(this).text().substr(1));
        totalPrice = totalPrice + price;
    });
    $('#amount').html('&#165; ' + totalPrice);
}