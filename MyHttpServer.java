
/**
 * @author Goncalo Pinto nº58178
 * @author Pedro Pilo nº58179
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe representativa das interacoes entre um servidore um clente
 */
public class MyHttpServer {
    private static ServerSocket serverSocket; // socket do server com a sua respetiva porta
    public static List<threaded> clientes = Collections.synchronizedList(new ArrayList<>());

    /**
     * simula as interacoes entre cliente e servidor
     * 
     * @param args numero da porta do servidor
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threaded clientThread = new threaded(clientSocket);
                Thread thread = new Thread(clientThread);
                if (clientes.size() >= 5) {
                    threaded.hasSpace = false;
                    thread.start();
                } else {
                    threaded.hasSpace = true;
                    thread.start();
                    clientes.add(clientThread);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
