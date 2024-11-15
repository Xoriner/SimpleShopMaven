package apps.seller;

import apps.organizer.Event;

import java.util.List;

public interface SellerDAO {

    //functions for offers table

    public List<Offer> getAllOffers();

    void displayAllOffers();

    public Offer getOffer(int id);

    public void addOffer(Event event);

    public void updateOffer(Offer offer);

    public void deleteOffer(Offer offer);


    //functions for sellers table
    public void addSeller(Seller seller);
}
