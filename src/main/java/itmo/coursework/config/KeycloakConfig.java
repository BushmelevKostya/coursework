//package itmo.coursework.config;
//
//import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class KeycloakConfig {
//
//	@Bean
//	public SecurityFilterChain securityFilterChainConfig(HttpSecurity http) throws Exception {
//		http
//				.cors(cors -> cors.configurationSource(request -> {
//					var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
//					corsConfiguration.addAllowedOrigin("http://localhost:4200");
//					corsConfiguration.addAllowedMethod("*");
//					corsConfiguration.addAllowedHeader("*");
//					corsConfiguration.setAllowCredentials(true);
//					return corsConfiguration;
//				}))
//				.authorizeHttpRequests(authz -> authz
//						.requestMatchers("/api/events/**").permitAll()
//						.anyRequest().authenticated()
//				)
//				.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
//
//		return http.build();
//	}
//}
