package javajo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import javajo.form.ChatForm;

/**
 * チャットコントローラークラスです
 */
@RestController
public class ChatController {
	
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
	
    /**
     * メッセージを受付し、/topic/messagesにメッセージを送信するメソッドです
     * @param chatForm 接続者名とメッセージ(json形式)
     * @return chatForm 接続者名とメッセージ(json形式)
     */
    @MessageMapping(value = "/message" /* 宛先名 */)
    @SendTo(value = "/topic/messages") // 処理結果の送り先
    ChatForm greet(ChatForm chatForm) {
        log.info("received {}", chatForm.getMessage());
        return chatForm;
    }

    @MessageMapping(value = "/connect" )
    @SendTo(value = "/topic/messages") // 処理結果の送り先
    String connect(String name) {
        log.info("connect {}", name);
        return name + "さんが接続しました。";
    }
}
