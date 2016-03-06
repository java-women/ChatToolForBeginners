const EMPTY_NAME = "ななし";

/**
 * 初期化処理
 */
var HelloStomp = function () {
    this.connectButton = document.getElementById('connect');
    this.disconnectButton = document.getElementById('disconnect');
    this.sendButton = document.getElementById('send');

    // イベントハンドラの登録
    this.connectButton.addEventListener('click', this.connect.bind(this));
    this.disconnectButton.addEventListener('click', this.disconnect.bind(this));
    this.sendButton.addEventListener('click', this.sendName.bind(this));
};

/**
 * エンドポイントへの接続処理
 */
HelloStomp.prototype.connect = function () {
    var socket = new WebSocket('ws://' + location.host + '/endpoint'); // エンドポイントのURL
    this.stompClient = Stomp.over(socket); // WebSocketを使ったStompクライアントを作成
    this.stompClient.connect({}, this.onConnected.bind(this)); // エンドポイントに接続し、接続した際のコールバックを登録
};

/**
 * エンドポイントへ接続したときの処理
 */
HelloStomp.prototype.onConnected = function (frame) {
    console.log('Connected: ' + frame);
    // 宛先が'/topic/messages'のメッセージを購読し、コールバック処理を登録
    this.stompClient.subscribe('/topic/messages', this.onSubscribeGreeting.bind(this));
    this.setConnected(true);
    
    var name = document.getElementById('name').value;
    if (!name) name = EMPTY_NAME;
    this.stompClient.send('/app/connect', {}, name);
};

/**
 * 宛先'/topic/messages'なメッセージを受信したときの処理
 */
HelloStomp.prototype.onSubscribeGreeting = function (message) {
	
	var name = document.getElementById('name').value;
    if (!name) name = EMPTY_NAME;
	
    var response = document.getElementById('response');

    var p = document.createElement('p');
    p.appendChild(document.createTextNode(name.body));
    
    var p = document.createElement('p');
    p.classList.add('left_balloon');
    p.appendChild(document.createTextNode(message.body));
    response.appendChild(p);
    
    /** スクロールを一番下に **/
    var scrollHeight = document.getElementById("response").scrollHeight;
    document.getElementById("response").scrollTop = scrollHeight;
};

/**
 * 宛先'/app/message'へのメッセージ送信処理
 */
HelloStomp.prototype.sendName = function () {
    var message = document.getElementById('message').value;
    this.stompClient.send('/app/message', {}, message);  // 宛先'/app/message'へメッセージを送信
};

/**
 * 接続切断処理
 */
HelloStomp.prototype.disconnect = function () {
    if (this.stompClient) {
        this.stompClient.disconnect();
        this.stompClient = null;
    }
    this.setConnected(false);
};

/**
 * ボタン表示の切り替え
 */
HelloStomp.prototype.setConnected = function (connected) {
    this.connectButton.disabled = connected;
    this.disconnectButton.disabled = !connected;
    this.sendButton.disabled = !connected;
};

new HelloStomp();