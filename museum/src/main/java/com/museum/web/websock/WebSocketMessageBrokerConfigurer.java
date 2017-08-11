package com.museum.web.websock;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.museum.MuseumConstant;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfigurer extends
		AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 添加一个服务端点，来接收客户端的连接。
		registry.addEndpoint(MuseumConstant.WEBSOCK_STOMP_REGISTRYPATH)
				.withSockJS();

	}

	/**
	 * 它重载了 configureMessageBroker() 方法：配置了一个 简单的消息代理。如果不重载，默认case下，会自动配置一个简单的
	 * 内存消息代理，用来处理 "/msg" 为前缀的消息。但经过重载后，消息代理将会处理前缀为 "/msg" 消息。
	 * A4）之外：发送应用程序的消息将会带有 "/app" 前缀，下图展现了 这个配置中的 消息流；
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 客户端接收服务端消息的地址的前缀信息
		config.enableSimpleBroker(MuseumConstant.WEBSOCK_STOMP_CLIENT_ACCEPT_PATH_PREFIXES);
		// 客户端发送消息的地址
		config.setApplicationDestinationPrefixes(MuseumConstant.WEBSOCK_STOPM_CLIEN_SEND_PREFIXES);
	}
}
