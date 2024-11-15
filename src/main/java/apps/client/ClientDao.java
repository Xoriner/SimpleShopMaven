package apps.client;

import apps.seller.Offer;
import java.util.List;

public interface ClientDao {
    //functions for clients table
    public void addClient(Client client);

    //functions for offers table
    public List<Offer> getAllOffers();
}
