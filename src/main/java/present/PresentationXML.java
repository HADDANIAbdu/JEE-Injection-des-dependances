package present;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresentationXML {
    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("config.xml");
        IMetier metier = appContext.getBean("metier", IMetier.class);
        System.out.println("Temperature xml : "+ metier.calcul() +" °C");
    }
}
