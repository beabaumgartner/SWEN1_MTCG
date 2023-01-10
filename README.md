# SWEN1_MTCG
## Monster Trading Cards Game

### Design

#### DB
Die Postgres-Datenbank wird mittels pg-admin aufgebaut und direkt in IntelliJ eine Verbindung aufgebaut.
Anstatt eines dockers gibt es im Ordner databse, ausführbare sql-Statments welche die DB erstellen, löschen oder reseten können.

![](.\database\DB_diagramm.jpg)

Der source Code ist in main und test gegliedert.

#### Testing
Die Unittests sind gebündelt in mtcgapp vorhanden. Vor dem starten der Unittest wird empfohlen die Datenbank, im Ordner database mit dem file reset_db.sql auszuführen um etwaige vorhandene Daten zu löschen und die DB neu aufzubauen.
Die Unittest sind so aufgebaut, dass sie ein eigenen Integration Test darstellen, da die Unittest mittels einer Ausführungsreinfolge gegliedert sind und mehrere aufeinander aufbauende Edge-Cases abprüfen (sowie das curl script, Bsp. um das battle Service abzufragen werden vorher enstprechende Unittests ausgeführt).
Des weiteren befindet sich in ./documents/executables/integration_test_postman, postman exportierte files, welche in Postman selbst importiert werden können und automatisiert Service-Routen abfragen.

#### Sourcecode (Main)

![](.\documents\specifications\server_diagramm.jpg)

HTTP-Server: Der httpserver wurde aus dem Moodle-Beispiel übernommen. Dabei wurden Kleinigkeiten hinzugefügt, um Beispielsweise den Contens-Type oder den Authorisierungs-Token zu erhalten.
Dieser implementiert einen Server Listener welche auf herinkommende Cliends hört und diese in eigene Threads auslagert.

src-Code: Der Source-Code wurde mittels Services und der DAL umgesetzt. Das DAL wurde nach dem Unit Of Work Schema umgesetzt.

### Lessons Lerned
Während dem Projekt habe ich gelernt, wie man eine Datenbank-Verbindung in IntelliJ aufbaut und eine entsprechende UOW gliedert.
Dabei konnte ich mit den commit und rollback commands für Datenbanken das erste mal arbeiten.
Dabei habe ich gelernt wie ein HTTP-Server aufgebaut wird, wie eine Request und ein Respons aussieht und man diese aufgliedert, um Daten heraus zu lesen.

### Testing Decisions
Ich habe in erster-Linie die Routen der Services ausgetestet und die zu erwartende Respons abgefragt. Dabei habe ich auch mittels der Unittests ein Integrations Test geschrieben, welcher mit den verschiedenen Test mögliche Use-cases testen kann.
Während dem Entwickeln, wurde oft auch das Open API Postman genuntzt und für diese auch Integration Tests erstellt welche in .\documents\executables\integration_test_postman zu finden sind.
Zudem wurde das CURL-Script mit einem "Enter Key to close program" versehen. Das CURL-Script ist in .\documents\executables zu finden.

### Unique Feature
Das eingebaute unique feature beinhaltet ein neues Service mit der Service-Route battlelogs. Dabei können zwei verschiedene GET-Requests abgefragt werde.
Dieser Request benötigt einen gültigen Authentication-Token und braucht kein body;
```
GET /battlelogs
```
<div style="float: right; width: 50%; margin-left: 4%; margin-top: -15px;">
  <p><img width="460" src=".\documents\executables\integration_test_postman\images\battlelogs.png" width="300px"/></p>
  <p> <br> </p>
</div>
<div tyle="float: right; width: 50%; margin-left: 4%;">
  <pre> Responses: </pre>
  <pre>
Code: 200 
    [
        {
            "battle_logId": 1,
            "first_Player": "kienboec",
            "second_Player": "altenhof"
        },
        {
            "battle_logId": 3,
            "first_Player": "bea",
            "second_Player": "kienboec"
        }
    ]
  </pre>
  <pre >
Code: 401
    Authentication information is 
    missing or invalid
  </pre>
<pre>
Code:404
    No Battle logs for user found
</pre>
</div>

<p> <br> </p>
<p> <br> </p>

```
GET /battlelogs/{battlelog_id}
```

<div style="float: right; width: 50%; margin-left: 4%; margin-top: -15px;">
  <p><img width="460" src=".\documents\executables\integration_test_postman\images\battlelogs_battlelog_id.png" width="300px"/></p>
  <p> <br> </p>
</div>
<div tyle="float: right; width: 50%; margin-left: 4%;">
  <pre> Responses: </pre>
  <pre>
Code: 200
Battle: kienboec vs altenhof

Round 1:
kienboec: WaterSpell (20 Damage) altenhof: RegularSpell (45 Damage)
=> 20 vs 45 -> 10 vs 90 => RegularSpell wins
Deck-Card Amount of kienboec: 3 vs altenhof: 5

Round 2:
kienboec: Dragon (50 Damage) altenhof: RegularSpell (50 Damage)
=> 50 vs 50 -> 50 vs 50 => Draw
Deck-Card Amount of kienboec: 3 vs altenhof: 5
  </pre>
  <pre >
Code: 401
    Authentication information is 
    missing or invalid
  </pre>
<pre>
Code:404
    No Battle log found with specific 
    battlelog-id
</pre>
</div>

### Tracked Time

<div style="float: left; width: 42%; margin-left: 4%;">
    <p>

| Datum       | Stunden  |
|-------------|----------|
| 21.11.2022  | 7        |
| 28.11.2022  | 5        |
| 05.12.2022  | 6        |
| 20.12.2022  | 3,5      |
| 23.12.2022  | 4        |
| 27.12.2022  | 10       |
| 02.12.2023  | 4        |
</p>
</div>
<div style="float: left; width: 50%; margin-left: 4%;">
<p>

| Datum         | Stunden |
|---------------|---------|
| 03.01.2023    | 10      |
| 04.01.2023    | 14      |
| 06.01.2023    | 13      |
| 07.01.2023    | 15      |
| 08.01.2023    | 7,5     |
| 09.01.2023    | 2       |
| 10.01.2023    | 6       |
| Stunden total | 107     |
</p>
</div>

**link zu GIT-Repository**: https://github.com/beabaumgartner/SWEN1_MTCG.git
