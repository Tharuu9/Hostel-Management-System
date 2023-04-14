package lk.ijse.hostel.dao.custom.impl;

import lk.ijse.hostel.dao.custom.UserDAO;
import lk.ijse.hostel.entity.UserLogin;
import lk.ijse.hostel.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public List<UserLogin> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM userlogin";
        Query query = session.createQuery(hql);
        List<UserLogin> userLogins = query.getResultList();

        transaction.commit();
        session.close();
        return userLogins;
    }
    @Override
    public boolean save(UserLogin entity) throws IOException {
        System.out.println("ENt : "+entity);
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(UserLogin entity) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String s) throws IOException {
        return false;
    }

    @Override
    public boolean find(String s) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public String generateNewID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List resultList = session.createSQLQuery("SELECT * FROM UserLogin ORDER BY userId DESC LIMIT 1").addEntity(UserLogin.class).getResultList();
        transaction.commit();
        session.close();
        return resultList.size()==0?"U000":((UserLogin)resultList.get(0)).getUserId();
    }

    @Override
    public UserLogin search(String s) throws SQLException, ClassNotFoundException, IOException {
        System.out.println("String S : "+s);
        Session session = FactoryConfiguration.getInstance().getSession();
        UserLogin user = session.find(UserLogin.class , s);
        session.close();
        return user;
    }
}
