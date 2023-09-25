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
@Table(name = "order_app_detail")
public class OrderAppDetail extends BaseEntity {

	private static final long serialVersionUID = 508899283965091411L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_app_id", nullable = false)
	private OrderApp orderApp;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_size_id", nullable = false)
	private ProductSize productSize;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "price", nullable = false)
	private Long price;

	@Column(name = "total_amount", nullable = false)
	private Long totalAmount;

}
