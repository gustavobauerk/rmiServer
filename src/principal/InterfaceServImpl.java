package principal;

import interfaces.InterfaceCliente;
import interfaces.InterfaceServ;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InterfaceServImpl extends UnicastRemoteObject implements InterfaceServ {
    private List<Trip> trips = new ArrayList<>();
    private List<String> places = new ArrayList<>();

    protected InterfaceServImpl() throws RemoteException {
        places.add("Curitiba");
        places.add("Londres");
        places.add("Paris");
        places.add("Boston");
        geraTrips();
    }

    @Override
    public List<Trip> searchAirfare(Trip query) throws RemoteException {
        List<Trip> result = new ArrayList<>();
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

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    private void geraTrips() {
        Random rand = new Random();
        int aux, aux1;
        for (int i = 0 ; i < 25 ; i++) {
            Trip trip = new Trip();
            trip.setNumberOfAirfares(rand.nextInt(50) + 250);
            trip.setPrice(rand.nextInt(1000) + 200);
            aux = rand.nextInt(places.size());
            trip.setSource(places.get(aux));
            do {
                aux1 = rand.nextInt(places.size());
            } while (aux1 == aux);
            trip.setDestination(places.get(aux1));
            trip.setDate(geraDate());
            this.trips.add(trip);
        }
    }

    private LocalDate geraDate() {
        Random rand = new Random();
        LocalDate result = LocalDate.of(2018, rand.nextInt(3) + 10, rand.nextInt(28) + 1);
        return result;
    }

}
