package pres;

import dao.DaoImpl;
import metier.MetierImpl;

public class PresentationV1 {
    public static void main(String[] args) {

        /*
        Injection des dependances par instanciation statique
         */

        DaoImpl d=new DaoImpl();
        MetierImpl metier=new MetierImpl();
        metier.setDao(d);
        System.out.println(metier.calcul());
    }
}
