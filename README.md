# Green House Project

- [Green House Project](#green-house-project)
	- [Feladatok:](#feladatok)
		- [1. A projekt leírása](#1-a-projekt-leírása)
		- [2. Az elvárt működés](#2-az-elvárt-működés)
		- [3. Modulok](#3-modulok)
			- [**3.1. Loader modul:**](#31-loader-modul)
			- [**3.2. Monitor modul**](#32-monitor-modul)
			- [**3.3. Driver modul**](#33-driver-modul)
			- [**3.4. Controller modul**](#34-controller-modul)
		- [4. Felhő szolgáltatások](#4-felhő-szolgáltatások)
			- [**4.1 MonitorService**](#41-monitorservice)
	- ["Szimulált Működés"](#szimulált-működés)


## Feladatok:
    
### 1. A projekt leírása
A projekt során a cél egy olyan felhő alapú vezérlőrendszer fejlesztése, amely képes automatikusan
szabályozni az okos üvegházak hőmérsékletét és páratartalmát a fűtés és a locsoló berendezések
vezérlésével. A rendszer képes egyszerre több üvegház kezelésére, amelyeknél a termesztett növény
igényeinek függvényében előre definiálva van az elvárt hőmérséklet és minimális páratartalom. A
rendszer a telepített szenzorok által mért adatokat a felhőn keresztül kéri le, amelyek alapján képes
önállóan döntést hozni, hogy szükséges-e valamilyen beavatkozás.

### 2. Az elvárt működés
- A távfelügyeleti rendszer által megvalósított működés:
- Az egyes üvegházakra vonatkozó információkat egy JSON/XML fájlból lehet betölteni a
rendszer indulásakor.
- A vezérlő az indulás után a betöltött üvegházak listája alapján dolgozik és mindegyiknek sorra
lekéri egy azonosító segítségével annak állapotát (MonitorService), vagyis az aktuális
hőmérsékletet és páratartalmat, amelyek alapján döntést hoz, hogy milyen beavatkozásra van
szükség.
- A vezérlőegység a beérkezett adatok alapján eldönti, hogy szükséges-e valamilyen
beavatkozás, és ha igen, akkor a kazánnak hány fokot kell emelni a hőmérsékleten, illetve a
hőmérsékletváltozás után szükséges-e újabb locsolás a páratartalom megfelelő szinten tartása
érdekében. A döntés eredményét a felhőn keresztül küldi el (ControllerSevice) a megfelelő
eszköz számára.
- Ha rendszer hibát észlel, vagyis túl nagy a hőmérséklet vagy páratartalom eltérése az elvárttól,
akkor az eszközök meghibásodását kell feltételeznünk, és erről egy bejegyzés készül egy
naplófájlba.

###  3. Modulok
#### **3.1. Loader modul:**
A modul feladata az üvegházakra vonatkozó adatok betöltése a rendszer indulása során, amelyet
jelenleg a JSON/XML fájl feldolgozásával tud megtenni. A betöltés után az ILoader interfész által
definiált formátumban kell az adatokat átadni a Controller modul számára, amely megkezdi az
üvegházak monitorozását.
#### **3.2. Monitor modul**
A modul feladata, hogy a megkapott adatok alapján lekérdezze a felhőből (MonitorService) az
azonosítónak megfelelő üvegház belső hőmérsékletét (Celsius fok) valamint páratartalmát (%),
továbbá információt kapunk arról is, hogy a kazán vagy a locsolórendszer parancsot hajt-e végre, tehát
működik-e a lekérés pillanatában. Az azonosító egy 10 karakteres alfanumerikus kód, amely az
induláskor betöltött állományban található meg. A modul a szolgáltatás által visszaadott információkat
továbbítja a Controller modul számára az IMonitor interfész által definiált struktúrában.
#### **3.3. Driver modul**
A Driver modul végzi a Controller által meghatározott beavatkozások fordítását az aktuális eszköznek
megfelelő parancsokra. A Driver tartalmazza a kazán és locsoló rendszer vezérlő parancsait, ezek külön
fájlból való betöltésére nincs lehetőség, a driver frissítése során a teljes modul lecserélésre kerül. A
Driver az IDriver interfészen keresztül kapja az utasításokat a Controllertől, majd a megfelelő mappelés
után a felhőn keresztül (CommandService) küldi ki a vezérlő jeleket.
#### **3.4. Controller modul**
A Controller modul fő feladata, hogy az aktuális hőmérséklet, páratartalom, az eszközök állapota és a
beállított elvárt hőmérséklet és páratartalom alapján meghatározza, hogy szükség van-e valamilyen
beavatkozásra, amely alapján a következő esetek lehetségesek:
1. Ha valamelyik eszköz parancsot hajt végre, akkor a korábbi parancsot nem írjuk felül,
feltételezzük, hogy megfelelően került meghatározásra, így semelyik eszköz nem
kaphat új parancsot. Ebben az esetben egy üres parancsot (üres stringet) kell küldeni
a mindkét eszköz számára.
2. Ha semelyik eszköz nem hajt végre parancsot, akkor a rendelkezésre álló információk
alapján határozzuk meg, hogy melyiknek kell működnie:
   * Minden érték a megadott sávban van, nem szükséges a beavatkozás, üres
parancsot küldünk a felhőn keresztül.
   * A hőmérséklet nem megfelelő: kiadunk egy parancsot, amelyben megadjuk,
hogy a kazánnak hány fokkal kell növelnie a hőmérsékletet az optimális
hőmérséklet eléréséhez
        * Megfelelő kazán parancs kiadásával (lásd vezérlő parancsok rész)
Példa: aktuális hőmérséklet 25, minimálisan megengedett 27,
optimális 28  mivel 25 < 27, ezért szükséges a beavatkozás, vagyis
emeljük a hőmérsékletet az optimális szintig, ami 28-25=3 Celsius
fokot jelent, így a kiadandó parancs: **bup3c**
   * Ha az előző pont alapján szükséges a hőmérséklet emelés, akkor a cél
hőmérséklete alapján meghatározzuk a várható páratartalmat, ha nem, akkor
az eredetit vesszük figyelembe, amely, ha elmarad az elvárttól, akkor 1%-os
párolgást feltételezve megadjuk a locsoló rendszer számára a szükséges
kilocsolandó vízmennyiséget (l).
       * Példa: Tudjuk az aktuális hőmérsékletet pl.: 25 Celsius, és a
páratartalmat pl.: 70%. A következő táblázat alapján meghatározható,
hogy ez m3-enként hány g vizet jelent a levegőben, ami 25 celsiusnál
maximálisan 23,3g, ami 70%-os páratartalom mellett 23,3*0,7 =
16,31g/m3. A cél legyen például 30 celsius, ahol már 30.3 g vizet képes
tárolni köbmétereként, így már 16,31/30.3, azaz 53,82%-os lenne csak
a páratartalom, amelyet tegyük fel, hogy legalább 60%-on kell
tartanunk.
Így legalább 30.3*0,6 = 18,18g/m3-nek kell lennie a levegőben, tehát
18,18-16,31 = 1,89 g/m3-t kell még a levegőbe juttatni. Az 1%-os
párolgással számolva: 1,87 / 0.01 = 187 g fizet kell elszórni
köbmétereként. Ezt kell megszorozni az üvegház méretével, pl.: 1200
m3, így megkapjuk, hogy 224,4 liter vizet kell kilocsolni a megfelelő
páratartalom eléréséhez. Így a kiküldendő parancs (kerekített
értékkel): **son224l**

|  Hőmérséklet  |  Max g/m3  |
|:-------------:|:----------:|
|      20       |    17.3    |
|      21       |    18.5    |
|       -       |     -      |
|       -       |     -      |
|       -       |     -      |
|       -       |     -      |
|       -       |     -      |

3. Hiba észlelése, ha az elvárt szinttől nagy mértékben, vagyis legalább 5 fokkal
különbözik az aktuális hőmérséklet, illetve 20%-kal aktuális páratartalom, akkor hibát
enged feltételezni, amelyet fájlba kell logolni.

A vezérlő parancsok:
o Kazán: bup{x}c  hőmérséklet megemelése x fokkal
o Locsoló rendszer: son{x}l  x liter víz locsolásának megkezdése
A vezérlő a működése során iteratív végrehajtást fogunk feltételezni, de a jelenlegi prototípus során
elég egyszer végig ellenőrizni az egyes üvegházakat. 

### 4. Felhő szolgáltatások

A távfelügyeleti rendszer működéséhez két szolgáltatás használatára van szükség:
#### **4.1 MonitorService**
A szolgáltatás elérése: http://193.6.19.58:8181/greenhouse/{id}

A szolgáltatás paraméterként át kell adni az üvegház azonosítóját (id)pl.: (KFI3EW45RD), amelyre
válaszul a következő adatokat kapjuk:

| Paraméter       | Típus  | Leírás                                                              |
|-----------------|--------|---------------------------------------------------------------------|
| ghId            | string | Az üvegház azonosítója                                              |
| token           | string | Biztonsági token, amely mentése szükséges a további kommunikációhoz |
| temperature_act | double | Az üvegház aktuális hőmérséklete Celsius fokban                     |
| humidity_act    | double | Az üvegház aktuális relatív páratartalma %-ban                      |
| boiler_on       | bool   | A kazán parancsot hajt-e végre?                                     |
| sprinkler_on    | bool   | A locsoló parancsot hajt-e végre?                                   |


## "Szimulált Működés"

- A program elindítása után a program betölti az egyes üvegházakra vonatkozó információkat.
- A betöltött üvegház azonosítók alapján a rendszer lekéri az aktuális állapotát az üvegházaknak
- A rendszer a beérkezett adatok alapján eldönti, hogy szükséges e beavatkozás.
- A döntés eredményét elküldi felhőn keresztül az eszközök számára.
- Ha a rendszer hibát észlel akkor azt rögzíti egy naplófájlban.
- A rendszer egyszeri lefutás után autómatikusan kilép és lezárja a kapcsolatokat.
