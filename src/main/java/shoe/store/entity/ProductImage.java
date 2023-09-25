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
@Table(name = "product_image")
public class ProductImage extends BaseEntity {

	private static final long serialVersionUID = 3059803149125917929L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	Product product;

	@JsonIgnore
	@Column(name = "data", columnDefinition="LONGBLOB")
	private byte[] data;

	@Column(name = "path", length = 500)
	private String path;

	@Column(name = "size")
	private Integer size;

	@Column(name = "is_primary")
	private Boolean isPrimary = false;

}
