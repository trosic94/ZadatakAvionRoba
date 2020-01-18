package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Avion;
import model.Roba;

import java.io.IOException;
import java.util.List;

public class Zadatak2DodavanjeVrednosti {
    static Dao<Roba,Integer> robaDao;
    static Dao<Avion,Integer> avionDao;
    public static void main(String[] args) {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:avionRoba.db");
            robaDao = DaoManager.createDao(connectionSource, Roba.class);
            avionDao = DaoManager.createDao(connectionSource, Avion.class);

            Avion avion1 =new Avion("Avion 1",34);
            avionDao.create(avion1);
            Avion avion2 =new Avion("Avion 2",21);
            avionDao.create(avion2);


            Roba roba1 = new Roba ("Patike","Duboke patike",1,avion1);
            robaDao.create(roba1);
            Roba roba2 = new Roba ("Kosulja","Na duge rukave",0.4,avion1);
            robaDao.create(roba2);
            Roba roba3 = new Roba ("Voda","Voda za pice",1.4,avion1);
            robaDao.create(roba3);
            Roba roba4 = new Roba ("Ploce","Drvene ploce",3.4,avion2);
            robaDao.create(roba4);
            Roba roba5 = new Roba ("Stolica","Plasticna stolica",2.4,avion2);
            robaDao.create(roba5);

            List<Roba> roba = robaDao.queryForAll();
            for (Roba j : roba)
                System.out.println(j);

            List<Avion> avioni = avionDao.queryForAll();
            for (Avion j : avioni)
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
