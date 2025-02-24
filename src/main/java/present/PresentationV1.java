package present;

import dao.IDaoImpl;
import metier.IMetierImpl;

public class PresentationV1 {
    public static void main(String[] args) {
        IDaoImpl dao = new IDaoImpl();
        //DI avec le setter
        //IMetierImpl metier = new IMetierImpl();
        //metier.setIdao(dao);
        //DI avec le constructeur
        IMetierImpl metierCons = new IMetierImpl(dao);
        //System.out.println("Temperature : "+ metier.calcul() +" °C");
        System.out.println("Temperature constructeur : "+ metierCons.calcul() +" °C");
    }
}

