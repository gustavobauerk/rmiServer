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
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
        } catch (AlreadyBoundException e) {
            System.out.println("Fukeq");
        }
    }

    public static void main(String[] args) {
        System.out.println("asd");
        System.setProperty("java.security.policy", "file:java.policy");
        Server server = new Server();
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        int i =0;
        while (flag) {
            String entrada = scan.nextLine();
            if (entrada.equals("teste")) {
            }
            if (entrada.equalsIgnoreCase("sair")) {
                break;
            }
            System.out.println(i);
            i++;
        }
    }

}
