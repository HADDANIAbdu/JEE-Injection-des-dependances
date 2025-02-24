package present;

import dao.IDaoImpl;
import metier.IMetierImpl;

public class PresentationV1 {
    public static void main(String[] args) {
        IDaoImpl dao = new IDaoImpl();
        IMetierImpl metier = new IMetierImpl();
        metier.setIdao(dao);
        System.out.println("Temperature : "+ metier.calcul() +" °C");
    }
}

