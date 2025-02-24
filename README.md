# JEE - Injection des dépendances 🚀
<br>

## 📌 Description de l'activité pratique : Injection des Dépendances et Couplage Faible
Dans cette activité, nous allons explorer l'injection des dépendances (DI) pour atteindre un couplage faible entre les classes d'une application Java.

## 1️⃣ Créer l'interface IDao avec une méthode getData()
L'interface IDao représente la couche d'accès aux données.
```java
public interface IDao {
    double getData();
}
```

## 2️⃣ Créer une implémentation de IDao
On crée une classe qui implémente cette interface.
```java
public class IDaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("database Version !");
        double temperature = 86;
        return temperature;
    }
}
```

## 3️⃣ Créer l'interface IMetier avec une méthode calcul()
L'interface IMetier représente la logique métier.
```java
public interface IMetier {
    double calcul();
}
```

## 4️⃣ Implémenter IMetier avec un couplage faible
On injecte IDao dans MetierImpl au lieu de le créer directement.
```java
public class IMetierImpl implements IMetier{
    private IDao Idao; //le couplage faible
    
    @Override
    public double calcul() {
        double temp =  Idao.getData();
        double res = (temp - 32) * 5/9;
        return res;
    }
    
    //Injection des dépendances via le setter
    public void setIdao(IDao Idao) {
        this.Idao = Idao;
    }
}
```

## 5️⃣ Injection des dépendances 
### 🎯 Par instanciation statique
###### en utilisant le setter : 
Dans la classe PresentationV1 (la classe main) on va d'abord créer une instanciation pour la classe IDaoImpl , parce que par défaut la valeur de l'attribut Idao
dans la classe IMetierImpl est null ce qui va générer une exception NullPointerException . comme elle est présenter dans cette image :

<div style="display: flex; justify-content: center;">
  <img src="/assets/NullPointerException.png" width="700">
</div>

le code de la classe PresentationV1 est le suivant, on a utilisé **l'injection des dépendances** via  **l'instanciation statique** en utilisant la méthode setIDao :
```java
public class PresentationV1 {
    public static void main(String[] args) {
        IDaoImpl dao = new IDaoImpl();
        IMetierImpl metier = new IMetierImpl();
        metier.setIdao(dao);
        System.out.println("Temperature : "+ metier.calcul() +" °C");
    }
}
```

maintenant on peut calculer la temperature sans aucun exception , et voici le résultat :
<div style="display: flex; justify-content: center;">
  <img src="/assets/result.png" width="300">
</div>

###### en utilisant le constructeur : 
Dans la classe IMetierImpl on va créer deux constructeur, l'un sans paramètres pour l'utilisation de setter pour DI, et l'autre avec un paramètre de type IDao 
pour DI avec le constructeur . le code de la classe devient alors : 
```java
public class IMetierImpl implements IMetier{
    private IDao Idao; //le couplage faible

    public IMetierImpl() {
        //constructeur sans paramètres pour utiliser le setter
        //dans l'injection des dépendances
    }

    public IMetierImpl(IDao dao) {
        this.Idao = dao;
        //constructeur avec paramètre pour faire l'injection
        //des dépendances avec le constructeur directement
    }
    @Override
    public double calcul() {
        double temp =  Idao.getData();
        double res = (temp - 32) * 5/9;
        return res;
    }

    //Injection des dépendances via le setter
    public void setIdao(IDao Idao) {
        this.Idao = Idao;
    }
}
```
et de meme dans la classe PresentationV1, on va utiliser directement le constructeur pour créer une instance de la classe IMetierImpl et au meme temps 
injecter la dépendance IDao :
```java
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
```
le résultat :
<div style="display: flex; justify-content: center;">
  <img src="/assets/result_const.png" width="300">
</div>

### 🎯 Par instanciation dynamique
Pour cette partie on créer une nouvelle classe PresentationV2 pour faire l'injection des dépendances par l'instanciation dynamique en utilisant un fichier de configuration
config.txt qui va contenir les différents classe à utiliser pour garantir fermer la classe de presentation aussi à la modification .

le fichier config.txt contient la classe IDaoImplV2 pour la version web service, et la classe IMetierImpl qui implémente l'interface IMetier:
```txt
ext.IDaoImplV2
metier.IMetierImpl
```
dans la classe PresentationV2, on va essayer d'injecter les dépendances par l'instanciation dynamique via le setter et le constrocteur.
###### en utilisant le setter : 
le code de la classe vise à lire d'abord la classe qui implémente l'interface IDao depuis le fichier config.txt, et de meme la classe IMetierImpl et utiliser la
méthode **getConstructor().newInstance()** pour créer une instance de la classe IMetierImpl, et alors utiliser la classe Method pour utiliser la méthode setIDao 
en spécifiant à la méthode **getDeclaredMethod()** le nom de la méthode à chercher et le type de l'argument .
en fin, utiliser la méthode invoke pour exécuter la méthode sur l'objet Imetier en passant le paramètre Idao .
```java
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
            IMetier Imetier = (IMetier) cMetier.getConstructor().newInstance();

            //utiliser la méthode setIDao pour l'injection des dépendances
            Method setIdao = cMetier.getDeclaredMethod("setIdao", IDao.class);
            setIdao.invoke(Imetier, Idao);
            System.out.println("Temperature : "+Imetier.calcul()+" °C");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
```
le résultat :
<div style="display: flex; justify-content: center;">
  <img src="/assets/Dynamic_setter.png" width="300">
</div>

###### en utilisant le constructeur :
l'utilisation de constructeur et plus facile, on specifiant juste le type de paramètre à passé aux constructeur et le paramètre lui meme , qui est Idao.
```java
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
```
le résultat :
<div style="display: flex; justify-content: center;">
  <img src="/assets/Dynamic_constructeur.png" width="300">
</div>

### 🎯 En utilisant le Framework Spring Version XML
Il faut d'abord ajouter la dépendance de spring context sur le fichier pom.xml, 
```xml
<dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>6.1.3</version>
        </dependency>
</dependencies>
```
pour qu'on puissent utiliser la classe **ClassPathXmlApplicationContext** de spring en passant le nom de fichier de configuration
en paramètre qui va etre de type xml dans ce cas **config.xml** .

Le fichier config.xml contient la liste des objets (beans) que Spring va créer et gérer automatiquement lors du démarrage de l'application.
L'injection des dépendances se fait grâce au tag **<property>**. Cela signifie que Spring va chercher dans la classe **IMetierImpl** une méthode 
**setIdao(IDao idao)**. Ensuite, il va appeler cette méthode pour donner au bean metier une instance du bean dao.

Autrement dit, Spring relie automatiquement les objets entre eux, sans qu'on ait besoin de les créer manuellement dans le code.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dao" class="dao.IDaoImpl"></bean>
    <bean id="metier" class="metier.IMetierImpl">
        <property name="Idao" ref="dao"></property>
    </bean>
</beans>
```
ensuite on va crée et initialise le conteneur Spring en chargeant la configuration XML (config.xml).
Spring scanne et instancie tous les beans définis dans config.xml dès cette étape. après, on demande à Spring de nous fournir
une instance du bean nommé "metier", Une fois le bean récupéré, on appelle la méthode **calcul()** pour afficher le résultat.
```java
public class PresentationXML {
    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("config.xml");
        IMetier metier = appContext.getBean("metier", IMetier.class);
        System.out.println("Temperature xml : "+ metier.calcul() +" °C");
    }
}
```
le résultat : 
<div style="display: flex; justify-content: center;">
  <img src="/assets/spring_xml.png" width="300">
</div>
