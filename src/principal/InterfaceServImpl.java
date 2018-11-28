package principal;

import interfaces.InterfaceCliente;
import interfaces.InterfaceServ;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import stack.LeiaCSV;

/**
 * Implementa os métodos do servidor
 */
public class InterfaceServImpl extends UnicastRemoteObject implements InterfaceServ {

    @Override
    public Trip searchAirfare(boolean ida, String source, String destination, String dateIda, String dateVolta, int passagens) throws RemoteException {
        Trip result = new Trip();
        result = LeiaCSV.searchTrip(ida, source, destination, dateIda, dateVolta, passagens);
        return result;
    }

    @Override
    public boolean buyAirfare(boolean ida, String source, String destination, String dateIda, String dateVolta, int passagens) throws RemoteException {
        boolean result = false;
        try {
            result = LeiaCSV.buyTrip(ida, source, destination, dateIda, dateVolta, passagens);
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    @Override
    public void registerInterest(String event, InterfaceCliente client, String destination, Integer price) throws RemoteException {
        LeiaCSV.registrar(event, client, destination, price);
    }

    @Override
    public void cancelRegister(String event, InterfaceCliente client, String destination, Integer price) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Hotel searchHotel(String name, String flightdate, String flightdateVolta, int numberOfRooms, int numberOfPeople) throws RemoteException {
        Hotel result = new Hotel();
        result = LeiaCSV.searchHotel(name, flightdate, flightdateVolta, numberOfRooms, numberOfPeople);
        return result;
    }

    @Override
    public boolean buyHotel(String name, String flightdate, String flightdateVolta, int numberOfRooms, int numberOfPeople) throws RemoteException {
        boolean result = false;
        try {
            result = LeiaCSV.buyHotel(name, flightdate, flightdateVolta, numberOfRooms, numberOfPeople);
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

}
