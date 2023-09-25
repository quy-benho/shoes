package shoe.store.entity;

import java.text.DecimalFormat;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import shoe.store.util.AppConstants;

@Entity
@Getter @Setter
@Table(name = "product")
public class Product extends BaseEntity {

	private static final long serialVersionUID = 7607672178815865696L;

	public static final String OUT_OF_STOCK 	= "Hết hàng";
	public static final String SELLING 			= "Còn hàng";

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "price", nullable = false)
	private Long price;

	@Column(name = "version_name", length = 50)
	private String versionName;

	@Column(name = "status", length = 50)
	private String status = SELLING;

	@Column(name = "description", length = 500)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "is_delete", nullable = false)
	private Boolean isDelete = false;

	@Transient
	private Boolean isWishList = false;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Set<ProductSize> sizes = new HashSet<ProductSize>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Set<ProductColor> colors = new HashSet<ProductColor>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Set<ProductImage> images = new HashSet<ProductImage>(0);

	@JsonProperty
	public String formatPrice() {
		return new DecimalFormat("#,###").format(price).replace(",", ".") + " ₫";
	}

	@JsonProperty
	public String primaryImage() {
		for (ProductImage image : images) {
			if (image.getIsPrimary()) {
				 return AppConstants.PRODUCT_RESOURCE_HANDLE + image.getPath();
			}
		}
		if (!images.isEmpty()) return AppConstants.PRODUCT_RESOURCE_HANDLE + images.iterator().next().getPath();
		else return AppConstants.NONE_IMG;
	}

	@JsonProperty
	public List<String> otherImages() {
		List<String> otherImages = new ArrayList<String>();
		for (ProductImage image : images) {
			if (!image.getIsPrimary()) {
				otherImages.add(AppConstants.PRODUCT_RESOURCE_HANDLE + image.getPath());
			}
		}
		return otherImages;
	}

	@JsonProperty
	public String name2() {
		return brand.getName() + " " + name + " - " + versionName;
	}

	@JsonProperty
	public String test(String txt) {
		return txt;
	}

}
