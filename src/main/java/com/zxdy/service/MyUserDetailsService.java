package com.zxdy.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;
	
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(
			String userName) throws UsernameNotFoundException {
		return new MyUserDetailsService.UserDetails(
				userName, userService);
	}

	public static class UserDetails implements
			org.springframework.security.core.userdetails.UserDetails {
		private static final long serialVersionUID = 7093082539616299399L;
		private final String userName;
		private UserService userService;
		private Integer userId;
		private String userIP;

		UserDetails(String userName) {
			this.userName = userName;
		}

		UserDetails(String userName, UserService userService) {
			this.userName = userName;
			this.userService = userService;
		}

		public boolean isEnabled() {
			return true;
		}

		public boolean isCredentialsNonExpired() {
			return true;
		}

		public boolean isAccountNonLocked() {
			return true;
		}

		public boolean isAccountNonExpired() {
			return true;
		}

		public String getUsername() {
			return this.userName;
		}

		public String getPassword() {
			return null;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public String getUserIP() {
			return userIP;
		}

		public void setUserIP(String userIP) {
			this.userIP = userIP;
		}

		public Collection<org.springframework.security.core.GrantedAuthority> getAuthorities() {
			Set<String> roleList = userService.getUserRoles(userName);
			if (roleList == null || roleList.isEmpty()) {
				JSONArray newUser = userService.createUser(userName);
				for (int i = 0; i < newUser.size(); i++) {
					switch (newUser.getJSONObject(i).getInt("ROLEID")) {
					case 0:
						roleList.add("ROLE_SUPERADMIN");
						roleList.add("ROLE_ADMIN");
						roleList.add("ROLE_USER");
						roleList.add("ROLE_VIEWER");
						break;
					case 1:
						roleList.add("ROLE_ADMIN");
						roleList.add("ROLE_USER");
						roleList.add("ROLE_VIEWER");
						break;
					case 2:
						roleList.add("ROLE_USER");
						roleList.add("ROLE_VIEWER");
						break;
					case 9:
						roleList.add("ROLE_VIEWER");
						break;
					case 10:
						roleList.add("ROLE_MATSUSER");
						break;
					}
				}
			}
			List<org.springframework.security.core.GrantedAuthority> authList = new ArrayList<org.springframework.security.core.GrantedAuthority>();
			for (String role : roleList) {
				authList.add(new MyUserDetailsService.UserDetails.GrantedAuthority(
						role));
			}
			return authList;
		}

		private static class GrantedAuthority implements
				org.springframework.security.core.GrantedAuthority {
			private static final long serialVersionUID = 1984291193405165789L;
			private final String role;

			GrantedAuthority(String role) {
				this.role = role;
			}

			public String getAuthority() {
				return this.role;
			}

			@Override
			public String toString() {
				return this.role;
			}
		}
	}
}
