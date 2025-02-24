package pres;

import dao.DaoImpl;
import ext.DaoImplV2;
import metier.MetierImpl;

public class PresentationV1 {
    public static void main(String[] args) {

        /*
        Injection des dependances par instanciation statique
         */

        DaoImplV2 d = new DaoImplV2();
        MetierImpl metier = new MetierImpl(d); //injection des dependance via le constructeur
        //  metier.setDao(d); //injection des dependance via le setter
        System.out.println(metier.calcul());

    }
}
