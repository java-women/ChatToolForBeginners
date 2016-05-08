package javajo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 動作確認用にHello Worldを出力するコントローラー
 */
@RestController
public class HelloController {
	
    @RequestMapping(value = "/")
    String hello() {
        return "Hello World!";
    }

}
