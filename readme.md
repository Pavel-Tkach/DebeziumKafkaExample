Приложение работающее с Debezium. Debezium слушает изменения в одной БД, отправляет их в Kafka. Затем приложение считывает их из Kafka, парсирует и отправляет на реплицируемую БД.

Шаги по развертыванию приложения:
1) Запустить команду docker compose up
2) Зайти в контейнер с образом postgres:13 при помощи docker exec -it <Container-Id> /bin/sh
3) перейти в сам PostgreSQL: psql -U docker -d exampledb -W
4) Ввести пароль docker
5) Выполнить скрипт создания таблицы
6) И настроить таблицу командой ALTER TABLE public.student REPLICA IDENTITY FULL;
7) Далее уже можно в другом консольном окне вызвать команду: 

curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" 127.0.0.1:8083/connectors/ --data "@debezium.json"

8) Теперь можно запустить само приложение