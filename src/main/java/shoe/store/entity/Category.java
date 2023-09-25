package shoe.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "category")
@NoArgsConstructor @AllArgsConstructor
public class Category extends BaseEntity {

	private static final long serialVersionUID = 2266934457478800397L;

	@Column(name = "name", unique = true, nullable = false, length = 50)
	private String name;

}
