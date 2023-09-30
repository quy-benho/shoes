$(function () {
    var configType = $("#configType").val();

    $("#jqGrid").jqGrid({
        url: '/admin/indexConfigs/list?configType=' + configType,
        datatype: "json",
        colModel: [
            {label: 'id', name: 'configId', index: 'configId', width: 50, key: true, hidden: true},
            {label: 'Tên sản phẩm', name: 'configName', index: 'configName', width: 180},
            {label: 'Link', name: 'redirectUrl', index: 'redirectUrl', width: 120},
            {label: 'Thứ tự', name: 'configRank', index: 'configRank', width: 120},
            {label: 'Mã sản phẩm', name: 'goodsId', index: 'goodsId', width: 120},
            {label: 'Thời gian', name: 'createTime', index: 'createTime', width: 120}
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

function configAdd() {
    reset();
    $('.modal-title').html('');
    $('#indexConfigModal').modal('show');
}

$('#saveButton').click(function () {
    var configName = $("#configName").val();
    var configType = $("#configType").val();
    var redirectUrl = $("#redirectUrl").val();
    var goodsId = $("#goodsId").val();
    var configRank = $("#configRank").val();
    if (!validCN_ENString2_18(configName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("Vui lòng nhập tên của mục cấu hình đáp ứng thông số kỹ thuật！");
    } else {
        var data = {
            "configName": configName,
            "configType": configType,
            "redirectUrl": redirectUrl,
            "goodsId": goodsId,
            "configRank": configRank
        };
        var url = '/admin/indexConfigs/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/admin/indexConfigs/update';
            data = {
                "configId": id,
                "configName": configName,
                "configType": configType,
                "redirectUrl": redirectUrl,
                "goodsId": goodsId,
                "configRank": configRank
            };
        }
        $.ajax({
            type: 'POST',
            url: url,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#indexConfigModal').modal('hide');
                    swal("Thành công", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $('#indexConfigModal').modal('hide');
                    swal(result.message, {
                        icon: "error",
                    });
                }
                ;
            },
            error: function () {
                swal("Tên quá dài", {
                    icon: "error",
                });
            }
        });
    }
});

function configEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    var rowData = $("#jqGrid").jqGrid("getRowData", id);
    $('.modal-title').html('');
    $('#indexConfigModal').modal('show');
    $("#configId").val(id);
    $("#configName").val(rowData.configName);
    $("#redirectUrl").val(rowData.redirectUrl);
    $("#goodsId").val(rowData.goodsId);
    $("#configRank").val(rowData.configRank);
}

function deleteConfig () {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "Xác nhận",
        text: "Bạn có muốn xóa không?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/indexConfigs/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
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
    $("#configName").val('');
    $("#redirectUrl").val('##');
    $("#configRank").val(0);
    $("#goodsId").val(0);
    $('#edit-error-msg').css("display", "none");
}