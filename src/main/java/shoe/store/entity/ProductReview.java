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
@Table(name = "product_review")
public class ProductReview extends BaseEntity {

	private static final long serialVersionUID = 3861713291646081545L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = false)
	private User user;

	@Column(name = "rating")
	private Short rating;

	@Column(name = "title", length = 50)
	String title;

	@Column(name = "content", nullable = false, length = 500)
	String content;

}
