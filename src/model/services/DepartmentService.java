package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    /**
     * Finds a Department by its ID.
     *
     * @param id the ID of the Department to find
     * @return the Department object if found, or null if not found
     */
    public List<Department> findAll() {
        return dao.findAll();
    }

    /**
     * Inserts a new Department or updates an existing one.
     *
     * @param obj the Department object to be saved or updated
     */
    public void saveOrUpdate(Department obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Department obj) {
        dao.deleteById(obj.getId());
    }
}
