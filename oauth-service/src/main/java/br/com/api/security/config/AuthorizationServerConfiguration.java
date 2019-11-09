package br.com.api.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import br.com.api.security.service.MyUserDetailService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
	private TokenStore tokenStore = new InMemoryTokenStore();
	
	@Autowired
	@Qualifier(value = "authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailService detailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Value("${spring.security.oauth2.client-id}")
	private String clientId;
	
	@Value("${spring.security.oauth2.client-secret}")
	private String secret;
	
	@Value("${spring.security.oauth2.resource-id}")
	private String resourceId;
	
	@Value("${spring.security.oauth2.refresh-token-seconds}")
	private int refreshTokenTimeSeconds;
	
	@Value("${spring.security.oauth2.access-token-seconds}")
	private int acessTokenTimeSeconds;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpointConfigurer) throws Exception {
		endpointConfigurer.tokenStore(this.tokenStore)
			.authenticationManager(this.authenticationManager)
			.userDetailsService(this.detailService);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory()
			.withClient(clientId)
			.authorizedGrantTypes("password", "authorization_code", "refresh_token")
			.scopes("all")
			.refreshTokenValiditySeconds(refreshTokenTimeSeconds)
			.resourceIds(resourceId)
			.secret(passwordEncoder.encode(secret))
			.accessTokenValiditySeconds(acessTokenTimeSeconds);
	}
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setTokenStore(this.tokenStore);
		
		return tokenServices;
	}

}
