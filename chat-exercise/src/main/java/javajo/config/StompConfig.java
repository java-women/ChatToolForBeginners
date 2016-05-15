package javajo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * STOMP用のコンフィグクラス
 */
@Configuration
@EnableWebSocketMessageBroker // WebSocketに関する設定クラス
public class StompConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("endpoint"); // WebSocketのエンドポイント
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app"); // Controllerに処理させる宛先のPrefix
		registry.enableSimpleBroker("/topic"); // queueまたはtopicを有効にする(両方可)。queueは1対1(P2P)、topicは1対多(Pub-Sub)
	}
}
