package present;

import config.AppConfig;
import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class presentationAnnotations {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        IMetier metier = appContext.getBean(IMetier.class);
        System.out.println("Temperature Annotation : "+ metier.calcul() +" °C");
    }
}
