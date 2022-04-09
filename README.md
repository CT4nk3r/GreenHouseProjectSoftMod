# GreenHouseProjectSoftMod

Feladatok:
    <a href=#elso>Elso feladat</a>
    

1. <a name='elso'>A projekt leírása</a>
   A projekt során a cél egy olyan felhő alapú vezérlőrendszer fejlesztése, amely képes automatikusan
szabályozni az okos üvegházak hőmérsékletét és páratartalmát a fűtés és a locsoló berendezések
vezérlésével. A rendszer képes egyszerre több üvegház kezelésére, amelyeknél a termesztett növény
igényeinek függvényében előre definiálva van az elvárt hőmérséklet és minimális páratartalom. A
rendszer a telepített szenzorok által mért adatokat a felhőn keresztül kéri le, amelyek alapján képes
önállóan döntést hozni, hogy szükséges-e valamilyen beavatkozás.</p>

1. Az elvárt működés
A távfelügyeleti rendszer által megvalósított működés:
    Az egyes üvegházakra vonatkozó információkat egy JSON/XML fájlból lehet betölteni a
rendszer indulásakor.
    A vezérlő az indulás után a betöltött üvegházak listája alapján dolgozik és mindegyiknek sorra
lekéri egy azonosító segítségével annak állapotát (MonitorService), vagyis az aktuális
hőmérsékletet és páratartalmat, amelyek alapján döntést hoz, hogy milyen beavatkozásra van
szükség.
    A vezérlőegység a beérkezett adatok alapján eldönti, hogy szükséges-e valamilyen
beavatkozás, és ha igen, akkor a kazánnak hány fokot kell emelni a hőmérsékleten, illetve a
hőmérsékletváltozás után szükséges-e újabb locsolás a páratartalom megfelelő szinten tartása
érdekében. A döntés eredményét a felhőn keresztül küldi el (ControllerSevice) a megfelelő
eszköz számára.
    Ha rendszer hibát észlel, vagyis túl nagy a hőmérséklet vagy páratartalom eltérése az elvárttól,
akkor az eszközök meghibásodását kell feltételeznünk, és erről egy bejegyzés készül egy
naplófájlba.
