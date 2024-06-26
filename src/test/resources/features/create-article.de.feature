#language: de
Funktionalität: Artikel anlegen.

  Szenario: Als ein Anwender sollte ich einen neuen Artikel anlegen können.
    Angenommen Erstelle einen Artikel mit folgenden Feldern
      | title | Cucumber                                                |
      | body  | Lorem ipsum dolor sit amet, consectetur adipiscing elit |
    Dann Sollte die Antwort den Status 200 haben
    Und "id" sollte nicht null sein
    Und "title" sollte nicht null sein
    Und "body" sollte nicht null sein
    Und "lastUpdatedOn" sollte nicht null sein
    Und "createdOn" sollte nicht null sein
    Und "title" sollte gleich "Cucumber" sein
    Und "title" sollte gleich zur Anfrage sein


  Szenario: Als Anwender soll ich keine Artikel mit ungültigen Feldern anlegen können.
  Die Fehlermeldung sollte anzeigen, welches Feld den Fehler verursacht hat.
    Angenommen Erstelle einen Artikel mit folgenden Feldern
      | title |                                                         |
      | body  | Lorem ipsum dolor sit amet, consectetur adipiscing elit |
    Dann Sollte die Antwort den Status 400 haben
    Und "body.detail" sollte "Title is empty" enthalten