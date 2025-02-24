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
