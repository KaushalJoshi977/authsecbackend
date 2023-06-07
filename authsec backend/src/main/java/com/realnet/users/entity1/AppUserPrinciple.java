package com.realnet.users.entity1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realnet.users.entity.Role;

public class AppUserPrinciple implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AppUser u;
	public AppUserPrinciple(AppUser u) {
		super();
		this.u = u;
	}
	
	
//	private String username;
//	@JsonIgnore
//	private String password;
//	private String role;
//
//	public AppUserPrinciple( String username, String password,String role
//			) {
//		this.username = username;
//		this.password = password;
//		this.role = role;
//	}

	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_"+u.getUsrGrp().getGroupName()));
////		u.getUsrGrp().forEach(role->{
////			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getGroupName()));
////		});
//		return authorities;
		
		//FROM GK
//				List<SimpleGrantedAuthority> authorities = u.getRoles().stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//				 authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
//				return  authorities;
		
		 Set<Role> roles = u.getRoles();
	        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	         
	        for (Role role : roles) {
//	        	authorities.add(new SimpleGrantedAuthority(role.getName().getDeclaringClass().getName()));
	            authorities.add(new SimpleGrantedAuthority(role.getName()));
	        }
	         
	        return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return u.getUserPassw();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return u.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
