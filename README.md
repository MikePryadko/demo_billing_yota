## Тестовое задание: 

Сделать простую реализацию сервиса тарификации услуг сотовой связи (минут звонков и гигабайт интернета).

Сервис должен позволять:

* Начислять пакеты минут и гигабайтов, имеющих время жизни
* Расходовать (вычитать) минуты и гигабайты из пакета
* Получать количество доступных минут и гигабайт
* Активировать и блокировать сим-карты

 

Технологии для использования:

* Spring Boot
* Gradle
* Любая встраиваемая СУБД
* Любые другие библиотеки

 

Код должен быть опубликован на github и содержать всё необходимое для работы с приложением описание.

Решение должно быть реализовано в виде самостоятельного jar файла и не должно требовать каких-либо других зависимостей для запуска.

Ожидается высокая степень покрытия тестами

---
В жизни биллинг имеет интеграцию с другой БД, хранящей детализацию по траффику, куда собирается информация со всей сотовой сети.

В нашем же примере, достаточно просто HTTP REST метода, который должен уменьшать количество ресурса в активном пакете.