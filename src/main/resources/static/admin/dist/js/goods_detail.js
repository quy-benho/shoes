$(function () {
    var goodsId = $("#goodsId").val();

    $("#jqGrid").jqGrid({
        url: '/admin/goodsDetail/list?goodsId=' + goodsId,
        datatype: "json",
        colModel: [
            {label: 'id', name: 'goodsDeatailId', index: 'goodsDeatailId', width: 50, key: true, hidden: true},
            {label: 'Mã sản phẩm', name: 'goodsId', index: 'goodsId', width: 120},
            {label: 'Màu sắc', name: 'goodsColor', index: 'goodsColor', width: 120},
            {label: 'Size', name: 'goodsSize', index: 'goodsSize', width: 120},
            {label: 'Số lượng', name: 'goodsQuantity', index: 'goodsQuantity', width: 120}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: 'Thông tin...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
});


function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function detailAdd() {
    reset();
    $('.modal-title').html('');
    $('#goodsDetailModal').modal('show');
}


// function categoryManage() {
//     var categoryLevel = parseInt($("#categoryLevel").val());
//     var parentId = $("#parentId").val();
//     var id = getSelectedRow();
//     if (id == undefined || id < 0) {
//         return false;
//     }
//     if (categoryLevel == 1 || categoryLevel == 2) {
//         categoryLevel = categoryLevel + 1;
//         window.location.href = '/admin/categories?categoryLevel=' + categoryLevel + '&parentId=' + id + '&backParentId=' + parentId;
//     } else {
//         swal("Không có danh mục phụ", {
//             icon: "warning",
//         });
//     }
// }


$('#saveButton').click(function () {
    var goodsColor = $("#goodsColor").val();
    var goodsSize = $("#goodsSize").val();
    var goodsQuantity = $("#goodsQuantity").val();
    var goodsId = $("#goodsId_").val();
    if (!validCN_ENString2_18(goodsColor)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("Vui lòng nhập tên danh mục!");
    } else {
        var data = {
            "goodsColor": goodsColor,
            "goodsSize": goodsSize,
            "goodsQuantity": goodsQuantity,
            "goodsId": goodsId
        };
        var url = '/admin/goodsDetail/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/admin/goodsDetail/update';
            data = {
                "goodsDeatailId": id,
                "goodsColor": goodsColor,
                "goodsSize": goodsSize,
                "goodsQuantity": goodsQuantity,
                "goodsId": goodsId
            };
        }
        $.ajax({
            type: 'POST',
            url: url,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#goodsDetailModal').modal('hide');
                    swal("Thành công !", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $('#goodsDetailModal').modal('hide');
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
    }
});

function detailEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    var rowData = $("#jqGrid").jqGrid("getRowData", id);
    $('.modal-title').html('');
    $('#goodsDetailModal').modal('show');
    $("#goodsDeatailId").val(id);
    $("#goodsColor").val(rowData.goodsColor);
    $("#goodsSize").val(rowData.goodsSize);
    $("#goodsQuantity").val(rowData.goodsQuantity);
    $("#goodsId_").val(rowData.goodsId);
}


function deleteDetail() {

	var id = getSelectedRowWithoutAlert();
    if (id == null) {
        return;
    }
    swal({
        title: "Xóa",
        text: "Bạn có muốn xóa không?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/goodsDetail/delete/" + id,
                    contentType: "application/json",
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("Thành công", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    )
    ;
}


function reset() {
    $("#goodsColor").val('');
    $("#goodsSize").val(0);
    $("#goodsQuantity").val(0);
    $("#goodsId_").val(0);
    $('#edit-error-msg').css("display", "none");
}

function search(param) {
    $("#jqGrid").jqGrid('setGridParam', {
        url: `/admin/goodsDetail/list${param}`,
        page: 1
    }).trigger("reloadGrid");
}

$('#btn-search').click(function() {
	let param = [];
	let goodsId = $('#goodsId').val().trim();
	if (goodsId) param.push(`goodsId=${goodsId}`);
	search(param.length>0 ? '?'+param.join("&") : '');
});

$('#btn-reset').click(function() {
	search('');
});
