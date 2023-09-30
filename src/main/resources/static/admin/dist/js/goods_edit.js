var editorD;

$(function () {

    
    const E = window.wangEditor;
    editorD = new E('#wangEditor')
    
    editorD.config.height = 750
    editorD.config.placeholder = 'Nhập mô tả sản phẩm'
    
    editorD.config.uploadImgServer = '/admin/upload/files'
    editorD.config.uploadFileName = 'files'
    
    editorD.config.uploadImgMaxSize = 2 * 1024 * 1024
    
    editorD.config.uploadImgMaxLength = 5
    
    editorD.config.showLinkImg = false
    editorD.config.uploadImgHooks = {
        
        success: function (xhr) {
            console.log('success', xhr)
        },
        
        fail: function (xhr, editor, resData) {
            console.log('fail', resData)
        },
        
        error: function (xhr, editor, resData) {
            console.log('error', xhr, resData)
        },
        
        timeout: function (xhr) {
            console.log('timeout')
        },
        customInsert: function (insertImgFn, result) {
            if (result != null && result.resultCode == 200) {
                
                result.data.forEach(img => {
                    insertImgFn(img)
                });
            } else {
                alert("error");
            }
        }
    }
    editorD.create();

    
    new AjaxUpload('#uploadGoodsCoverImg', {
        action: '/admin/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('Chỉ hỗ trợ các tệp định dạng jpg, png, gif!');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $("#goodsCoverImg").attr("src", r.data);
                $("#goodsCoverImg").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});

$('#saveButton').click(function () {
    var goodsId = $('#goodsId').val();
    var goodsCategoryId = $('#levelTwo option:selected').val();
    var goodsName = $('#goodsName').val();
    var tag = $('#tag').val();
    var originalPrice = $('#originalPrice').val();
    var sellingPrice = $('#sellingPrice').val();
    var goodsIntro = $('#goodsIntro').val();
    var stockNum = $('#stockNum').val();
    var goodsSellStatus = $("input[name='goodsSellStatus']:checked").val();
    var goodsDetailContent = editorD.txt.html();
    var goodsCoverImg = $('#goodsCoverImg')[0].src;
    if (isNull(goodsCategoryId)) {
        swal("Vui lòng bổ sung loại sản phẩm", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsName)) {
        swal("Nhập tên sản phẩm", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsName, 100)) {
        swal("Vui lòng nhập tên sản phẩm", {
            icon: "error",
        });
        return;
    }
    if (isNull(tag)) {
        swal("Vui lòng nhập thẻ sản phẩm", {
            icon: "error",
        });
        return;
    }
    if (!validLength(tag, 100)) {
        swal("Nhãn quá dài", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsIntro)) {
        swal("Vui lòng nhập mô tả sản phẩm", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsIntro, 100)) {
        swal("Mô tả sản phẩm quá 100 kí tự", {
            icon: "error",
        });
        return;
    }
    if (isNull(originalPrice) || originalPrice < 1) {
        swal("Vui lòng nhập giá sản phẩm", {
            icon: "error",
        });
        return;
    }
    if (isNull(sellingPrice) || sellingPrice < 1) {
        swal("Vui lòng nhập giá bán của sản phẩm", {
            icon: "error",
        });
        return;
    }
    if (isNull(stockNum) || sellingPrice < 0) {
        swal("Vui lòng nhập kho sản phẩm", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsSellStatus)) {
        swal("Vui lòng chọn trạng thái kệ", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsDetailContent)) {
        swal("Vui lòng nhập mô tả sản phẩm", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsDetailContent, 50000)) {
        swal("Mô tả sản phẩm quá dài", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsCoverImg) || goodsCoverImg.indexOf('img-upload') != -1) {
        swal("Ảnh bìa không được để trống", {
            icon: "error",
        });
        return;
    }
    var url = '/admin/goods/save';
    var swlMessage = 'Lưu thành công';
    var data = {
        "goodsName": goodsName,
        "goodsIntro": goodsIntro,
        "goodsCategoryId": goodsCategoryId,
        "tag": tag,
        "originalPrice": originalPrice,
        "sellingPrice": sellingPrice,
        "stockNum": stockNum,
        "goodsDetailContent": goodsDetailContent,
        "goodsCoverImg": goodsCoverImg,
        "goodsCarousel": goodsCoverImg,
        "goodsSellStatus": goodsSellStatus
    };
    if (goodsId > 0) {
        url = '/admin/goods/update';
        swlMessage = 'Sửa thành công';
        data = {
            "goodsId": goodsId,
            "goodsName": goodsName,
            "goodsIntro": goodsIntro,
            "goodsCategoryId": goodsCategoryId,
            "tag": tag,
            "originalPrice": originalPrice,
            "sellingPrice": sellingPrice,
            "stockNum": stockNum,
            "goodsDetailContent": goodsDetailContent,
            "goodsCoverImg": goodsCoverImg,
            "goodsCarousel": goodsCoverImg,
            "goodsSellStatus": goodsSellStatus
        };
    }
    console.log(data);
    $.ajax({
        type: 'POST',
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
            	swal("Lưu thành công", {
                    icon: "success",
                }).then(function () {
                    window.location.href = "/admin/goods";
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("Lỗi hệ thống", {
                icon: "error",
            });
        }
    });
});

$('#cancelButton').click(function () {
    window.location.href = "/admin/goods";
});

$('#levelOne').on('change', function () {
    $.ajax({
        url: '/admin/categories/listForSelect?categoryId=' + $(this).val(),
        type: 'GET',
        success: function (result) {
            if (result.resultCode == 200) {
                var levelTwoSelect = '';
                var secondLevelCategories = result.data.secondLevelCategories;
                if (secondLevelCategories) {
                	var length2 = secondLevelCategories.length;
                    for (var i = 0; i < length2; i++) {
                        levelTwoSelect += '<option value=\"' + secondLevelCategories[i].categoryId + '\">' + secondLevelCategories[i].categoryName + '</option>';
                    }
                }
                
                $('#levelTwo').html(levelTwoSelect);
//                var levelThreeSelect = '';
//                var thirdLevelCategories = result.data.thirdLevelCategories;
//                var length3 = thirdLevelCategories.length;
//                for (var i = 0; i < length3; i++) {
//                    levelThreeSelect += '<option value=\"' + thirdLevelCategories[i].categoryId + '\">' + thirdLevelCategories[i].categoryName + '</option>';
//                }
//                $('#levelThree').html(levelThreeSelect);
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("Lỗi hệ thống", {
                icon: "error",
            });
        }
    });
});

//$('#levelTwo').on('change', function () {
//    $.ajax({
//        url: '/admin/categories/listForSelect?categoryId=' + $(this).val(),
//        type: 'GET',
//        success: function (result) {
//            if (result.resultCode == 200) {
//                var levelThreeSelect = '';
//                var thirdLevelCategories = result.data.thirdLevelCategories;
//                var length = thirdLevelCategories.length;
//                for (var i = 0; i < length; i++) {
//                    levelThreeSelect += '<option value=\"' + thirdLevelCategories[i].categoryId + '\">' + thirdLevelCategories[i].categoryName + '</option>';
//                }
//                $('#levelThree').html(levelThreeSelect);
//            } else {
//                swal(result.message, {
//                    icon: "error",
//                });
//            }
//            ;
//        },
//        error: function () {
//            swal("Lỗi hệ thống", {
//                icon: "error",
//            });
//        }
//    });
//});
