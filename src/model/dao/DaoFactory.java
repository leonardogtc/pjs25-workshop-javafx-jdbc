package model.dao;

import db.DB;

public class DaoFactory {

	public static DepartmentDao createDepartmentDao() {
		return new model.dao.impl.DepartmentDaoJDBC(DB.getConnection());
	}

    public static SellerDao createSellerDao() {
        return new model.dao.impl.SellerDaoJDBC(DB.getConnection());
    }

}