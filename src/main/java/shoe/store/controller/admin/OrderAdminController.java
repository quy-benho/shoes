package shoe.store.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shoe.store.entity.DeliveryLog;
import shoe.store.entity.OrderWeb;
import shoe.store.payload.Meta;
import shoe.store.payload.response.ListResponse;
import shoe.store.repository.DeliveryLogRepository;
import shoe.store.service.OrderWebService;
import shoe.store.service.ProductService;
import shoe.store.util.DeliveryStatus;
import shoe.store.util.PaymentStatus;
import shoe.store.util.Validator;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdminController {

	@Autowired
	ProductService productService;

	@Autowired
	DeliveryLogRepository deliveryLogRepository;

	@Autowired
	OrderWebService orderWebService;

	@PostMapping()
	public String showOrders() {
		
		return "admin/orders";
	}

	@ResponseBody
	@PostMapping(path = "/data")
	public ListResponse<OrderWeb> getDataOrders(@RequestParam Map<String, String> request) {

		PageRequest pageRequest;
		Meta meta = new Meta(request);
		String field = meta.getField();
		String sort = meta.getSort();
		String text = meta.getQuerySearch();
		String paymentMethod = meta.getQueryMethod();
		String paymentStatus = meta.getQueryPayment();
		String deliveryStatus = meta.getQueryDelivery();

		if (sort.equalsIgnoreCase("asc")) {
			pageRequest = PageRequest.of(meta.getPage() - 1, meta.getPerpage(), Sort.by(field).ascending());
		} else {
			pageRequest = PageRequest.of(meta.getPage() - 1, meta.getPerpage(), Sort.by(field).descending());
		}

		Page<OrderWeb> ordersPage = orderWebService.searchFilter(text, paymentMethod, paymentStatus, deliveryStatus, pageRequest);

		meta.setPages(ordersPage.getTotalPages());
		meta.setTotal(ordersPage.getTotalElements());
		ListResponse<OrderWeb> productsResponse = new ListResponse<OrderWeb>(meta, ordersPage.getContent());

		return productsResponse;
	}

	@GetMapping("/detail/*")
	public String redirectOrderDetailAdminGet() {

		return "admin/layout";
	}

	@PostMapping("/detail/{id}")
	public String showOrderDetail(@PathVariable String id, ModelMap model) {

		if (Validator.checkId(id)) {
			OrderWeb orderWeb = orderWebService.findById(Long.valueOf(id)).get();
			String deliveryStatus = orderWeb.getDeliveryStatus();
			List<Map<String, String>> actions = new ArrayList<Map<String, String>>();
			boolean hasCancelled = true;
			String undoAction = "";
			Map<String, String> actionMap = new HashMap<String, String>();

			switch (deliveryStatus) {
			case DeliveryStatus.NOT_APPROVED:

				actionMap.put("status", DeliveryStatus.WAITING_FOR_DELIVERY);
				actionMap.put("title", "Duyệt đơn hàng này");
				actionMap.put("class", "btn-success");
				actions.add(actionMap);
				break;

			case DeliveryStatus.WAITING_FOR_DELIVERY:
				undoAction = DeliveryStatus.NOT_APPROVED;

				actionMap.put("status", DeliveryStatus.DELIVERING);
				actionMap.put("title", "Bắt đâu giao hàng");
				actionMap.put("class", "btn-primary");
				actions.add(actionMap);
				break;

			case DeliveryStatus.DELIVERING:
				undoAction = DeliveryStatus.WAITING_FOR_DELIVERY;

				actionMap.put("status", DeliveryStatus.SUCCESSFUL);
				actionMap.put("title", "Giao hàng thành công");
				actionMap.put("class", "btn-success");
				actions.add(actionMap);

				actionMap = new HashMap<String, String>();
				actionMap.put("status", DeliveryStatus.WAITING_FOR_DELIVERY_2);
				actionMap.put("title", "Giao hàng thất bại, chờ giao lần 2");
				actionMap.put("class", "btn-warning");
				actions.add(actionMap);
				break;

			case DeliveryStatus.WAITING_FOR_DELIVERY_2:
				undoAction = DeliveryStatus.DELIVERING;

				actionMap.put("status", DeliveryStatus.DELIVERING_2);
				actionMap.put("title", "Bắt đầu giao hàng lần 2");
				actionMap.put("class", "btn-primary");
				actions.add(actionMap);
				break;

			case DeliveryStatus.DELIVERING_2:
				undoAction = DeliveryStatus.WAITING_FOR_DELIVERY_2;

				actionMap.put("status", DeliveryStatus.SUCCESSFUL);
				actionMap.put("title", "Giao hàng thành công");
				actionMap.put("class", "btn-success");
				actions.add(actionMap);

				actionMap = new HashMap<String, String>();
				actionMap.put("status", DeliveryStatus.CANCELLED);
				actionMap.put("title", "Giao hàng không thành công");
				actionMap.put("class", "btn-warning");
				actions.add(actionMap);
				break;

			case DeliveryStatus.SUCCESSFUL:
				hasCancelled = false;
				break;

			case DeliveryStatus.CANCELLED:
				hasCancelled = false;
				break;

			default:
				break;
			}

			model.addAttribute("actions", actions);
			model.addAttribute("orderWeb", orderWeb);
			model.addAttribute("undoAction", undoAction);
			model.addAttribute("hasCancelled", hasCancelled);
		}

		return "admin/order-detail";
	}

	@ResponseBody
	@PostMapping("/cancel/{id}")
	public String adminCancelOrder(@PathVariable String id) {

		OrderWeb orderWeb = orderWebService.findById(Long.valueOf(id)).get();
		orderWeb.setDeliveryStatus(DeliveryStatus.CANCELLED);
		String paymentStatus = orderWeb.getPaymentStatus();
		if (paymentStatus.equalsIgnoreCase(PaymentStatus.UNPAID) || paymentStatus.equalsIgnoreCase(PaymentStatus.PENDING_ATM)) {
			orderWeb.setPaymentStatus(PaymentStatus.CANCELLED);
		}
		orderWebService.save(orderWeb);
		return "";
	}

	@ResponseBody
	@PostMapping("/change-status")
	public String adminChangeStatusOrder(@RequestParam String id, @RequestParam String status) {

		OrderWeb orderWeb = orderWebService.findById(Long.valueOf(id)).get();
		orderWeb.setDeliveryStatus(status);
		
		if (status.equalsIgnoreCase(DeliveryStatus.SUCCESSFUL)) {
			orderWeb.setPaymentStatus(PaymentStatus.PAID);


		} else if (status.equalsIgnoreCase(DeliveryStatus.CANCELLED)) {
			String paymentStatus = orderWeb.getPaymentStatus();
			if (paymentStatus.equalsIgnoreCase(PaymentStatus.UNPAID) || paymentStatus.equalsIgnoreCase(PaymentStatus.PENDING_ATM)) {
				orderWeb.setPaymentStatus(PaymentStatus.CANCELLED);
			}
		}

		orderWebService.save(orderWeb);
		DeliveryLog dlog = new DeliveryLog();
		dlog.setOrderWeb(orderWeb);
		dlog.setDeliveryStatus(orderWeb.getDeliveryStatus());
		deliveryLogRepository.save(dlog);
		return "";
	}

}
