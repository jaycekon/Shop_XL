/**
 * Created by Administrator on 2016/3/22.
 */
$(function(){
    //**添加商品封面图**//

    /*图片显示*/
    var setImagePreview = function(file){
        var docObj = file;

        if (docObj.files && docObj.files[0]) {
            //火狐下，直接设img属性
            if (window.navigator.userAgent.indexOf("Chrome") >= 1 || window.navigator.userAgent.indexOf("Safari") >= 1) {
                return window.URL.createObjectURL(docObj.files[0]);
            }
            else {
                return window.URL.createObjectURL(docObj.files[0]);
            }
        }
    };


    var dom = {
        $form : $('.product-message')
    };

    dom.$form.on('click','.file',function(){

        $(this).on('change',function(){

            var imgcontent = $(this).parents('.img-content'),
                parent = $(this).parent();

            $(this).hide();
            $(this).siblings().hide();
            $(this).siblings('.cancel').show();
            parent.removeClass('add');

            var src = setImagePreview($(this)[0]);
            var img = $('<img>');
            img[0].src = src;

            parent.append(img);

            if(imgcontent.children().length == 5){
                return;
            }else{
                imgcontent.append(
                    '<div class="col-xs-2 add">'+
                    '<span class="addIcon">+</span>'+
                    '<span class="cancel">X</span>'+
                    '<input type="file" accept="image/*" name="" class="file"/>'+
                    '</div>')
            }

            /*用ajax把数据上传到后台*/
            var formData = new FormData()
            // fileInput
            formData.append('imgfile',this.files[0]);
            
            $.ajax({
                url: './../../uploadImg',
                type: 'POST',
                data: formData,
                processData: false,   // 告诉jquery不要去处理发送的数据
                contentType: false,   // 告诉jquery不要去设置Content-Type请求头
            });

        });


    });

    dom.$form.on('click','.cancel',function(){

        var imgcontent = $(this).parents('.img-content');

        $(this).parents('.col-xs-2').remove();

        if(imgcontent.children().length < 5 && imgcontent.find('.add').length > 0){
            return;
        }else{
            imgcontent.append(
                '<div class="col-xs-2 add">'+
                '<span class="addIcon">+</span>'+
                '<span class="cancel">X</span>'+
                '<input type="file" accept="image/*" name="" class="file"/>'+
                '</div>')
        }
    });

    $('#addspecbtn').on('click',function(){
        $(this).parents('.form-group').after(addSpec());
    });

    dom.$form.on('click','.glyphicconDel',function(){
        $(this).parents('.specific').remove();
    });


});