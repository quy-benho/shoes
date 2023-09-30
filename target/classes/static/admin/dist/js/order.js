$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/orders/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'orderId', index: 'orderId', width: 50, key: true, hidden: true},
            {label: 'Mã đơn hàng', name: 'orderNo', index: 'orderNo', width: 120},
            {label: 'Tổng tiền', name: 'totalPrice', index: 'totalPrice', width: 80, formatter: priceFormatter},
            {label: 'Trạng thái', name: 'orderStatus', index: 'orderStatus', width: 80, formatter: orderStatusFormatter},
            {label: 'Phương thức', name: 'payType', index: 'payType', width: 80,formatter:payTypeFormatter},
            {label: 'Địa chỉ', name: 'userAddress', index: 'userAddress', width: 10, hidden: true},
            {label: 'Số ĐT', name: 'userPhone', index: 'userPhone', width: 10, hidden: true},
            {label: 'Thời gian đặt', name: 'createTime', index: 'createTime', width: 120},
            {label: 'Điều hành', name: 'createTime', index: 'createTime', width: 100, formatter: operateFormatter}
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
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function operateFormatter(cellvalue, rowObject) {
        return "<a href=\'##\' onclick=openOrderItems(" + rowObject.rowId + ")>Xem thông tin đặt hàng</a>" +
            "<br>" +
            "<a href=\'##\' onclick=openExpressInfo(" + rowObject.rowId + ")>Xem thông tin người nhận</a>";
    }

    function orderStatusFormatter(cellvalue) {
    	let orderStatus = getOrderStatusByCode(cellvalue);
    	return orderStatus ? orderStatus.name : cellvalue
    }

    function payTypeFormatter(cellvalue) {
      
        if (cellvalue == 0) {
            return "Không";
        }
        if (cellvalue == 1) {
            return "ATM";
        }
        if (cellvalue == 2) {
            return "COD";
        }
        return cellvalue;
    }

    function priceFormatter(cellvalue) {
    	let s0 = (cellvalue * 1).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,').replace(/,/g, '.');
		let s1 = s0.substring(0, s0.length-3);
		return s1+' ₫';
    }

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

});

function reload() {
//    initFlatPickr();
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function openOrderItems(orderId) {
    $.ajax({
        type: 'GET',
        url: '/admin/order-items/' + orderId,
        contentType: 'application/json',
        success: function (result) {
            if (result.resultCode == 200) {
                $('#orderItemModal').modal('show');
                var itemString = '';
                for (i = 0; i < result.data.length; i++) {
                    itemString += result.data[i].goodsName + ' x ' + result.data[i].goodsCount + ' | ID sản phẩm: ' + result.data[i].goodsId +' | Màu: '+ result.data[i].goodsColor + ' | Size: '+ result.data[i].goodsSize + "<br>";
                }
                $("#orderItemString").html(itemString);
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
}


function openExpressInfo(orderId) {
    var rowData = $("#jqGrid").jqGrid("getRowData", orderId);
    $('#expressInfoModal').modal('show');
    $("#userPhoneInfo").text(rowData.userPhone);
    $("#userAddressInfo").text(rowData.userAddress);
}


function orderEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    var rowData = $("#jqGrid").jqGrid("getRowData", id);
    $('#orderInfoModal').modal('show');
    $("#orderId").val(id);
    $("#userAddress").val(rowData.userAddress);
    $("#totalPrice").val(rowData.totalPrice.replaceAll('.','').replaceAll(' ₫',''));
}


$('#saveButton').click(function () {
    var totalPrice = $("#totalPrice").val();
    var userName = $("#userName").val();
    var userPhone = $("#userPhone").val();
    var userAddress = $("#userAddress").val();
    var id = getSelectedRowWithoutAlert();
    var url = '/admin/orders/update';
    var data = {
        "orderId": id,
        "totalPrice": totalPrice,
        "userName": userName,
        "userPhone": userPhone,
        "userAddress": userAddress
    };
    $.ajax({
        type: 'POST',
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                $('#orderInfoModal').modal('hide');
                swal("Thành công", {
                    icon: "success",
                });
                reload();
            } else {
                $('#orderInfoModal').modal('hide');
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

const error = text => {
	swal(text, {
        icon: "error",
    });
}

function orderCheckDone() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    var orderNos = '';
    for (i = 0; i < ids.length; i++) {
        var rowData = $("#jqGrid").jqGrid("getRowData", ids[i]);
        if (rowData.orderStatus != 'Đã thanh toán' && rowData.orderStatus != 'Hết hàng' && rowData.orderStatus != 'Đặt ship COD') {
            orderNos += rowData.orderNo + " ";
        }
    }
    if (orderNos.length > 0 & orderNos.length < 100) {
        swal(orderNos + "Đơn hàng không chuyển trạng thái thành công được", {
            icon: "error",
        });
        return;
    }
    if (orderNos.length >= 100) {
        swal("Vui lòng chọn 1 lựa chọn", {
            icon: "error",
        });
        return;
    }
    swal({
        title: "Xác nhận",
        text: "Bạn có chắc chắn muốn thực hiện thao tác hoàn thành không?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/orders/checkDone",
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
    );
}

function orderCheckOut() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    var orderNos = '';
    for (i = 0; i < ids.length; i++) {
        var rowData = $("#jqGrid").jqGrid("getRowData", ids[i]);
        if (rowData.orderStatus != 'Đã thanh toán' 
        		&& rowData.orderStatus != 'Đặt ship COD'
        		&& rowData.orderStatus != 'Đang thanh toán') {
            orderNos += rowData.orderNo + " ";
        }
    }
    if (orderNos.length > 0 & orderNos.length < 100) {
        swal(orderNos + "Đơn hàng đã đóng", {
            icon: "error",
        });
        return;
    }
    if (orderNos.length >= 100) {
        swal("Vui lòng chọn 1 lựa chọn", {
            icon: "error",
        });
        return;
    }
    swal({
        title: "Xác nhận",
        text: "Bạn có chắc chắn muốn thực hiện thao tác gửi đi không?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/orders/checkOut",
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

function closeOrder() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "Xác nhận",
        text: "Bạn có chắc chắn muốn đóng đơn đặt hàng không?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/orders/close",
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
    $("#totalPrice").val(0);
    $("#userAddress").val('');
    $('#edit-error-msg').css("display", "none");
}

function search(param) {
    $("#jqGrid").jqGrid('setGridParam', {
        url: `/admin/orders/list${param}`,
        page: 1
    }).trigger("reloadGrid");
}

$('#btn-search').click(function() {
	let param = [];
	let totalPriceMin, totalPriceMax;
	let orderNo = $('#orderNo').val().trim();
	let totalAmountType = $('input[name=totalAmountType]:checked', '#searchForm').val();
	let orderStatus = $('input[name=orderStatus]:checked', '#searchForm').val();
	let payType = $('input[name=payType]:checked', '#searchForm').val();

	if (totalAmountType == 0) {
		totalPriceMin = 0;
		totalPriceMax = 999999;
	} else if (totalAmountType == 1) {
		totalPriceMin = 999999;
		totalPriceMax = 3000001;
	} else if (totalAmountType == 2) {
		totalPriceMin = 3000000;
		totalPriceMax = 100000000;
	}

	if (orderNo) param.push(`orderNo=${orderNo}`);
	if (totalPriceMin) param.push(`totalPriceMin=${totalPriceMin}`);
	if (totalPriceMax) param.push(`totalPriceMax=${totalPriceMax}`);
	if (orderStatus) param.push(`orderStatus=${orderStatus}`);
	if (payType) param.push(`payType=${payType}`);

	search(param.length>0 ? '?'+param.join("&") : '');
});

$('#btn-reset').click(function() {
	search('');
});


const orderStatusList = [
	{code: 0, name:"Chưa thanh toán"},
	{code: 1, name:"Đã thanh toán"},
	{code: 2, name:"Giao thành công", ignoreCode: [-1, -2, -3, 0, 1, 2, 3, 4], customErrorMsg: 'Đơn không trong trạng thái "Đang giao hàng"'},
	{code: 3, name:"Đã hết hàng", ignoreCode: [-1, -2, -3, 2, 5]},
	{code: 4, name:"Đã đặt ship COD"},
	{code: 5, name:"Đang giao hàng", ignoreCode: [-1, -2, -3, 0, 2, 5]},
	{code: -1, name:"Khách đã hủy"},
	{code: -2, name:"Quá hạn"},
	{code: -3, name:"Đã đóng", ignoreCode: [-3]}
]

const getOrderStatusByName = name => {
	return orderStatusList.find(status => status.name == name);
}

const getOrderStatusByCode = code => {
	return orderStatusList.find(status => status.code == code);
}

const getOrderId = () => {
	let ids = getSelectedRows();
	if (ids) {
		if (ids.length == 1) {
	        return ids[0];
	    } else {
	    	swal("Vui lòng chọn 1 lựa chọn", {
	            icon: "error",
	        });
	        return null;
	    }
	} else return null;
}

const getCurrentOrderStatus = orderId => {
	let currentOrderStatusName = $("#jqGrid").jqGrid("getRowData", orderId).orderStatus;
	return getOrderStatusByName(currentOrderStatusName);
}

const changeOrderStatus = newStatusCode => {
	let orderId = getOrderId();
	if (orderId) {
		let currentOrderStatus = getCurrentOrderStatus(orderId);
		let newOrderStatus = getOrderStatusByCode(newStatusCode)
		if (!currentOrderStatus || !newOrderStatus) {
			error(`Trạng thái không hợp lệ!`);
			return;
		}

		let valid = true;
		newOrderStatus.ignoreCode.some(code => {
			if (currentOrderStatus.code == code) {
				if (!newOrderStatus.customErrorMsg) {
					error(`Đơn hàng trong trạng thái "${currentOrderStatus.name}"`);
				} else {
					error(newOrderStatus.customErrorMsg);
				}
				valid = false;
				return true;
			}
		})
		if (!valid) return;

		swal({
	        title: "Xác nhận",
	        text: `Chuyển trạng thái sang "${newOrderStatus.name}"`,
	        icon: "warning",
	        buttons: ["Quay lại", "Xác nhận"],
	        dangerMode: true,
	    }).then((flag) => {
	            if (flag) {
	                $.ajax({
	                    type: "POST",
	                    data: JSON.stringify({orderId: orderId, orderStatus: newOrderStatus.code}),
	                    url: "/admin/orders/changeStatus",
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
	    );
	}
	
}
