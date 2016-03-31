/* 轮播图《首页和商品详情》 */
window.addEventListener('load', function(){

    /* fz 即 FrozenJS 的意思 */
    var slider = new fz.Scroll('.ui-slider', {
        role: 'slider',
        indicator: true,
        autoplay: true,
        interval: 3000,
        autoplay: true
    });

    /* 滑动开始前 */
    slider.on('beforeScrollStart', function(from, to) {
        // from 为当前页，to 为下一页
    })

    /* 滑动结束 */
    slider.on('scrollEnd', function(cruPage) {
        // curPage 当前页
    });

});

/*点击查看倾销币*/
$('.watchPrice').click(function(event){
    $(".ui-dialog").dialog("show");
    return false;
});


/* tab 《商品介绍和评价》 */
window.addEventListener('load', function(){

    var tab = new fz.Scroll('.bottomBlock', {
        role: 'tab',
        interval: 3000
    });

    /* 滑动开始前 */
    tab.on('beforeScrollStart', function(from, to) {
        // from 为当前页，to 为下一页
    })

    /* 滑动结束 */
    tab.on('scrollEnd', function(curPage) {
        // curPage 当前页
    });

})

/* 加入购物车 */
$('#addCartBtn').tap(function(){
    $('.ui-actionsheet').addClass('show');
})
$('#rise_div_close, #addCartSure').tap(function(){
    $('.ui-actionsheet').removeClass('show');
})


/*商品数量编辑《商品详情页》*/
var $minus = $('#minus');
var $plus = $('#plus');
var $num = $('#num');
$minus.tap(function(){
    num =  parseFloat( $num.val() );
    if ( num >= 2 ) {          // 修改数量显示
        $num.val( num-1 );
    }

});
$plus.tap(function(){
    num =  parseFloat( $num.val() );
    $num.val( num+1 );
});