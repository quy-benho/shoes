package shoe.store.entity;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

	private static final long serialVersionUID = 4666563959323744392L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_size_id", nullable = false)
	private ProductSize productSize;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@JsonProperty
	public String formatPrice(Long price) {
		return new DecimalFormat("#,###").format(price).replace(",", ".") + " ₫";
	}

}
