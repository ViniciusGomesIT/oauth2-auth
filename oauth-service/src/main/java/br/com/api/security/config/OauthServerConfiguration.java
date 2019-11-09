package br.com.api.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OauthServerConfiguration extends ResourceServerConfigurerAdapter {
	
	@Value("${spring.security.oauth2.resource-id}")
	private String resourceId;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceId);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true).and()
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.antMatchers("/oauth/token").permitAll()
			.anyRequest().fullyAuthenticated();
			
	}
}
