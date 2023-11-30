package system.socket;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerMainThread implements Runnable {
    private ServerSocket serverSocket = null;
    private Text algorithm = null;
    private TextArea ciphertext = null;
    private TextArea key = null;
    private boolean threadStop = false;

    public void setThreadStop(boolean threadStop) {
        this.threadStop = threadStop;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setAlgorithm(Text algorithm) {
        this.algorithm = algorithm;
    }

    public void setCiphertext(TextArea ciphertext) {
        this.ciphertext = ciphertext;
    }

    public void setKey(TextArea key) {
        this.key = key;
    }

    public SocketServerMainThread() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        try {
            while (true) {
                //有阻塞--客户端连接上来
                System.out.println("正在监听端口是否有连接请求");
                Socket client;
                client = serverSocket.accept();
                System.out.println(client.getRemoteSocketAddress().toString()+"连接成功\n");
                //创建一个单独的线程处理与客户端的交互
                SocketServerForClient ssfc = new SocketServerForClient(client,algorithm,ciphertext,key);
                new Thread(ssfc).start();
                if(threadStop){
                    ssfc.setThreadStop(true);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


