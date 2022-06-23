# CI/CD  

Fougerouse Arsène
Langlet Alexis

Polytech DO3

## Questions

### 1) Créez un fichier `docker-compose.yml` et ajoutez-y un service `db` s'appuyant sur l'image Docker `postgres:latest`. 
  
Un fichier `docker-compose.yml` a été créé. Comme L'ORM de quarkus utilise postgres:14 nous avons décidé d'utiliser cette image plutôt que la latest.  

### 2) Créez une base de données `city_api` avec une table `city`.  
    
L'ORM de quarkus nous permet de créer automatiquement cette table à partir de la description de l'entité.
Afin, de peupler un peu cette base de données, nous avons créé un fichier `import.sql` contenant quelques exemples de villes.


### 3) Dans le langage de votre choix, créez un service web. 

Pour cet exercice, nous avons opté pour le langage Java. Afin de nous permettre de créer facilement un service web, nous avons choisi d'utiliser le framework Quarkus.  
Les routes API ont bien été implémentées, leurs implémentations sont trouvables dans `CityResource.java` et `HealthResource.java`.  

La lecture des variables d'environnement est configurée dans `application.properties` grace aux paramètres de configuration de Quarkus.  

### 4) Écrivez des tests :  

Des tests sur les différents endpoints ont été mis en place dans `CityEndpointTest.java` et `HealthEndpointTest.java`. 
Ils sont executables en lancant la commande ```mvn quarkus:test``` ou la commande ```mvn test```
L'ORM de doctrine nous permet d'insérer dans la base de données, puis d'annuler les changements. 

### 5) Écrivez un fichier `Dockerfile` à la racine de votre projet. Testez que votre image Docker est correcte.  
Notre dockerfile est correct, pour build correctement le projet, il faut executer.


### 6) Écrivez un workflow GitHub Actions `ci` pour qu'un linter soit exécuté à chaque push.

Les workflows GitHub ont été écrit dans le dossier `.github/workflows`.

Une partie de la CI se base sur les outils de [SonarCloud](https://sonarcloud.io/). Ce site lance de la CI sur des projets JAVA et permet d'avoir un report concernant la qualité de code. Il affiche les vulnérabilité des packages utilisés, mais également le pourcentage de code couvert par les tests.
Il n'y a malheureusement pas de linters connus pour le langage Java, donc nous n'avons pas pu en utiliser pour la CI.

### 7) Modifiez le workflow pour que les tests s'exécutent à chaque push.

Pour lancer les tests à chaque push, il nous a suffit de créer un pipeline lancant un mvn test sur notre projet. 
### 8+9) Modifiez le workflow pour que l'image Docker soit push sur `ghcr.io` avec pour tag `city-api:latest`. 
Les services de ghcr.io posant problèmes, nous avons décidé de push sur `hub.docker.com` à la place. 
Nous utilisons pour cela l'action github [docker/build-push-action](https://github.com/docker/build-push-action/blob/master/docs/advanced/tags-labels.md) pour créer notre image et la pousser sur le répertoire docker d'Arsène.  

### 10) Écrivez un workflow GitHub Actions `release` qui, lorsqu'un tag au format `vX.X.X` soit poussé build et push l'image Docker avec un tag `city-api:X.X.X`. 

Afin de faire cela simplement, on rajoute une étape dans notre job précédent, celle-ci utilise [docker/metadata-action](https://github.com/docker/metadata-action) et nous permets de créer les bonnes images avec les bons tags en fonction des commits.  

### 11+12+13) Déploiement de l'application sur un cluster Kubernetes local

L'application à été déployée sur un cluster Kubernetes en local via Minikube. L'installation de Minikube s'est faite en suivant la [documentation](https://kubernetes.io/docs/tasks/tools/).

Les différentes ressources nécessaires au déploiement sont décrites dans le dossier `kubernetes`.

la base de données et l'API sont chacune déployées sur un pod. Cette dernière utilise la dernière image docker poussée sur [docker](https://hub.docker.com/r/gridexx/city-api/tags) via le workflow GitHub.

Les variables d'environnements sont injectées dans les pods via un service `Secret` qui permet de les encoder en base64. Pour assurer la communication entre l'API et la base de données, le service `postgres-database-service.yml` définit le nom de domaine au sein du cluster `quarkus-database`. Il est utilisé dans la variable d'environnement `CITY_API_DB_URL: "jdbc:postgresql://quarkus-database/postgres` par l'API. 

Par manque de temps, nous n'avons pas pu implémenter le déploiement de l'application sur un cluster Kubernetes via un Chart Helm, n'y nous occuper du Ingress Controlleur.  