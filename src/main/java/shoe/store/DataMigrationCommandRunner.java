package shoe.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import shoe.store.entity.Brand;
import shoe.store.entity.Category;
import shoe.store.entity.OrderApp;
import shoe.store.entity.OrderAppDetail;
import shoe.store.entity.OrderWeb;
import shoe.store.entity.OrderWebDetail;
import shoe.store.entity.Product;
import shoe.store.entity.ProductSize;
import shoe.store.entity.User;
import shoe.store.repository.BrandRepository;
import shoe.store.repository.CartItemRepository;
import shoe.store.repository.CategoryRepository;
import shoe.store.repository.OrderAppDetailRepository;
import shoe.store.repository.OrderAppRepository;
import shoe.store.repository.OrderWebDetailRepository;
import shoe.store.repository.OrderWebRepository;
import shoe.store.repository.ProductRepository;
import shoe.store.repository.ProductSizeRepository;
import shoe.store.repository.UserRepository;
import shoe.store.security.Role;
import shoe.store.service.UtilService;
import shoe.store.service.OrderWebService;
import shoe.store.service.impl.ProductServiceImpl;
import shoe.store.util.DeliveryStatus;
import shoe.store.util.PaymentMethod;
import shoe.store.util.PaymentStatus;

@Component
public class DataMigrationCommandRunner implements CommandLineRunner {

	private int total = 30;
	private boolean test = true;
	private boolean migratedData = false;
	private boolean showCreatedDataToConsole = false;
	private final Log logger = LogFactory.getLog(getClass());

	private void test() {

	}

	protected void migratedData() {

	}

	private void printf(String format, Object... args) {
		if (showCreatedDataToConsole) System.out.printf(format, args);
	}

	private void println(String text) {
		if (showCreatedDataToConsole) System.out.println(text);
	}

	private void println() {
		if (showCreatedDataToConsole) System.out.println();
	}

	private void finish() {
		println("-CREATE FINISHED-\n");
		if (!showCreatedDataToConsole) System.out.println("DONE");
	}

	private int rand(int number) {
		return ThreadLocalRandom.current().nextInt(number);
	}

	private int rand(int number1, int number2) {
		return ThreadLocalRandom.current().nextInt(number1, number2);
	}

	private boolean randB() {
		return ThreadLocalRandom.current().nextBoolean();
	}

	@Override
	public void run(String... args) throws Exception {
		String strArgs = Arrays.stream(args).collect(Collectors.joining("|"));
		logger.info("Application started with arguments:" + strArgs);
		Object[] arrArgs = Arrays.stream(args).toArray();

		if (arrArgs.length > 0 && arrArgs[0].toString().equals("migrate")) {
			if (arrArgs.length > 1 && arrArgs[1].toString().matches("\\d+")) {
				total = Integer.valueOf(arrArgs[1].toString());
				if (total > 100000) total = 100000;
				if (arrArgs.length > 2 && (arrArgs[2].toString().equals("1") || arrArgs[2].toString().equalsIgnoreCase("true") || arrArgs[2].toString().equalsIgnoreCase("t"))) {
					showCreatedDataToConsole = true;
				}
			}
			migratedData();

		} else if (this.getClass().getResource("").getProtocol().equals("file")) {
			if (System.getProperty("isMigratedData") == null) {
				if (migratedData) migratedData();
				System.setProperty("isMigratedData", "true");

			} else if (test) {
				test();
			}
		}
	}

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductSizeRepository productSizeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private OrderWebRepository orderWebRepository;

	@Autowired
	private OrderWebDetailRepository orderWebDetailRepository;

	@Autowired
	private OrderWebService orderWebService;

	@Autowired
	private OrderAppRepository orderAppRepository;

	@Autowired
	private OrderAppDetailRepository orderAppDetailRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

}
