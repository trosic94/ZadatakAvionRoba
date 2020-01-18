package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Roba;

import java.io.IOException;
import java.util.List;

public class Zadatak3IzmenaVrednosti {
    static Dao<Roba,Integer> robaDao;
    public static void main(String[] args) {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:avionRoba.db");
            robaDao = DaoManager.createDao(connectionSource, Roba.class);

            List<Roba> roba = robaDao.queryForAll();
            for (Roba j : roba)
                System.out.println(j);


            List<Roba> robaIzmena = robaDao.queryForEq(Roba.POLJE_OPIS,"Plasticna stolica");

            for (Roba j : robaIzmena)
            {
                j.setOpis("Drvena stolica");
                robaDao.update(j);
            }
            roba = robaDao.queryForAll();
            for (Roba j : roba)
                System.out.println(j);

            List<Roba> robaBrisanje = robaDao.queryForEq(Roba.POLJE_NAZIV,"Voda");

            for (Roba j : robaBrisanje)
            {
                robaDao.delete(j);
            }

            roba = robaDao.queryForAll();
            for (Roba j : roba)
                System.out.println(j);


        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
