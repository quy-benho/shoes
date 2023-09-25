package shoe.store.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

	private static final long serialVersionUID = 5143334592028679331L;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;

	@JsonIgnore
	@Column(name = "password", nullable = false, length = 200)
	private String password;

	@Column(name = "gender")
	private Boolean gender;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "phone", length = 20)
	private String phone;

	@Column(name = "address", length = 200)
	private String address;

	@Column(name = "role", length = 50)
	private String role;

	@Column(name = "is_delete", nullable = false)
	private Boolean isDelete = false;

	@JsonIgnore
	@Column(name = "image_data", columnDefinition="LONGBLOB")
	private byte[] imageData;

	@Column(name = "image_path", length = 500)
	private String imagePath;

	public User(String name, String email, String phone) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

}
