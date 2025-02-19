# Тест-кейсы для автотестов микросервиса создания и просмотра объявлений

## Тест-кейс 1: Создание объявления продавцом
### Описание:
Проверить создание объявления продавцом

### Тестовая среда:
https://qa-internship.avito.com

### Тестовые данные:
sellerID 358937

### Шаги:
1. Вызвать метод POST /api/1/item с телом:

   {
   "sellerID": 358937,
   "name": "Донской сфинкс",
   "price": 20000,
      "statistics":{
         "contacts":3,
         "likes":500,
         "viewCount":1523
      }
   }
### Ожидаемый результат:
1. 200 OK

   {
   "status": "Сохранили объявление - a39def56-8dd7-4a7d-95d3-66b62a1fcea7"
   }

В значении поля "status" получаем уникальный идентификатор объявления в формате uuid


## Тест-кейс 2: Получение объявления по его идентификатору
### Описание:
Проверить получение объявления по его идентификатору

### Тестовая среда:
https://qa-internship.avito.com

### Тестовые данные:
sellerID 358937

### Предусловие:
1. Вызвать метод POST /api/1/item с телом:
   
{
   "sellerID": 358937,
   "name": "Донской сфинкс",
   "price": 20000,
      "statistics": {
         "contacts":3,
         "likes":500,
         "viewCount":1523
   }
   }
2. Получить идентификатор объявления в формате uuid из значения поля "status"

### Шаги:
1. Вызвать метод GET /api/1/item/:id с path-параметром из 2 пункта предусловия

### Ожидаемый результат:
1. 200 OK
   
[
   {
      "createdAt": "2025-02-13 19:14:39.906167 +0300",
      "id": "a39def56-8dd7-4a7d-95d3-66b62a1fcea7",
      "name": "Донской сфинкс",
      "price": 20000,
      "sellerID": 358937,
      "statistics": {
         "contacts": 3,
         "likes": 500,
         "viewCount": 1523
      }
   }
]

В значении поля "createdAt" получаем дату в формате год-месяц-день, время создания в формате
"часы:минуты:секунды.микросекунды", часовой пояс в формате +0300

В значении поля "id" получаем uuid из 2 пункта предусловия

Значения полей "name", "price", "sellerID", "contacts", "likes", "viewCount" соответствуют значениям аналогичных полей
из 1 пункта предусловия

В значении поля "statistics" содержится json с 3 полями: "contacts", "likes", "viewCount"

## Тест-кейс 3: Получение статистики по айтем id
### Описание:
Проверить получение статистики по айтем id

### Тестовая среда:
https://qa-internship.avito.com

### Тестовые данные:
sellerID 358937

### Предусловие:
1. Вызвать метод POST /api/1/item с телом:

   {
   "sellerID": 358937,
   "name": "Донской сфинкс",
   "price": 20000,
      "statistics": {
         "contacts":3,
         "likes":500,
         "viewCount":1523
      }
   }
2. Получить идентификатор объявления из значения поля "status"

### Шаги:
1. Вызвать метод GET /api/1/statistic/:id с path-параметром из 2 пункта предусловия

### Ожидаемый результат:
1. 200 OK

   [
   {
   "contacts": 3,
   "likes": 500,
   "viewCount": 1523
   }
   ]

Значения полей "contacts", "likes", "viewCount" соответствуют значениям аналогичных полей из 1 пункта предусловия

## Тест-кейс 4: Получение всех объявлений по идентификатору продавца
### Описание:
Проверить получение всех объявлений по идентификатору продавца

### Тестовая среда:
https://qa-internship.avito.com

### Тестовые данные:
sellerID 358937

### Предусловие:
1. У продавца созданно 2 объявления

### Шаги:
1. Вызвать метод GET /api/1/:sellerID/item с path-параметром sellerID

### Ожидаемый результат:
1. 200 OK

   [
      {
         "createdAt": "2025-02-13 19:14:39.906167 +0300",
         "id": "a39def56-8dd7-4a7d-95d3-66b62a1fcea7",
         "name": "Донской сфинкс",
         "price": 20000,
         "sellerID": 358937,
            "statistics": {
               "contacts": 3,
               "likes": 500,
               "viewCount": 1523
         }
      },

      {
         "createdAt": "2025-02-13 19:14:39.906167 +0300",
         "id": "u24uip45-8dd7-4a7d-95d3-66b62a1fcea7",
         "name": "Донской сфинкс",
         "price": 20000,
         "sellerID": 358937,
            "statistics": {
               "contacts": 3,
               "likes": 500,
               "viewCount": 1523
         }
      }
   ]

Внутри массива приходит 2 объекта.
Каждый объект должен содержать поля: "createdAt", "id", "name", "price", "sellerID", "statistics" с полями "contacts",
"likes", "viewCount".



## Тест-кейс 5: Получение объявления по его идентификатору с невалидными данными
### Описание:
Проверить ответ системы при получении объявления по его идентификатору с невалидными данными

### Тестовая среда:
https://qa-internship.avito.com

### Шаги:
1. Вызвать метод GET /api/1/item/:id с path-параметром с последующими значениями 12345, Объявление, Item, -_.~

### Ожидаемый результат:
1. 400 Bad Request

   {
   "result": {
   "message": "Передан некорректный идентификатор объявления",
   "messages": {}
   },
   "status": "400"
   }

Значение поля "message" содержит значение "Передан некорректный идентификатор объявления"

Значение поля "status" содержит значение "400"

## Тест-кейс 6: Получение статистики объявления по айтем id с невалидными данными
### Описание:
Проверить ответ системы при получении статистики объявления по его идентификатору с невалидными данными

### Тестовая среда:
https://qa-internship.avito.com

### Шаги:
1. Вызвать метод GET /api/1/statistic/:id с path-параметром с последующими значениями 12345, Объявление, Item, -_.~

### Ожидаемый результат:
1. 400 Bad Request

   {
   "result": {
   "message": "передан некорректный идентификатор объявления",
   "messages": {}
   },
   "status": "400"
   }

Значение поля "message" содержит значение "Передан некорректный идентификатор объявления"

Значение поля "status" содержит значение "400"

## Тест-кейс 7: Получение всех объявлений по идентификатору продавца с невалидными данными
### Описание:
Проверить ответ системы при получении статистики объявления по его идентификатору с невалидными данными

### Тестовая среда:
https://qa-internship.avito.com

### Шаги:
1. Вызвать метод GET /api/1/:sellerID/item с path-параметром с последующими значениями Объявление, Item, -_.~

### Ожидаемый результат:
1. 400 Bad Request

   {
   "result": {
   "message": "передан некорректный идентификатор продавца",
   "messages": {}
   },
   "status": "400"
   }

Значение поля "message" содержит значение "передан некорректный идентификатор продавца"

Значение поля "status" содержит значение "400"

## Тест-кейс 8: Создание объявления без поля "prise"
### Описание:
Проверка создания объявления без поля "prise"

### Тестовая среда:
https://qa-internship.avito.com

### Предусловие:
1. Пользователь авторизован в системе.

### Тестовые данные:
sellerID: 358937

### Шаги:
1. Вызвать метод POST /api/1/item с телом:

   {
   "sellerID": 358937,
   "name": "Донской сфинкс",
   "statistics":{
   "contacts": 3,
   "likes": 500,
   "viewCount": 1523
   }
   }

2. Вызвать метод GET 
### Ожидаемый результат:
1. 200 OK

   {
   "status": "Сохранили объявление - 424f05db-e666-4a88-acf8-dd4c2e1c642f"
   }

В значении поля "status" получаем уникальный идентификатор объявления в формате uuid

2. 200 OK

[
{
"createdAt": "2025-02-16 20:51:30.822827 +0300 +0300",
"id": "424f05db-e666-4a88-acf8-dd4c2e1c642f",
"name": "dsdsd",
"price": 0,
"sellerId": 358937,
"statistics": {
"contacts": 3,
"likes": 500,
"viewCount": 1523
}
}
]

Значение поля "price" содержит значение 0

## Тест-кейс 9: Получение объявлений по идентификатору продавца без объявлений
### Описание:
Проверить получение объявлений по идентификатору продавца, у которого нет созданных объявлений

### Тестовая среда:
https://qa-internship.avito.com

### Тестовые данные:
sellerID 519411

### Предусловие:
1. У продавца нет созданных объявлений

### Шаги:
1. Вызвать метод GET /api/1/:sellerID/item с path-параметром sellerID

### Ожидаемый результат:
1. 200 OK

   []

В ответе приходит пустой массив

## Тест-кейс 10: Поиск несуществующего объявления

### Описание:
Проверка ответа системы на поиск объявления, не созданного в системе.

### Тестовая среда:
https://qa-internship.avito.com

### Тестовые данные:
id: a39def56-8dd7-4a7d-95d3-66b62a1fcea1 

### Шаги:
1. Вызвать метод GET /api/1/item/:id

### Ожидаемый результат:
1. 404 Not Found

{
"result": {
"message": "item a39def56-8dd7-4a7d-95d3-66b62a1fcea1 not found",
"messages": null
},
"status": "404"
}

## Тест-кейс 11: Поиск статистики несуществующего объявления

### Описание:
Проверка ответа системы на поиск статистики объявления, не созданного в системе.

### Тестовая среда:
https://qa-internship.avito.com

### Тестовые данные:
id: a39def56-8dd7-4a7d-95d3-66b62a1fcea1

### Шаги:
1. Вызвать метод /api/1/statistic/:id

### Ожидаемый результат:
1. 404 Not Found

{
"result": {
"message": "statistic a39def56-8dd7-4a7d-95d3-66b62a1fcea1 not found",
"messages": null
},
"status": "404"
}