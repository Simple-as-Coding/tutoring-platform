# S.O.L.I.D.

▪ **Single Responsibility Principle**
> A class should have only one reason to change.

**Klasa powinna mieć tylko jeden powód do zmiany.** W tym kontekście odpowiedzialność uważa się za jeden z powodów
zmiany. Zasada ta mówi, że jeśli mamy 2 powody, by zmienić klasę, musimy podzielić funkcjonalność na dwie klasy. Każda
klasa poradzi sobie tylko z jedną odpowiedzialnością, a w przyszłości, jeśli będziemy musieli dokonać jednej zmiany,
zrobimy to w klasie, która ją obsługuje. Kiedy musimy wprowadzić zmiany w klasie, która ma więcej obowiązków, zmiana
może wpłynąć na kod, który nie powinien być modyfikowany.

▪ **Open Close Principle**
> Software entities like classes, modules and functions should be open for extension but closed for modifications.

**Elementy oprogramowania, takie jak klasy, moduły i funkcje, powinny być otwarte na rozszerzenia, ale zamknięte dla
modyfikacji.** Zasada ta jest stosunkowo intuicyjna, bo polega na tym by tak zaprojektować rozwiązanie, aby
implementacja nowych funkcjonalności nie zmieniała istniejącego kodu a jedynie go rozszerzała. Najczęstsze sposoby na
zastosowanie tej zasady to tzw. polimorfizm, wzorzec strategii i odwrócenie zależności.

▪ **Liskov's Substitution Principle**
> Derived types must be completely substitutable for their base types.

**Typy pochodne muszą całkowicie zastępować typy podstawowe.** Zasada ta jest rozszerzeniem zasady Open Close Principle
pod względem zachowania, co oznacza, że nowe klasy pochodne rozszerzają klasy podstawowe bez zmiany ich zachowania.
Innymi słowy klasy pochodne powinny móc zastąpić klasy bazowe bez żadnych zmian w kodzie. Zasadę tę Robert Martin
zaczerpną od Barbary Liskov (profesor MIT). Została ona opublikowana w 1987r.

▪ **Interface Segregation Principle**

> Clients should not be forced to depend upon interfaces that they don't use.

**Klienci nie powinni być zmuszani do polegania na interfejsach, których nie używają.** Ta zasada uczy nas dbać o to,
jak piszemy nasze interfejsy. Pisząc je, powinniśmy zadbać o to, aby dodać tylko metody, które powinny tam być. W
przeciwnym wypadku, klasy implementujące interfejs również będą musiały zaimplementować te metody. Na przykład, jeśli
utworzymy interfejs o nazwie Worker i dodamy metodę przerwy na posiłek, to wszyscy pracownicy będą musieli ją
zaimplementować.

▪ **Dependency Inversion Principle**

> High-level modules should not depend on low-level modules. Both should depend on abstractions.

> Abstractions should not depend on details. Details should depend on abstractions.

**Moduły wysokiego poziomu nie powinny zależeć od modułów niskiego poziomu. Oba powinny zależeć od abstrakcji.
Abstrakcje nie powinny zależeć od szczegółów. Szczegóły powinny zależeć od abstrakcji.** Zasada odwrócenia zależności
określa, że powinniśmy oddzielać moduły wysokiego poziomu od modułów niskiego poziomu, wprowadzając warstwę abstrakcji
między klasami wysokiego poziomu a klasami warstwy niższej. W klasyczny sposób, gdy moduł oprogramowania (klasa,
komponent) potrzebuje jakiegoś innego modułu, inicjuje się i zawiera bezpośrednie odniesienie do niego. Spowoduje to
ścisłe połączenie tych dwóch elementów. Stosując odwrócenie zależności poprzez warstwę abstrakcji, moduły mogą być łatwo
zamieniane na inne.

Jest bardzo ważna zasada, dotycząca nie tyle projektu systemu co jego architektury.

Bardzo dobrym miejscem do zastosowania zasady DIP jest styk warstwy aplikacji z warstwą dostępu do danych (DAO). Warstwa
aplikacji powinna być zależna jedynie od zbioru interfejsów (warstwa abstrakcji), których używa w sposób ślepy „nie
wiedząc” jaka technologia DAO jest aktualnie w użyciu. Taka struktura aplikacji umożliwia wymianę technologii DAO oraz
DBMS’u w sposób niewidoczny dla reszty architektury systemu.

# Links

* Design patterns, anti-patterns, refactoring, UML at [sourcemaking.com](https://sourcemaking.com)
* Solid principles simple and easy explanation
  at [betterprogramming.pub](https://betterprogramming.pub/solid-principles-simple-and-easy-explanation-f57d86c47a7f)
* Solid principles at [baeldung.com](https://www.baeldung.com/solid-principles)
