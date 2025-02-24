# TP : Injection de Dépendances en Java

## Objectif
Ce TP a pour but d'illustrer l'injection de dépendances en Java à travers la création d'interfaces et d'implémentations en utilisant le couplage faible. Nous allons explorer deux manières d'injecter les dépendances :

- **Par instanciation statique**
- **Par instanciation dynamique (via la réflection)**

## 1. Création de l'interface `IDao`
L'interface `IDao` définit une méthode `getData()` qui représente une source de données.

```java
public interface IDao {
    double getData();
}
```

## 2. Implémentation de `IDao`
Nous allons créer une classe `DaoImpl` qui implémente l'interface `IDao` et retourne une valeur statique simulant l'accès à une source de données.

```java
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        return 42; // Exemple de donnée
    }
}
```

## 3. Création de l'interface `IMetier`
L'interface `IMetier` définit une méthode `calcul()` qui utilise une source de données pour effectuer un calcul.

```java
public interface IMetier {
    double calcul();
}
```

## 4. Implémentation de `IMetier` avec couplage faible
Nous allons implémenter l'interface `IMetier` dans une classe `MetierImpl`. Cette classe ne dépend pas directement de `DaoImpl`, mais utilise `IDao`, ce qui permet de changer facilement l'implémentation de la source de données.

```java
package metier;

import dao.DaoImpl;
import dao.IDao;

public class MetierImpl implements IMetier {
    //couplage faible
    private IDao dao;

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    public MetierImpl() {
    }

    @Override
    public double calcul() {
        double t=dao.getData();
        double res=t*25;
        return res;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

}
```

## 5. Injection des dépendances

### a) Par instanciation statique
Nous créons directement les objets dans le programme principal.

```java
public static void main(String[] args) {

        /*
        Injection des dependances par instanciation statique
         */

        DaoImplV2 d = new DaoImplV2();
        MetierImpl metier = new MetierImpl(d); //injection des dependance via le constructeur
        //  metier.setDao(d); //injection des dependance via le setter
        System.out.println(metier.calcul());

    }
```

### b) Par instanciation dynamique (via la réflection)
Cette approche permet de charger dynamiquement les classes sans les dépendances directes dans le code source.

```java
package pres;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class PresentationV2 {
    public static void main(String[] args)  {
        try {
            Scanner scanner=new Scanner(new File("config.txt"));
            String daoClassname=scanner.nextLine();
            Class cDao=Class.forName(daoClassname);
            IDao dao=(IDao) cDao.getConstructor().newInstance();

            String metierClassname=scanner.nextLine();
            Class cMetier=Class.forName(metierClassname);
           // IMetier metier=(IMetier) cMetier.getConstructor(IDao.class).newInstance(dao); par constructeur
            IMetier metier=(IMetier) cMetier.getConstructor().newInstance();
            //par setter
            Method setDao=cMetier.getDeclaredMethod("setDao",IDao.class);
            setDao.invoke(metier,dao);
            System.out.println("RES="+metier.calcul());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```

Le fichier `config.txt` contiendra le nom de la classe :
```
dao.DaoImpl
metier.MetierImpl
```

## Ressources
Le Professeur Mohamed Youssfi explique en détail le principe de l'inversion de contrôle (IoC) et l'injection de dépendances dans cette vidéo :
[Principe de l'Inversion de Contrôle (IoC) et Injection des Dépendances](https://www.youtube.com/watch?v=N6_IL2cxVrs&ab_channel=ProfesseurMohamedYOUSSFI)

## Conclusion
L'injection de dépendances permet de réduire le couplage entre les classes et d'améliorer la flexibilité et la maintenabilité du code. En utilisant l'instanciation dynamique, nous pouvons modifier le comportement du programme sans changer son code source, ce qui est idéal pour les applications modulaires et extensibles.


