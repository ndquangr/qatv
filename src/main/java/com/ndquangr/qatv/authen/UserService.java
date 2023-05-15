package com.ndquangr.qatv.authen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * User service for spring security, authenticates the user
 *
 */
public class UserService implements UserDetailsService {

	/**
	 * load user for authentication and authority
	 */
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {

		/*com.a2m.htmlconverter.model.User user = null;
		try {
			user = UserDao.getInstance().getByUserName(username);

		} catch (Exception e) {
			CommonUtils.doLog(getClass()).error(e.getMessage());
		}*/
		Object user = null;
		if (user == null) {
			throw new AuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		}

		String role[] = {"ROLE_USER", "ROLE_MOD", "ROLE_ADMIN"};

		/*switch (Integer.valueOf(String.valueOf(user.getRoles()))) {
			case 0 : // normal user
				role[1] = role[2] = null;
				break;
			case 1 : // mod user
				role[2] = null;
				break;
			case 2 : // admin user
				break;
			default :
				role[1] = role[2] = null;
				break;
		}*/

		List<GrantedAuthority> authorities = buildUserAuthority(role);
		//return buildUserForAuthentication(user, authorities);
		return null;
	}

	/**
	 * Build Sptring security user
	 * 
	 * @param user
	 * @param authorities
	 * @return
	 */
	/*private User buildUserForAuthentication(
			com.a2m.htmlconverter.model.User user,
			List<GrantedAuthority> authorities) {
		return new User(user.getUserName(), user.getPassword(),
				!user.getDeleteFlag(), true, true, true, authorities);

	}*/

	/**
	 * Build list user authority
	 * 
	 * @param userRoles
	 * @return
	 */
	private List<GrantedAuthority> buildUserAuthority(String... userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (String userRole : userRoles) {
			if (userRole != null)
				setAuths.add(new SimpleGrantedAuthority(userRole));
		}

		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(
				setAuths);

		return result;
	}

}