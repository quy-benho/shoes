package shoe.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "color")
public class Color extends BaseEntity {

	private static final long serialVersionUID = -2507430338778398025L;

	@Column(name = "name", nullable = false, length = 50)
	String name;

	@Column(name = "code", unique = true, length = 50)
	String code;

}
