package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {

    private SellerDao dao = DaoFactory.createSellerDao();

    /**
     * Finds a Seller by its ID.
     *
     * @param id the ID of the Seller to find
     * @return the Seller object if found, or null if not found
     */
    public List<Seller> findAll() {
        return dao.findAll();
    }

    /**
     * Inserts a new Seller or updates an existing one.
     *
     * @param obj the Seller object to be saved or updated
     */
    public void saveOrUpdate(Seller obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    /**
     * Removes a Seller by its ID.
     *
     * @param obj the Seller object to be removed
     */
    public void remove(Seller obj) {
        dao.deleteById(obj.getId());
    }
}
