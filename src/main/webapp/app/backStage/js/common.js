// 侧边栏折叠
$navItem = $('.navItem');
$subNavItem = $('.subNav li')
$navItem.click(function(){
    $navItem.removeClass('active');
    $subNavItem.removeClass('active');
    $(this).addClass('active');
    /*  $(this).parent().siblings().find('.subNav').slideUp('fast');*/
    $(this).siblings('.subNav').slideToggle('fast');
});

$subNavItem.click(function(){
    $subNavItem.removeClass('active');
    $(this).addClass('active');
    $navItem.removeClass('active');
    $(this).parent().siblings('.navItem').addClass('active');
})

// 上传图片
$(document).on("change",".imgBox input[type='file']",function(){
    // 读取数据过程
    readFile(this);
});

// 读取文件属性
function readFile(obj) {
    var file = obj.files[0];
    if(!/image\/\w+/.test(file.type)) {
        alert('文件必须为图片');
        return false;
    }

    var reader = new FileReader();
    reader.readAsDataURL(file);
    var $image_box = $(obj).parent();
    var $load;
    reader.onloadstart = function() {
        // 数据上传中
    }
    reader.onload = function() {
        // 数据上传完毕
        var $image_box = $(obj).parent();
        // 预览照片
        previewImage($(obj),$image_box);
    }
}
// 照片预览功能
function previewImage(fileObj,$image_box) {
    var objUrl = getObjectURL(fileObj[0].files[0]);
    // 图片局部显示
    var $image = $("<img class='image'>");
    $image.attr('src',objUrl);
    var h=w=$('.imgBox').css('width');

    // 如果图片被缓存，直接返回缓存数据
    if($image[0].complete) {
        realImage($image,w,h);
    }else {
        // 否则图片完全加载完之后
        $image[0].onload = function(){
            realImage($image,w,h);
        }
    }

    $image.appendTo($image_box);
}

// 图片裁剪函数
function realImage($image,w,h) {
    var imageWidth = $image[0].width;
    var imageHeight = $image[0].height;
    if( imageHeight>imageWidth ) {
        $image.css('width',w);
        var realHeight=parseFloat(imageHeight)*parseFloat(w)/parseFloat(imageWidth);
        var clipless=(parseFloat(realHeight)-parseFloat(h))/2;
        var clipmore=parseFloat(clipless)+parseFloat(h);
        $image.css('height',realHeight+'px');
        $image.css('clip',"rect(" + clipless +"px "+parseFloat(w)+"px "+clipmore+"px 0px)");
        $image.css("top",-(parseFloat(clipless))+"px");
        $image.css("left","0px");
    }else {
        $image.css("height",h);
        var realWidth=parseFloat(imageWidth)*parseFloat(h)/parseFloat(imageHeight);
        var clipless=(parseFloat(realWidth)-parseFloat(w))/2;
        var clipmore=parseFloat(clipless)+parseFloat(w);
        $image.css("width",realWidth+"px");
        $image.css("clip","rect(0px "+clipmore+"px "+parseFloat(h)+"px "+clipless+"px)");
        $image.css("top","0px");
        $image.css("left",-(parseFloat(clipless))+"px");
    }
    $image.css('display','block');
}


//建立一個可存取到該file的url
function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

/* 菜单的显示 */
$('.navbar-toggle' ).click(function(){
    $('.navigation').slideToggle();
});