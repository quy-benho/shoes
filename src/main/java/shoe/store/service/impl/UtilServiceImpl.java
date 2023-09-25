package shoe.store.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shoe.store.service.UtilService;

@Service
public class UtilServiceImpl implements UtilService {

	public String formatPrice(long price) {
		return new DecimalFormat("#,###").format(price).replace(",", ".") + " ₫";
	}

	public <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	    List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c)
	      r.add(clazz.cast(o));
	    return r;
	}

//	@PersistenceContext
//    private EntityManager entityManager;
//
//	@Value("${spring.datasource.url}")
//	String url;
	
	@Override
	@Transactional
	public void alterUtf8() {
		
//		String mySqlDbName = url.substring(0, url.indexOf("?")==-1?url.length():url.indexOf("?")).substring(url.lastIndexOf("/")+1);
//		
//		Query query = entityManager.createNativeQuery
//				("SELECT CONCAT('ALTER TABLE ', TABLE_SCHEMA, '.', TABLE_NAME,\" COLLATE 'utf8mb4_unicode_ci', CONVERT TO CHARSET utf8mb4;\") AS ExecuteTheString "
//						+ "FROM INFORMATION_SCHEMA.TABLES "
//						+ "WHERE TABLE_SCHEMA = ?1 "
//						+ "AND TABLE_TYPE='BASE TABLE'");
//		query.setParameter(1, mySqlDbName);
//
//		List<String> queries = castList(String.class, query.getResultList());
//		queries.forEach(alter -> {
//			entityManager.createNativeQuery(alter).executeUpdate();
//		});
//		System.out.println("Alter utf8 successful");
	}

}
