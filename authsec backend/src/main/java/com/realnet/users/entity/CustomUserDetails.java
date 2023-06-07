//package com.realnet.users.entity;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.realnet.users.entity1.AppUser;
//
//import lombok.ToString;
//
//@ToString
//public class CustomUserDetails implements UserDetails {
//	private static final long serialVersionUID = 3163073361116831556L;
//	
//	private AppUser user;
////	public User getUser() {
////		return user;
////	}
////	public void setUser(User user) {
////		this.user = user;
////	}
//
//	public CustomUserDetails(AppUser user) {
//		super();
//		this.user = user;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//		user.getRoles().forEach(role -> {
//			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//		});
//		return authorities;
//	}
//
//	@Override
//	public String getPassword() {
//		return user.getPassword();
//	}
//
//	@Override
//	public String getUsername() {
//		//return user.getUsername();
//		return user.getEmail();
//	}
//
//	@JsonIgnore
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@JsonIgnore
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@JsonIgnore
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@JsonIgnore
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//
//}
