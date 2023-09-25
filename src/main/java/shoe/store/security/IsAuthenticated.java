package shoe.store.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.annotation.Secured;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Secured({ Role.ADMIN, Role.WEB_MANAGER, Role.WEB_STAFF, Role.CUSTOMER }) 
public @interface IsAuthenticated {

}
