package shoe.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "delivery_log")
public class DeliveryLog extends BaseEntity {

	private static final long serialVersionUID = -1142781677627984261L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_web_id", nullable = false)
	private OrderWeb orderWeb;

	@Column(name = "delivery_status", nullable = false, length = 50)
	private String deliveryStatus;

}
