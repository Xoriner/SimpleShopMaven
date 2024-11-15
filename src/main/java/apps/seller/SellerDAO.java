package apps.seller;

import java.util.List;

public interface SellerDAO {

    //functions for offers table

    public List<Offer> getAllOffers();

    public Offer getOffer(int id);

    public void addOffer(Offer offer);

    public void updateOffer(Offer offer);

    public void deleteOffer(Offer offer);


    //functions for sellers table

}
