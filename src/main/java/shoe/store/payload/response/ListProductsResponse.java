package shoe.store.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import shoe.store.entity.Product;
import shoe.store.payload.Meta;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListProductsResponse {

	private Meta meta;
	private List<Product> data;

}
