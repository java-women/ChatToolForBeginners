package javajo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ルートURLコントロールクラス
 */
@RestController
public class HelloController {
	
    @RequestMapping(value = "/")
    String hello() {
        return "Hello World!";
    }

}
