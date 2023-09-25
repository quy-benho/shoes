package shoe.store.entity;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import shoe.store.util.DeliveryStatus;
import shoe.store.util.PaymentStatus;

@Entity
@Getter @Setter
@Table(name = "order_web")
public class OrderWeb extends BaseEntity {

	private static final long serialVersionUID = 4939518312771230350L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = false)
	private User user;

	@Column(name = "consignee", nullable = false, length = 100)
	private String consignee;

	@Column(name = "consignee_phone", nullable = false, length = 20)
	private String consigneePhone;

	@Column(name = "payment_method", nullable = false, length = 50)
	private String paymentMethod;

	@Column(name = "payment_status", nullable = false, length = 50)
	private String paymentStatus = PaymentStatus.CANCELLED;

	@Column(name = "delivery_status", nullable = false, length = 50)
	private String deliveryStatus = DeliveryStatus.CANCELLED;

	@Column(name = "delivery_address", nullable = false, length = 200)
	private String deliveryAddress;

	@Column(name = "total_amount", nullable = false)
	private Long totalAmount = 0L;

	@Column(name = "sent_mail", nullable = true)
	private Boolean sentMail = false;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderWeb")
	private Set<OrderWebDetail> orderWebDetails = new HashSet<OrderWebDetail>(0);

	public String formatPrice(Long price) {
		return new DecimalFormat("#,###").format(price).replace(",", ".") + " ₫";
	}

	@JsonProperty
	public String getFormatTotalAmount() {
		return formatPrice(totalAmount);
	}

	@JsonProperty
	public String getFormatId() {
		return "TS" + this.id;
	}

}
