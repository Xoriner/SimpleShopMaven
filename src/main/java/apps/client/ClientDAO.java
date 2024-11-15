package apps.client;

import apps.seller.Offer;
import java.util.List;

public interface ClientDAO {
    //functions for clients table
    public void addClient(Client client);

    //functions for offers table
    public List<Offer> getAllOffers();

    public void displayAllOffers();

    //functions for orders table


}
