const EMPTY_NAME = 'ななし';

/**
 * 初期化処理
 */
var ChatStomp = function () {
    this.connectButton = document.getElementById('connect');
    this.disconnectButton = document.getElementById('disconnect');
    this.sendButton = document.getElementById('send');
    this.messageText = document.getElementById('message');

    // イベントハンドラの登録
    this.connectButton.addEventListener('click', this.connect.bind(this));
    this.disconnectButton.addEventListener('click', this.disconnect.bind(this));
    this.sendButton.addEventListener('click', this.sendName.bind(this));
    this.messageText.addEventListener('input', this.setSendableStatus.bind(this));
};

/**
 * エンドポイントへの接続処理
 */
ChatStomp.prototype.connect = function () {
    var socket = new WebSocket('ws://' + location.host + '/endpoint'); // エンドポイントのURL
    this.stompClient = Stomp.over(socket); // WebSocketを使ったStompクライアントを作成
    this.stompClient.connect({}, this.onConnected.bind(this)); // エンドポイントに接続し、接続した際のコールバックを登録
};

/**
 * エンドポイントへ接続したときの処理
 */
ChatStomp.prototype.onConnected = function (frame) {
    console.log('Connected: ' + frame);
    // 宛先が'/topic/messages'のメッセージを購読し、コールバック処理を登録
    this.stompClient.subscribe('/topic/messages', this.onSubscribeGreeting.bind(this));
    this.setConnected(true);
};

/**
 * 受信したメッセージを画面に表示する処理
 */
ChatStomp.prototype.onSubscribeGreeting = function (message) {
    
    // 名前:messageを分解
    
    var response = document.getElementById('response'); 
    var p = document.createElement('p');
    p.classList.add('talk-area');
    response.appendChild(p);
    
    var name_span = document.createElement('span');
    name_span.classList.add('talk-name');
    name_span.appendChild(document.createTextNode(JSON.parse(message.body).name));
    p.appendChild(name_span);
    
    var message_span = document.createElement('span');
    message_span.classList.add('talk-content');

    message_span.appendChild(document.createTextNode(JSON.parse(message.body).message));
    p.appendChild(message_span);
    
    /** スクロールを一番下に **/
    var scrollHeight = document.getElementById('response').scrollHeight;
    document.getElementById('response').scrollTop = scrollHeight;
};

/**
 * 宛先'/app/message'へのメッセージ送信処理
 */
ChatStomp.prototype.sendName = function () {
    var name = document.getElementById('name').value;
    if (!name) name = EMPTY_NAME;
    var json_message = {name: name, message: document.getElementById('message').value};
    this.stompClient.send("/app/message", {}, JSON.stringify(json_message));     
};

/**
* メッセージ入力に応じた送信ボタン表示の切り替え
*/
ChatStomp.prototype.setSendableStatus = function () {
    var message = document.getElementById('message').value || '';
    var connected = this.connectButton.disabled;
    this.canSubmit(connected && message.length > 0);
};

/**
 * 接続切断処理
 */
ChatStomp.prototype.disconnect = function () {
    if (this.stompClient) {
        this.stompClient.disconnect();
        this.stompClient = null;
    }
    this.setConnected(false);
};

/**
 * 有効/無効切り替え
 */
ChatStomp.prototype.setConnected = function (connected) {
    this.connectButton.disabled = connected;
    this.disconnectButton.disabled = !connected;
    this.setSendableStatus();
};

/**
* 送信ボタン有効/無効切り替え
*/
ChatStomp.prototype.canSubmit = function (enabled) {
    this.sendButton.disabled = !enabled;
};

new ChatStomp();