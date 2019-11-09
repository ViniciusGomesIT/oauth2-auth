package br.com.api.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.api.entity.User;
import br.com.api.reposiroty.UserRepository;
import br.com.api.security.factory.JwtUserFactory;

@Service
public class MyUserDetailService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public MyUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepository.findByEmailIgnoreCase(email);
		
		if ( userOpt.isPresent() ) {
			return JwtUserFactory.create(userOpt.get());
		} else {
			throw new UsernameNotFoundException( String.format("User with email %s does not exist", email) );
		}
	}
}
