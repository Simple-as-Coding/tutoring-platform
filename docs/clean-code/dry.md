# DRY (Don’t Repeat Yourself)

Jeden z grzechów głównych – powtórzenia w kodzie źródłowym. Każdy programista powinien zauważać takie sytuacje niemal
natychmiast – i sprawnie sobie z nimi radzić. Każde uczucie deja vu powinno powodować natychmiastową reakcję –
przynajmniej zatrzymanie się na chwilę i rozpoznanie sytuacji.

Jak likwidować powtórzenia ? To proste. Prawie zawsze dobrym rozwiązaniem jest nowa funkcja. Tworzysz nową funkcję,
kopiujesz do niej powtarzający się kod, a w miejscach powtórzeń wywołujesz utworzoną funcję. Tyle.

Tym jednym ruchem: skracamy kod źródłowy, ułatwiamy jego zrozumienie, umożliwiamy jego testowanie oraz dajemy możliwość
łatwego wprowadzania zmian w przyszłości. Tak wiele za tak niewiele.