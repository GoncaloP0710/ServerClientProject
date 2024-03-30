
/**
 * @author Goncalo Pinto nº58178
 * @author Pedro Pilo nº58179
 */
import java.io.*;
import java.net.*;

/**
 * Classe representativa de um cliente HTTP
 */
public class MyHttpClient {

    private Socket sock;
    private PrintWriter wrt;
    private BufferedReader rdr;

    /**
     * Cria um novo cliente e a sua ligacao ao servidor
     * 
     * @param hostName
     * @param portNumber
     * @throws IOException
     */
    public MyHttpClient(String hostName, int portNumber) throws IOException {
        this.sock = new Socket(hostName, portNumber);
        this.wrt = new PrintWriter(sock.getOutputStream());
        this.rdr = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

    /**
     * Envia um pedido GET ao servidor
     * 
     * @param ObjectName url do pedido
     * @throws IOException
     */
    public void getResource(String ObjectName) throws IOException {
        wrt.print("GET /" + ObjectName + " HTTP/1.1\r\n");
        wrt.print("\r\n");
        wrt.flush();
        ler();
    }

    /**
     * Envia um metodo de requisicao POST ao servidor
     * 
     * @param data dados a enviar ao servidor
     * @throws IOException
     */
    public void postData(String[] data) throws IOException {
        wrt.print("POST " + "/simpleForm.html" + " HTTP/1.1\r\n");
        wrt.print("Content-Type: application/x-www-form-urlencoded\r\n");
        wrt.print("Content-Length: " + (String.join("&", data).replace(": ", "=").length() + "\r\n"));
        wrt.print("\r\n");
        wrt.print(String.join("&", data).replace(": ", "="));
        wrt.flush();
        ler();
    }

    /**
     * Envia ao servidor um metodo nao implementado
     * 
     * @param wrongMethodName nome do metodo nao implementado
     * @throws IOException
     */
    public void sendUnimplementedMethod(String wrongMethodName) throws IOException {
        wrt.println(wrongMethodName + " HTTP/1.1\r\n");
        wrt.flush();
        ler();
    }

    /**
     * Envia um pedido GET mal formatado ao servidor
     * 
     * @param type tipo de pedido GET mal formatado
     * @throws IOException
     */
    public void malformedRequest(int type) throws IOException {
        if (type == 1) {
            wrt.print("GET HTTP/1.1\r\n");
            wrt.flush();
            ler();
        } else if (type == 2) {
            wrt.print("GET   /index.html   HTTP/1.1\r\n");
            wrt.flush();
            ler();
        } else if (type == 3) {
            wrt.print("GET /index.html\r\n");
            wrt.flush();
            ler();
        }
    }

    /**
     * Le a resposta enviada pelo servidor
     * 
     * @throws IOException
     */
    public void ler() throws IOException {
        StringBuilder str = new StringBuilder();
        int read = (char) rdr.read();
        while (read != -1 && rdr.ready()) {
            str.append((char) read);
            read = (char) rdr.read();
        }
        if (read != -1) {
            str.append((char) read);
        }
        System.out.println(str.toString());
    }

    /**
     * Termina a ligacao do cliente ao servidor
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        wrt.print("client has closed the conection");
        wrt.close();
        rdr.close();
        sock.close();
    }
}