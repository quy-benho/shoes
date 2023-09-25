package shoe.store.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shoe.store.entity.BaseEntity;
import shoe.store.payload.Meta;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse<T extends BaseEntity> {

	private Meta meta;
	private List<T> data;

}
