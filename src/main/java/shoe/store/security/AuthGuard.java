package shoe.store.security;

public class AuthGuard {

	public static boolean isAuthenticated(UserPrincipal userPrincipal) {
		return userPrincipal != null;
	}

	public static boolean hasManagerPermission(UserPrincipal userPrincipal) {
		if (isAuthenticated(userPrincipal)) {
			if (userPrincipal.getRole().equals(Role.ADMIN) || userPrincipal.getRole().equals(Role.WEB_MANAGER)) {
				return true;
			}
		}
		return false;
	}
}
