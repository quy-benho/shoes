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
@Table(name = "order_app")
public class OrderApp extends BaseEntity {

	private static final long serialVersionUID = 3286241768164677070L;

	public static final String MONEY_DELIVERED = "Đã giao tiền";
	public static final String MONEY_NOT_DELIVERED = "Chưa giao tiền";

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", nullable = false)
	private User user;

	@Column(name = "total_amount", nullable = false)
	private Long totalAmount;

	@Column(name = "status", nullable = false, length = 50)
	private String status = MONEY_NOT_DELIVERED;

}
