package present;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;


public class PresentationV2 {
    public static void main(String[] args) {
        try{
            Scanner sc = new Scanner(new File("config.txt"));
            String daoClassName = sc.nextLine();
            Class cDao = Class.forName(daoClassName);
            IDao Idao = (IDao) cDao.getConstructor().newInstance();

            String metierClassName = sc.nextLine();
            Class cMetier = Class.forName(metierClassName);
            //utiliser le constructeur sans paramètre
            //IMetier Imetier = (IMetier) cMetier.getConstructor().newInstance();

            //utiliser la méthode setIDao pour l'injection des dépendances
            //Method setIdao = cMetier.getDeclaredMethod("setIdao", IDao.class);
            //setIdao.invoke(Imetier, Idao);

            //utiliser directement le constructeur avec paramètres pour DI
            IMetier Imetier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(Idao);
            System.out.println("Temperature Constructeur : "+Imetier.calcul()+" °C");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

