package principal;

import interfaces.InterfaceCliente;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servidor
 */
public class Server {
    private InterfaceServImpl interf;
    private static InterfaceCliente cli;

    /**
     * Inicia servidor
     */
    public Server() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Registry servicoNomes;
        try {
            interf = new InterfaceServImpl();
            servicoNomes = LocateRegistry.createRegistry(10000);
            servicoNomes.bind("Server", interf);
        } catch (RemoteException | AlreadyBoundException e) {
            System.out.println("Fukeq");
        }
    }

    public static void main(String[] args) {
        System.setProperty("java.security.policy", "file:java.policy");
        Server server = new Server();
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            String entrada = scan.nextLine();
            if (entrada.equalsIgnoreCase("sair")) {
                break;
            }
            if(entrada.equals("a")){
                try {
                    cli.notificar("asd");
                } catch (RemoteException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
