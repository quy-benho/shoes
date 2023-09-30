/**
 * jqGrid Chinese Translation
 * 咖啡兔 yanhonglei@gmail.com 
 * http://www.kafeitu.me 
 * 
 * 花岗岩 marbleqi@163.com
 * 
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html 
**/
/*global jQuery, define */
(function( factory ) {
	"use strict";
	if ( typeof define === "function" && define.amd ) {
		// AMD. Register as an anonymous module.
		define([
			"jquery",
			"../grid.base"
		], factory );
	} else {
		// Browser globals
		factory( jQuery );
	}
}(function( $ ) {

$.jgrid = $.jgrid || {};
if(!$.jgrid.hasOwnProperty("regional")) {
	$.jgrid.regional = [];
}
$.jgrid.regional["cn"] = {
    defaults : {
        recordtext: "Bản ghi {0} đến Bản ghi {1} \u3000 Tổng cộng {2}", // 共字前是全角空格
        emptyrecords: "Không có dữ liệu！",
        loadtext: "Loading...",
	savetext: "Đang lưu...",
        pgtext : "Trang {0} \u3000 Tổng số {1} trang",
		pgfirst : "Trang đầu tiên",
		pglast : "Trang cuối cùng",
		pgnext : "Trang tiếp theo",
		pgprev : "Trang trước",
		pgrecs : "Số lượng bản ghi trên mỗi trang",
		showhide: "Chuyển đổi bảng thu gọn mở rộng",
		// mobile
		pagerCaption : "Bảng :: Thiết lập trang",
		pageText : "Page:",
		recordPage : "Số lượng bản ghi trên mỗi trang",
		nomorerecs : "Không có thêm bản ghi...",
		scrollPullup: "Tải thêm...",
		scrollPulldown : "Làm mới...",
		scrollRefresh : "Làm mới trang..."
    },
    search : {
        caption: "Tìm kiếm...",
        Find: "Tìm thấy",
        Reset: "Cài lại",
        odata: [{ oper:'eq', text:'Bằng\u3000\u3000'},{ oper:'ne', text:'Khác\u3000'},{ oper:'lt', text:'Nhỏ hơn\u3000\u3000'},{ oper:'le', text:'Nhỏ hơn hoặc bằng'},{ oper:'gt', text:'Nhiều hơn\u3000\u3000'},{ oper:'ge', text:'Lớn hơn hoặc bằng'},{ oper:'bw', text:'Bắt đầu với'},{ oper:'bn', text:'Không phải ở đầu'},{ oper:'in', text:'Thuộc về\u3000\u3000'},{ oper:'ni', text:'Không thuộc về'},{ oper:'ew', text:'Kết thúc là'},{ oper:'en', text:'Không phải ở cuối'},{ oper:'cn', text:'Lưu trữ\u3000\u3000'},{ oper:'nc', text:'Không chứa'},{ oper:'nu', text:'Trống rỗng'},{ oper:'nn', text:'Có giá trị'}, {oper:'bt', text:'Khoảng thời gian'}],
        groupOps: [ { op: "AND", text: "Đáp ứng mọi điều kiện" },    { op: "OR",  text: "Đáp ứng mọi điều kiện" } ],
		operandTitle : "Bấm để tìm kiếm",
		resetTitle : "Đặt lại tiêu chí tìm kiếm",
		addsubgrup : "Thêm nhóm điều kiện",
		addrule : "Thêm điều kiện",
		delgroup : "Xóa nhóm điều kiện",
		delrule : "Xóa điều kiện"		
    },
    edit : {
        addCaption: "Thêm bản ghi",
        editCaption: "Chỉnh sửa bản ghi",
        bSubmit: "Gửi đi",
        bCancel: "Hủy",
        bClose: "Đóng",
        saveData: "Bạn có muốn lưu không？",
        bYes : "Lưu",
        bNo : "Không",
        bExit : "Thoát",
        msg: {
            required:"Trường này là bắt buộc",
            number:"Vui lòng nhập một số hợp lệ",
            minValue:"Giá trị đầu vào phải lớn hơn hoặc bằng ",
            maxValue:"Giá trị đầu vào phải nhỏ hơn hoặc bằng ",
            email: "Email không hợp lệ",
            integer: "Vui lòng nhập một số nguyên hợp lệ",
            date: "Vui lòng nhập thời gian hợp lệ",
            url: "Vui lòng nhập địa chỉ hợp lệ ('http://' hoặc 'https://')",
            nodefined : " Không xác định！",
            novalue : " Cần một giá trị trả lại ！",
            customarray : "Hàm tùy chỉnh cần trả về một mảng！",
            customfcheck : "Phải có một chức năng tùy chỉnh!"
        }
    },
    view : {
        caption: "Xem bản ghi",
        bClose: "Đóng"
    },
    del : {
        caption: "Xóa",
        msg: "Xóa các bản ghi đã chọn？",
        bSubmit: "Xóa",
        bCancel: "Hủy"
    },
    nav : {
        edittext: "",
        edittitle: "Chỉnh sửa bản ghi đã chọn",
        addtext:"",
        addtitle: "Thêm bản ghi mới",
        deltext: "",
        deltitle: "Xóa các bản ghi đã chọn",
        searchtext: "",
        searchtitle: "Tìm thấy",
        refreshtext: "",
        refreshtitle: "Làm mới bảng",
        alertcap: "Ghi chú",
        alerttext: "Vui lòng chọn một bản ghi",
        viewtext: "",
        viewtitle: "Xem các bản ghi đã chọn",
		savetext: "",
		savetitle: "Giữ bản ghi",
		canceltext: "",
		canceltitle : "Hủy chỉnh sửa bản ghi",
		selectcaption : "Quản lý..."
    },
    col : {
        caption: "Chọn cột",
        bSubmit: "Ok",
        bCancel: "Hủy"
    },
    errors : {
        errcap : "Lỗi",
        nourl : "Không có url nào được đặt",
        norecords: "Không có bản ghi để xử lý",
        model : "colNames và colModel có độ dài khác nhau！"
    },
    formatter : {
        integer : {thousandsSeparator: ",", defaultValue: '0'},
        number : {decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},
        currency : {decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, prefix: "", suffix:"", defaultValue: '0.00'},
        date : {
            dayNames:   [
               "日", "một", "hai", "ba", "bốn", "năm", "sáu",
                "Chủ nhật thứ hai thứ ba thứ tư thứ năm thứ sáu Thứ Bảy",
            ],
            monthNames: [
              "Một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín", "mười", "mười một", "mười hai",
                "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
            ],
            AmPm : ["am","pm","Buổi sáng","Buổi chiều"],
            S: function (j) {return j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1) % 10, 3)] : 'th';},
            srcformat: 'Y-m-d',
            newformat: 'Y-m-d',
            parseRe : /[#%\\\/:_;.,\t\s-]/,
            masks : {
                // see http://php.net/manual/en/function.date.php for PHP format used in jqGrid
                // and see http://docs.jquery.com/UI/Datepicker/formatDate
                // and https://github.com/jquery/globalize#dates for alternative formats used frequently
                // one can find on https://github.com/jquery/globalize/tree/master/lib/cultures many
                // information about date, time, numbers and currency formats used in different countries
                // one should just convert the information in PHP format
                ISO8601Long:"Y-m-d H:i:s",
                ISO8601Short:"Y-m-d",
                // short date:
                //    n - Numeric representation of a month, without leading zeros
                //    j - Day of the month without leading zeros
                //    Y - A full numeric representation of a year, 4 digits
                // example: 3/1/2012 which means 1 March 2012
                ShortDate: "n/j/Y", // in jQuery UI Datepicker: "M/d/yyyy"
                // long date:
                //    l - A full textual representation of the day of the week
                //    F - A full textual representation of a month
                //    d - Day of the month, 2 digits with leading zeros
                //    Y - A full numeric representation of a year, 4 digits
                LongDate: "l, F d, Y", // in jQuery UI Datepicker: "dddd, MMMM dd, yyyy"
                // long date with long time:
                //    l - A full textual representation of the day of the week
                //    F - A full textual representation of a month
                //    d - Day of the month, 2 digits with leading zeros
                //    Y - A full numeric representation of a year, 4 digits
                //    g - 12-hour format of an hour without leading zeros
                //    i - Minutes with leading zeros
                //    s - Seconds, with leading zeros
                //    A - Uppercase Ante meridiem and Post meridiem (AM or PM)
                FullDateTime: "l, F d, Y g:i:s A", // in jQuery UI Datepicker: "dddd, MMMM dd, yyyy h:mm:ss tt"
                // month day:
                //    F - A full textual representation of a month
                //    d - Day of the month, 2 digits with leading zeros
                MonthDay: "F d", // in jQuery UI Datepicker: "MMMM dd"
                // short time (without seconds)
                //    g - 12-hour format of an hour without leading zeros
                //    i - Minutes with leading zeros
                //    A - Uppercase Ante meridiem and Post meridiem (AM or PM)
                ShortTime: "g:i A", // in jQuery UI Datepicker: "h:mm tt"
                // long time (with seconds)
                //    g - 12-hour format of an hour without leading zeros
                //    i - Minutes with leading zeros
                //    s - Seconds, with leading zeros
                //    A - Uppercase Ante meridiem and Post meridiem (AM or PM)
                LongTime: "g:i:s A", // in jQuery UI Datepicker: "h:mm:ss tt"
                SortableDateTime: "Y-m-d\\TH:i:s",
                UniversalSortableDateTime: "Y-m-d H:i:sO",
                // month with year
                //    Y - A full numeric representation of a year, 4 digits
                //    F - A full textual representation of a month
                YearMonth: "F, Y" // in jQuery UI Datepicker: "MMMM, yyyy"
            },
            reformatAfterEdit : false,
			userLocalTime : false
        },
        baseLinkUrl: '',
        showAction: '',
        target: '',
        checkbox : {disabled:true},
        idName : 'id'
    },
	colmenu : {
		sortasc : "Sắp xếp tăng dần",
		sortdesc : "Sắp xếp giảm dần",
		columns : "Cột",
		filter : "Lọc",
		grouping : "Phân loại",
		ungrouping : "Hủy phân loại",
		searchTitle : "Tìm thấy:",
		freeze : "Đóng băng",
		unfreeze : "Hủy đóng băng",
		reorder : "Sắp xếp lại"
	}
};
}));
