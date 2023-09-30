$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/goods/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'goodsId', index: 'goodsId', width: 60, key: true},
            {label: 'Tên sản phẩm', name: 'goodsName', index: 'goodsName', width: 120},
            {label: 'Mô tả', name: 'goodsIntro', index: 'goodsIntro', width: 120},
            {label: 'Hình ảnh', name: 'goodsCoverImg', index: 'goodsCoverImg', width: 120, formatter: coverImageFormatter},
            {label: 'Kho hàng', name: 'stockNum', index: 'stockNum', width: 60, hidden: true},
            {label: 'Giá', name: 'sellingPrice', index: 'sellingPrice', width: 60, formatter: priceFormatter},
            {
                label: 'Trạng thái',
                name: 'goodsSellStatus',
                index: 'goodsSellStatus',
                width: 80,
                formatter: goodsSellStatusFormatter
            },
            {label: 'Thời gian', name: 'createTime', index: 'createTime', width: 60}
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

    function goodsSellStatusFormatter(cellvalue) {
        if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 80%;\">Đang bán</button>";
        }
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 80%;\">Dừng bán</button>";
        }
    }

    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + "' height=\"80\" width=\"80\" alt='Hình ảnh chính' class='mt-2'/>";
    }

    function priceFormatter(cellvalue) {
    	let s0 = (cellvalue * 1).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,').replace(/,/g, '.');
		let s1 = s0.substring(0, s0.length-3);
		return s1+' ₫';
    }

});


function reload() {
    initFlatPickr();
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}


function addGoods() {
    window.location.href = "/admin/goods/add";
}


function editGoods() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    window.location.href = "/admin/goods/edit/" + id;
}

function putUpGoods() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "Đăng bán sản phẩm",
        text: "Bạn có chắc chắn muốn thực hiện thao tác không?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "PUT",
                    url: "/admin/goods/status/0",
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

function putDownGoods() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "Dừng bán sản phẩm",
        text: "Bạn có chắc chắn muốn thực hiện thao tác không?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "PUT",
                    url: "/admin/goods/status/1",
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

function search(param) {
    $("#jqGrid").jqGrid('setGridParam', {
        url: `/admin/goods/list${param}`,
        page: 1
    }).trigger("reloadGrid");
}

$('#btn-search').click(function() {
	let param = [];
	let priceMin, priceMax;
	let goodsId = $('#goodsId').val().trim();
	let goodsName = $('#goodsName').val().trim();
	let goodsIntro = $('#goodsIntro').val().trim();
	let priceType = $('input[name=priceType]:checked', '#searchForm').val();

	if (priceType == 0) {
		priceMin = 0;
		priceMax = 999999;
	} else if (priceType == 1) {
		priceMin = 999999;
		priceMax = 3000001;
	} else if (priceType == 2) {
		priceMin = 3000000;
		priceMax = 100000000;
	} else if (priceType == 3) {
		priceMin = $('#customPrice').val()*1-1;
		priceMax = $('#customPrice').val()*1+1;
	}

	if (goodsId) param.push(`goodsId=${goodsId}`);
	if (goodsName) param.push(`goodsName=${goodsName}`);
	if (goodsIntro) param.push(`goodsIntro=${goodsIntro}`);
	if (priceMin) param.push(`priceMin=${priceMin}`);
	if (priceMax) param.push(`priceMax=${priceMax}`);

	search(param.length>0 ? '?'+param.join("&") : '');
});

$('input[name=priceType]').click(function() {
	$("#customPrice").prop('disabled', $(this).val() < 3);
});

$('#btn-reset').click(function() {
	$("#customPrice").prop('disabled', true);
	search('');
});
