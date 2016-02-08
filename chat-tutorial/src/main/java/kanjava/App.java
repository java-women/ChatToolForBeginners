package kanjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.*;

@SpringBootApplication
@RestController
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping(value = "/")
    String hello() {
        return "Hello World!";
    }

    @Configuration
    @EnableWebSocketMessageBroker // WebSocketに関する設定クラス
    static class StompConfig extends AbstractWebSocketMessageBrokerConfigurer {

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

    @MessageMapping(value = "/message" /* 宛先名 */) // Controller内の@MessageMappingアノテーションをつけたメソッドが、メッセージを受け付ける
    @SendTo(value = "/topic/messages") // 処理結果の送り先
    String greet(String message) {
        log.info("received {}", message);
        return message;
    }

    @MessageMapping(value = "/connect" )
    @SendTo(value = "/topic/messages") // 処理結果の送り先
    String connect(String name) {
        log.info("connect {}", name);
        return name + "さんが接続しました。";
    }
}