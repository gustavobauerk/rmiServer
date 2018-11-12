package principal;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Server {
    private InterfaceServImpl interf;

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
        }
    }

}
