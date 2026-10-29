# Vérifications réalisées

Le projet a été construit avec Java 17 et le Maven Wrapper fourni. Les résultats ci-dessous proviennent d’exécutions réelles ; ils ne représentent pas une note de l’enseignant.

| Vérification | Résultat |
| --- | --- |
| `mvnw.cmd -B -ntp clean verify` | Succès des trois projets Maven et création des deux JAR exécutables |
| `NotificationInjectionTest` | 2 tests réussis, 0 erreur, 0 échec |
| `ProductServiceIntegrationTest` sur H2 | 3 tests réussis, 0 erreur, 0 échec |
| Mêmes tests de produits sur MySQL 8.4.13 | 3 tests réussis, 0 erreur, 0 échec, y compris le rollback |
| JAR de notifications | Email sélectionné malgré la présence du service SMS |
| JAR de produits sur MySQL | Trois insertions, liste, recherche, suppression et liste finale conformes au sujet |
| Consultation SQL après fermeture du JAR | Ordinateur et Clavier persistés ; Souris absente |
| Inspection des bibliothèques du JAR produit | Hibernate 7.2.25.Final, Spring ORM 7.0.10 et MySQL Connector 9.7.0 présents ; aucune bibliothèque Spring Data ou H2 embarquée |

La suite standard comporte cinq tests distincts. Les trois tests de produits ont été exécutés une seconde fois contre un vrai serveur MySQL, dans la base dédiée `evaluation_produits_test`, en remplaçant les paramètres `TEST_DB_*` du profil de test.

Le test de rollback ouvre une transaction Spring, appelle le service pour insérer un produit, puis provoque une exception. Une consultation dans une nouvelle transaction confirme l’absence du produit annulé. Les tests CRUD vérifient les données et la suppression entre des appels de service séparés, sans transaction englobante de test.

Les deux applications ont aussi été exécutées avec leurs JAR produits par le build. La vérification SQL porte sur la base normale `evaluation_produits`, après la fin du processus Java. Une exécution supplémentaire dans une base de démonstration vide a permis d’enregistrer une sortie avec les identifiants 1, 2 et 3, comme dans l’énoncé.

## Sorties enregistrées

- [Notification Email](notification-output.txt)
- [Scénario complet sur MySQL](product-mysql-output.txt)
- [Structure de la table et données persistées après exécution](mysql-persistence.txt)

La configuration normale utilise MySQL et conserve les données avec `hbm2ddl.auto=update`. H2 est limité au scope Maven `test`. Les identifiants réels des exécutions suivantes peuvent différer, puisque la démonstration conserve les produits déjà présents.
