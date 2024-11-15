package apps.seller;

import apps.organizer.Event;

import java.util.List;

public interface SellerDAO {

    //functions for offers table

    public List<Offer> getAllOffers();

    void displayAllOffers();

    public Offer getOffer(int id);

    public void addOffer(Event event, Seller seller);

    public void updateOffer(Offer offer);

    public void deleteOffer(int ask_id);


    //functions for sellers table
    public void addSeller(Seller seller);
}
