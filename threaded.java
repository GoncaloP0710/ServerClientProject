
/**
 * @author Goncalo Pinto nº58178
 * @author Pedro Pilo nº58179
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Classe representativa das threads do servidor HTTP
 */
public class threaded implements Runnable {
    private BufferedReader in; // reader da socket do cliente que tem o pedido
    private Socket client; // socket do cliente que tem o pedido
    private PrintWriter wrt; // escreve a resposta na socket do cliente que tem o pedido
    private Boolean isActive; // indica se o cliente tem um pedido ativo
    public static Boolean hasSpace = true;

    /**
     * Cria uma nova thread para um cliente do servidor
     * 
     * @param clientSocket socket de comunicação do lado do cliente
     * @throws IOException
     */
    public threaded(Socket clientSocket) throws IOException {
        this.client = clientSocket;
        this.in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
        this.wrt = new PrintWriter(client.getOutputStream());
        this.isActive = true;
    }

    /**
     * responde a mensagens do cliente
     */
    @Override
    public void run() {
        String clienString; // String com o pedido do cliente
        try {
            while (isActive) {
                clienString = ler();
                if (!hasSpace && !clienString.isEmpty()) {
                    wrt.write("HTTP/1.1 503 Service Unavailable");
                } else {
                    if (clienString.equals("client has closed the conection")) {
                        deactivate();
                        MyHttpServer.clientes.remove(this);
                        System.out.println(MyHttpServer.clientes.size());
                    }
                    System.out.println(clienString);
                    String[] linhaDePedido = clienString.split("\r\n");// Array das linhas do pedido do cliente
                    String[] linhaDePedidoDividida = linhaDePedido[0].split(" ");
                    if (checkGet(linhaDePedido[0])) { // Verifica se o pedido e GET bem formado
                        wrt.print("HTTP/1.1 200 OK\r\n");
                        wrt.print(lerFicheiro());
                    } else if (checkPost(linhaDePedido)) { // Verifica se o pedido e POST bem formado
                        wrt.print("HTTP/1.1 200 OK\r\n");
                        wrt.print(lerFicheiro());
                    } else {
                        if (badGet(linhaDePedidoDividida)) {
                            wrt.print("HTTP/1.1 400 Bad Request\r\n");
                        } else if (linhaDePedidoDividida[0].equals("POST")) {
                            wrt.print("HTTP/1.1 400 Bad Request\r\n");
                        } else {
                            wrt.print("HTTP/1.1 501 Not Implemented\r\n");
                        }
                    }
                }
                wrt.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Le o pedido do cliente enviado ao servidor
     * 
     * @return pedido do cliente
     * @throws IOException
     */
    public String ler() throws IOException {
        StringBuilder str = new StringBuilder();
        int read = (char) in.read();
        while (read != -1 && in.ready()) {
            str.append((char) read);
            read = (char) in.read();
        }
        if (read != -1) {
            str.append((char) read);
        }
        return str.toString();
    }

    /**
     * Desativa este cliente
     */
    private void deactivate() {
        this.isActive = false;
    }

    /**
     * Getter do atributo isActive
     * 
     * @return valor de isActive
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Verifica se um pedido GET esta bem formatado
     * 
     * @param req pedido do cliente
     * @return true se for um pedido GET bem formatado, false caso
     *         contrario
     */
    private boolean checkGet(String req) {
        return req.equals("GET /index.html HTTP/1.1")
                || req.equals("GET /index.html HTTP/1.0")
                || req.equals("GET / HTTP/1.0")
                || req.equals("GET / HTTP/1.1");

    }

    /**
     * Verifica se um pedido GET
     * 
     * @param req
     * @return
     */
    private boolean badGet(String[] req) {
        return (req[0].equals("GET") && req.length != 3)
                || (req[0].equals("GET") && req.length == 3 && !req[1].equals("/index.html")
                        || (req[0].equals("GET") && req.length == 3 && !(req[2].equals("HTTP/1.1"))));
    }

    /**
     * Verifica se um pedido POST esta bem formatado
     * 
     * @param req pedido do cliente
     * @return true se for um pedido POST bem formatado, false caso
     *         contrario
     */
    private boolean checkPost(String[] req) {
        return req[0].equals("POST /simpleForm.html HTTP/1.1")
                && req.length == 5
                && req[1].equals("Content-Type: application/x-www-form-urlencoded")
                && req[2].split(" ").length == 2
                && req[2].split(" ")[1] != "";
    }

    /**
     * Le o conteudo da pagina "index.html"
     * 
     * @return conteudo da pagina
     * @throws FileNotFoundException
     */
    private String lerFicheiro() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("index.html"));
        StringBuilder st = new StringBuilder();
        while (sc.hasNextLine()) {
            st.append(sc.nextLine());
        }
        return st.toString();
    }
}
