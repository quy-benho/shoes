package shoe.store.util;

public class Validator {

	public static boolean checkId(String id) {
		return id != null && id.matches("[0-9]+");
	}

	public static String checkFilterId(String data) {

		if (data == null || data.trim().length() == 0 || !data.matches("[0-9]+")) {
			data = "%%";
		}
		return data;
	}

	public static long checkFilterPrice(String price, boolean isMin) {
		if (price == null || price.trim().length() == 0 || !price.matches("[0-9]+")) {
			return isMin ? 0L : Long.MAX_VALUE;
		}
		
		return Long.valueOf(price);
	}

}
