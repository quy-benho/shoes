package shoe.store.payload;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta {

	private Integer page;
	private Integer pages;
	private Integer perpage;
	private Long total;
	private String sort;
	private String field;
	private String querySearch;
	private String queryStatus;
	private String queryMethod;
	private String queryPayment;
	private String queryDelivery;

	public Meta(Map<String, String> request) {
		String page = request.get("pagination[page]");
		String pages = request.get("pagination[pages]");
		String perpage = request.get("pagination[perpage]");
		String total = request.get("pagination[total]");
		String querySearch = request.get("query[]");
		String queryMethod = request.get("query[method]");
		String queryPayment = request.get("query[payment]");
		String queryDelivery = request.get("query[delivery]");

		this.page = page != null ? Integer.valueOf(page) : 1;
		this.pages = pages != null ? Integer.valueOf(pages) : 0;
		this.perpage = perpage != null ? Integer.valueOf(perpage) : 10;
		this.total = total != null ? Long.valueOf(total) : 0;
		this.field = fixOrderSortField(request.get("sort[field]"));
		this.sort = request.get("sort[sort]");
		this.querySearch = querySearch != null ? querySearch : "";
		this.queryMethod = queryMethod != null ? queryMethod : "";
		this.queryPayment = queryPayment != null ? queryPayment : "";
		this.queryDelivery = queryDelivery != null ? queryDelivery : "";

	}

	private String fixOrderSortField(String field) {
		switch (field) {
		case "paymentMethod":
			field = "payment_method";
			break;

		case "paymentStatus":
			field = "payment_status";
			break;

		case "deliveryStatus":
			field = "delivery_status";
			break;

		case "formatTotalAmount":
			field = "total_amount";
			break;

		default:
			break;
		}
		return field;
	}
}
