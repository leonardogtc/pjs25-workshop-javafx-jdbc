package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        String sql = null;
        try {
            sql = "INSERT INTO seller (\"Name\", \"Email\", \"BirthDate\", \"BaseSalary\", \"DepartmentId\") " +
                    "VALUES (?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id); // Set the generated ID to the Seller object
                }
                DB.closeResultSet(rs);

            } else {
                throw new DbException("Unexpected error! No rows affected.");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        String sql = null;
        try {
            sql = "UPDATE public.seller " +
                    "SET \"Name\"=?, \"Email\"=?, \"BirthDate\"=?, \"BaseSalary\"=?, \"DepartmentId\"=? " +
                    "WHERE \"Id\"=?;";
            st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(int id) {
        PreparedStatement st = null;
        String sql = null;
        try {
            sql = "DELETE FROM seller WHERE \"Id\" = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            sql = "SELECT seller.*, department.Name as DepName" +
                    "FROM seller INNER JOIN department" +
                    "ON seller.department_id = department.id" +
                    "WHERE seller.Id = ?";

            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {

                // Assuming Department is another entity with a constructor that takes id and
                // name
                // You would need to implement the Department class accordingly
                Department dep = instantiateDepartment(rs);

                Seller obj = instantiateSeller(rs, dep);

                return obj;
            } else {
                return null; // No seller found with the given id
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("name"));
        seller.setEmail(rs.getString("email"));
        seller.setBirthDate(new java.util.Date(rs.getTimestamp("birth_date").getTime()));
        seller.setBaseSalary(rs.getDouble("base_salary"));
        seller.setDepartment(dep);
        return seller;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("department_id"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            // sql = "SELECT seller.*, department.\"Name\" as DepName " +
            //         "FROM seller INNER JOIN department " +
            //         "ON seller.\"DepartmentId\" = department.\"Id\" " +
            //         "ORDER BY department.\"Name\"";

            sql = "SELECT seller.*, department.name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.department_id = department.id " +
                    "ORDER BY department.name";

            st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("department_id"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("department_id"), dep);
                }

                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            sql = "SELECT seller.*, department.\"Name\" as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.\"DepartmentId\" = department.\"ID\" " +
                    "WHERE seller.\"DepartmentId\" = ? " +
                    "ORDER BY department.\"Name\"";

            st = conn.prepareStatement(sql);
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}