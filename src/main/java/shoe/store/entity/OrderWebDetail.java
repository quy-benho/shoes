package shoe.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "order_web_detail")
public class OrderWebDetail extends BaseEntity {

	private static final long serialVersionUID = -1211117630717120701L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_web_id", nullable = false)
	private OrderWeb orderWeb;

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
