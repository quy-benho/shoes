package shoe.store.util;

import java.io.File;

public interface AppConstants {

    File IMAGE_DIR = new File("image");

    String IMAGE_RESOURCE_HANDLE = "/image";

    String PRODUCT_RESOURCE_HANDLE = IMAGE_RESOURCE_HANDLE + "/product/";

    File PRODUCT_IMAGE_DIR = new File(IMAGE_DIR.getName() + File.separator + "product");

    String NONE_IMG = "images/product/none.jpg";
}
