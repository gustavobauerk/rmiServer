package principal;

import interfaces.InterfaceCliente;
import interfaces.InterfaceServ;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InterfaceServImpl extends UnicastRemoteObject implements InterfaceServ {
    protected InterfaceServImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Trip> searchAirfare(Trip query) throws RemoteException {
        List<Trip> result = new ArrayList<>();
        Random rand = new Random();
        int n = rand.nextInt(50) + 1;
        query.setNumberOfAirfares(n);
        result.add(query);
        return result;
    }

    @Override
    public void buyAirfare(Trip query) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerInterest(String event, InterfaceCliente client, String destination, Integer price) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelRegister(String event, InterfaceCliente client, String destination, Integer price) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Hotel> searchHotel(Hotel query) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buyHotel(Hotel query) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
