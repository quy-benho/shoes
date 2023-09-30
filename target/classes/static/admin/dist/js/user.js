$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/users/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'userId', index: 'userId', width: 50, key: true, hidden: true},
            {label: 'Username', name: 'loginName', index: 'loginName', width: 60},
            {label: 'Tên', name: 'nickName', index: 'nickName', width: 60},
            {label: 'Địa chỉ', name: 'address', index: 'address', width: 150},
            {label: 'Chữ ký', name: 'introduceSign', index: 'introduceSign', width: 80},
            {label: 'Trạng thái', name: 'lockedFlag', index: 'lockedFlag', width: 70, formatter: lockedFormatter},

            {label: 'Thời gian đăng ký', name: 'createTime', index: 'createTime', width: 80}
        ],
        height: 'auto',
        rowNum: 10,
        rowList: [5, 10, 20, 50, 100],
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

    function lockedFormatter(cellvalue) {
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm mt-2\" style=\"width: 95%;\">Khóa</button>";
        } else if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm mt-2\" style=\"width: 95%;\">Mở</button>";
        }
    }

    function deletedFormatter(cellvalue) {
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 100%;\">Có</button>";
        } else if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 100%;\">Không</button>";
        }
    }
});


function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}


function lockUser(lockStatus) {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    if (lockStatus != 0 && lockStatus != 1) {
        swal('Không hợp lệ', {
            icon: "error",
        });
    }
    swal({
        title: "Xác nhận",
        text: "Bạn có chắc chắn muốn sửa đổi trạng thái tài khoản không?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/users/lock/" + lockStatus,
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

$('#btn-updateUser').click(function() {
	var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    if (ids.length>1) {
    	swal("Chỉ chọn 1 bản ghi", {
            icon: "warning",
        });
    	return;
    }

	let nickName = $( `#${ids}>td[aria-describedby='jqGrid_nickName']` ).text();
	let address = $( `#${ids}>td[aria-describedby='jqGrid_address']` ).text();
	let introduceSign = $( `#${ids}>td[aria-describedby='jqGrid_introduceSign']` ).text();

	$('#userId').val(ids);
	$('#nickName').val(nickName);
	$('#address').val(address);
	$('#introduceSign').val(introduceSign);
	$('#userFormModal').modal('show');
});

$('#userForm').submit(function(e) {
	e.preventDefault();

	$.ajax({
	    type: 'PUT',
	    url: '/admin/users/update',
	    data: $('#userForm').serialize(),
	    success: function (result) {
	        if (result.resultCode == 200) {
	        	$("#jqGrid").jqGrid('setGridParam', {
	                url: '/admin/users/list',
	                page: $("#jqGrid").jqGrid('getGridParam', 'page')
	            }).trigger("reloadGrid");
	        	
	        	$('#userFormModal').modal('hide');
	        	
	        	swal("Thành công", {
	                icon: "success",
	            });
	        	
	        } else {
	            swal("Lỗi hệ thống", {
	                icon: "error",
	            });
	        }
	    },
	    error: function () {
	        swal("Lỗi hệ thống", {
	            icon: "error",
	        });
	    }
	});

//	let nickName = $( `#${ids}>td[aria-describedby='jqGrid_nickName']` ).text();
//	let address = $( `#${ids}>td[aria-describedby='jqGrid_address']` ).text();
//	let introduceSign = $( `#${ids}>td[aria-describedby='jqGrid_introduceSign']` ).text();
//
//	$('#nickName').val(nickName);
//	$('#address').val(address);
//	$('#introduceSign').val(introduceSign);
});

