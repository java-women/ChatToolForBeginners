package javajo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import javajo.App;

/**
 * チャットコントローラークラス
 */
@RestController
public class ChatController {
	
    private static final Logger log = LoggerFactory.getLogger(App.class);
	

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
