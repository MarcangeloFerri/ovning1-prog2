# PROG2: Övningsuppgift 1

# Instruktioner

Se iLearn.

# Kompilering och testning

Kompilera koden med:

```bash
./mvnw compile
```

(byt ut `./mvnw` till `mvnw.bat` på Windows)

Paketera till ett `.jar`-arkiv med:

```bash
./mvnw package -Dmaven.test.skip
```

(ni behöver lägga till `-Dmaven.test.skip` om alla testfall inte lyckas)

Ni kan köra tillämpningsprogrammet med (efter `package`):

```bash
java -cp target/ovning1-1.0-SNAPSHOT.jar se.su.ovning1.Exercise1
```

Kör testfallen med:

```bash
./mvnw test
```
