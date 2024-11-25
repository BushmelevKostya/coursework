package itmo.coursework.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class KeycloakConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChainConfig(
			HttpSecurity http, Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter)
			throws Exception {
		http
				.cors(cors -> cors.configurationSource(request -> {
					var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
					corsConfiguration.addAllowedOrigin("http://localhost:4200");
					corsConfiguration.addAllowedMethod("*");
					corsConfiguration.addAllowedHeader("*");
					corsConfiguration.setAllowCredentials(true);
					return corsConfiguration;
				}))
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						httpRequests -> httpRequests
								.requestMatchers("/api/v1/ws").permitAll()
								.anyRequest().authenticated()
				)
				.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS))
				.oauth2ResourceServer(
						resourceServer -> resourceServer.jwt(
								jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)
						)
				);
		
		return http.build();
	}
}