package javajo.form;

import javax.validation.constraints.NotNull;

/**
 * チャット機能のformクラス
 */
public class ChatForm {
	
	@NotNull
	private String name;
	
	@NotNull
	private String message;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
