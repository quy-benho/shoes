package shoe.store.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import shoe.store.controller.admin.DetailsAccountInterceptor;
import shoe.store.util.AppConstants;

@Configuration
@EnableJpaAuditing
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private DetailsAccountInterceptor detailsAccountInterceptor;

    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
	        .addResourceHandler(AppConstants.IMAGE_RESOURCE_HANDLE + "/**")
	        .addResourceLocations("file:" + AppConstants.IMAGE_DIR.getAbsolutePath() + File.separator)
	        .setCachePeriod(0);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(detailsAccountInterceptor).addPathPatterns("/admin/**");
	}
}
