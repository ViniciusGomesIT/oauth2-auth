package br.com.api.security.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.api.entity.Role;
import br.com.api.entity.User;
import br.com.api.security.model.JwtUser;

public class JwtUserFactory {
	
	private JwtUserFactory() {
		
	}
	
	public static JwtUser create(User user) {
		return new JwtUser(String.valueOf(user.getId()), user.getEmail(), user.getPassword(), mapToGrantedAuthorities(user.getRoles()));
		
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		
		return authorities;
	}
}
