package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Avion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AvionNit extends Thread {
    @Override
    public void run() {
        super.run();
        proveriPoletanjeAviona(avion);
    }

    static Dao<Avion,Integer> avionDao;
    private Avion avion;

    public Avion getAvion() {
        return avion;
    }
    public AvionNit(Avion avion){
        this.avion = avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }
    public static  Boolean dozvoljenoPoletanje = true;
    public static final Object avionSinhronizacija =new Object();
    public static void main(String[] args) {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:avionRoba.db");
        avionDao = DaoManager.createDao(connectionSource, Avion.class);
        List<Avion> avioni = avionDao.queryForAll();
        ArrayList<AvionNit> avioniNiti =  new ArrayList<AvionNit>();

        for (Avion j : avioni)
        {
            AvionNit trenutniAvion = new AvionNit(j);
            avioniNiti.add(trenutniAvion);
        }

            for (AvionNit j : avioniNiti)
            {
//               j.getAvion().getId();
//                proveriPoletanjeAviona(j.getAvion());
                j.start();
            }
            for (AvionNit j : avioniNiti)
            {
//               j.getAvion().getId();
//                proveriPoletanjeAviona(j.getAvion());
                j.join();
            }
            System.out.println("Svi su poleteli");




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
public  void proveriPoletanjeAviona(Avion avion){
    System.out.println("Pocinju provere za avion "+avion.getId());
            boolean moguPoleteti = false;
            try {
                sleep(new Random().nextInt(2)*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    System.out.println("Avion "+avion.getId()+" je spreman i ceka na poletanje");
        while (!moguPoleteti){
            synchronized (avionSinhronizacija){

                if (!dozvoljenoPoletanje){
                    moguPoleteti = false;
                }
                else {
                    moguPoleteti = true;
                    dozvoljenoPoletanje = false;
                }
            }
        }
    System.out.println("Avion "+ avion.getId()+" izlazi na poletanje");
    try {
        sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("Avion "+avion.getId()+" je poleteo");
    synchronized (avionSinhronizacija){

        dozvoljenoPoletanje = true;
    }

}

}
