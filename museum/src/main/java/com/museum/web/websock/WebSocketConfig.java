package com.museum.web.websock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.museum.MuseumConstant;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements
		WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub

		registry.addHandler(systemWebSocketHandler(),
				MuseumConstant.WEBSOCKREGISTRYPATH)
				.addInterceptors(new WebSocketInterceptor()).withSockJS();

	}

	@Bean
	public WebSocketHandler systemWebSocketHandler() {
		return new SystemWebSocketHandler();
	}

}
