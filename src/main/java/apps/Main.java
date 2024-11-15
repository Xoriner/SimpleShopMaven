package apps;

import apps.seller.DAOImpl;
import apps.seller.Offer;

public class Main {
    public static void main(String[] args) {
        DAOImpl dao = new DAOImpl();
        dao.addOffer(new Offer("Balon", "10pm", "available"));
        dao.deleteOffer(dao.getOffer(1));
    }
}