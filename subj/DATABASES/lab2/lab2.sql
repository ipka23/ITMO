-- 1.	Сделать запрос для получения атрибутов из указанных таблиц,
-- применив фильтры по указанным условиям:
-- Таблицы: Н_ОЦЕНКИ, Н_ВЕДОМОСТИ.
-- Вывести атрибуты: Н_ОЦЕНКИ.КОД, Н_ВЕДОМОСТИ.ДАТА.
-- Фильтры (AND):
-- a) Н_ОЦЕНКИ.ПРИМЕЧАНИЕ = отлично.
-- b) Н_ВЕДОМОСТИ.ДАТА = 2022-06-08.
-- Вид соединения: RIGHT JOIN.

select Н_ОЦЕНКИ.КОД, Н_ВЕДОМОСТИ.ДАТА
from Н_ОЦЕНКИ
         right join Н_ВЕДОМОСТИ on Н_ВЕДОМОСТИ.ОЦЕНКА = Н_ОЦЕНКИ.КОД
where Н_ОЦЕНКИ.ПРИМЕЧАНИЕ = 'отлично'
  and Н_ВЕДОМОСТИ.ДАТА = '2022-06-08';


-- 2.	Сделать запрос для получения атрибутов из указанных таблиц,
-- применив фильтры по указанным условиям:
-- Таблицы: Н_ЛЮДИ, Н_ВЕДОМОСТИ, Н_СЕССИЯ.
-- Вывести атрибуты: Н_ЛЮДИ.ИД, Н_ВЕДОМОСТИ.ДАТА, Н_СЕССИЯ.ИД.
-- Фильтры (AND):
-- a) Н_ЛЮДИ.ФАМИЛИЯ = Афанасьев.
-- b) Н_ВЕДОМОСТИ.ИД = 1490007.
-- c) Н_СЕССИЯ.УЧГОД < 2008/2009.
-- Вид соединения: LEFT JOIN.


select Н_ЛЮДИ.ИД, Н_ВЕДОМОСТИ.ДАТА, Н_СЕССИЯ.ИД
from Н_ЛЮДИ
         left join Н_ВЕДОМОСТИ on Н_ЛЮДИ.ИД = Н_ВЕДОМОСТИ.ЧЛВК_ИД
         left join Н_СЕССИЯ on Н_ВЕДОМОСТИ.ИД = Н_СЕССИЯ.ЧЛВК_ИД
where Н_ЛЮДИ.ФАМИЛИЯ = 'Афанасьев'
  and Н_ВЕДОМОСТИ.ИД = 1490007
  and Н_СЕССИЯ.УЧГОД < '2008/2009';


-- 3.	Вывести число фамилий и имен без учета повторений.
-- При составлении запроса нельзя использовать DISTINCT.

select count(*) as ЧИСЛО_УНИКАЛЬНЫХ_ЧЛВК
from (select ФАМИЛИЯ, ИМЯ from Н_ЛЮДИ group by ФАМИЛИЯ, ИМЯ);

-- 4.	Выдать различные отчества студентов и число людей с каждой из этих отчеств,
-- ограничив список отчествами,
-- встречающимися менее 50 раз на заочной форме обучения.
-- Для реализации использовать соединение таблиц.


select Н_ЛЮДИ.ОТЧЕСТВО, count(*) as КОЛИЧЕСТВО
from Н_ЛЮДИ
         join Н_УЧЕНИКИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
         join Н_ПЛАНЫ on Н_УЧЕНИКИ.ПЛАН_ИД = Н_ПЛАНЫ.ИД
         join Н_ФОРМЫ_ОБУЧЕНИЯ on Н_ПЛАНЫ.ФО_ИД = Н_ФОРМЫ_ОБУЧЕНИЯ.ИД
where Н_ФОРМЫ_ОБУЧЕНИЯ.НАИМЕНОВАНИЕ = 'Заочная'
group by ОТЧЕСТВО
having count(*) < 50;





-- 5.	Выведите таблицу со средним возрастом студентов во всех группах (Группа, Средний возраст),
-- где средний возраст меньше минимального возраста в группе 1101.
select ГРУППА, avg(age(now(), ДАТА_РОЖДЕНИЯ))
from Н_ЛЮДИ
         join Н_УЧЕНИКИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
group by ГРУППА
having avg(age(now(), ДАТА_РОЖДЕНИЯ)) <
       (select min(age(now(), ДАТА_РОЖДЕНИЯ))
        from Н_ЛЮДИ
                 join Н_УЧЕНИКИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
        where Н_УЧЕНИКИ.ГРУППА = '1101');


-- select count(*) from (select ГРУППА, avg(age(now(), ДАТА_РОЖДЕНИЯ))
--                       from Н_ЛЮДИ
--                                join Н_УЧЕНИКИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
--                       group by ГРУППА
--                       having avg(age(now(), ДАТА_РОЖДЕНИЯ)) >
--                              (select min(age(now(), ДАТА_РОЖДЕНИЯ))
--                               from Н_ЛЮДИ
--                                        join Н_УЧЕНИКИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
--                               where Н_УЧЕНИКИ.ГРУППА = '1101')) as f;

-- select extract(year from avg(age(now(), ДАТА_РОЖДЕНИЯ)))
-- from Н_ЛЮДИ
--          join Н_УЧЕНИКИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
-- where Н_УЧЕНИКИ.ГРУППА = '1101';
--
--
-- select extract(year from min(age(now(), ДАТА_РОЖДЕНИЯ)))
-- from Н_ЛЮДИ
--          join Н_УЧЕНИКИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
-- where Н_УЧЕНИКИ.ГРУППА = '1101';
--
--
--
-- select avg(age(now(), ДАТА_РОЖДЕНИЯ))
-- from Н_ЛЮДИ
-- join Н_УЧЕНИКИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД;


-- 6.	Получить список студентов,
-- отчисленных ровно первого сентября 2012 года с заочной формы обучения.
-- В результат включить:
-- номер группы;
-- номер, фамилию, имя и отчество студента;
-- номер пункта приказа;
-- Для реализации использовать соединение таблиц.
select Н_УЧЕНИКИ.ГРУППА,
       Н_УЧЕНИКИ.ИД,
       Н_ЛЮДИ.ФАМИЛИЯ,
       Н_ЛЮДИ.ИМЯ,
       Н_ЛЮДИ.ОТЧЕСТВО,
       Н_УЧЕНИКИ.П_ПРКОК_ИД
from Н_УЧЕНИКИ
         join Н_ЛЮДИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
where Н_УЧЕНИКИ.ИД in
      (select Н_УЧЕНИКИ.ИД
       from Н_УЧЕНИКИ
       where ПРИЗНАК = 'отчисл'
         and СОСТОЯНИЕ = 'утвержден'
         and (КОГДА_СОЗДАЛ::DATE) = '2012-09-01'
    )
  and Н_УЧЕНИКИ.ИД in
    (
select Н_УЧЕНИКИ.ИД
from Н_ПЛАНЫ
    join Н_УЧЕНИКИ
on Н_УЧЕНИКИ.ПЛАН_ИД = Н_ПЛАНЫ.ИД
    join Н_ФОРМЫ_ОБУЧЕНИЯ on Н_ПЛАНЫ.ФО_ИД = Н_ФОРМЫ_ОБУЧЕНИЯ.ИД
where Н_ФОРМЫ_ОБУЧЕНИЯ.НАИМЕНОВАНИЕ = 'Заочная'
    );


---- попроще(этот вариант лучше всех)
select Н_УЧЕНИКИ.ГРУППА,
       Н_УЧЕНИКИ.ИД,
       Н_ЛЮДИ.ФАМИЛИЯ,
       Н_ЛЮДИ.ИМЯ,
       Н_ЛЮДИ.ОТЧЕСТВО,
       Н_УЧЕНИКИ.П_ПРКОК_ИД
from Н_УЧЕНИКИ
         join Н_ЛЮДИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
         join Н_ПЛАНЫ on Н_УЧЕНИКИ.ПЛАН_ИД = Н_ПЛАНЫ.ИД
         join Н_ФОРМЫ_ОБУЧЕНИЯ on Н_ПЛАНЫ.ФО_ИД = Н_ФОРМЫ_ОБУЧЕНИЯ.ИД
where ПРИЗНАК = 'отчисл'
  and СОСТОЯНИЕ = 'утвержден'
  and (Н_УЧЕНИКИ.КОГДА_СОЗДАЛ::DATE) = '2012-09-01'
  and НАИМЕНОВАНИЕ = 'Заочная';

--- вот тут уже не 0 строк будет
select Н_УЧЕНИКИ.ГРУППА,
       Н_УЧЕНИКИ.ИД,
       Н_ЛЮДИ.ФАМИЛИЯ,
       Н_ЛЮДИ.ИМЯ,
       Н_ЛЮДИ.ОТЧЕСТВО,
       Н_УЧЕНИКИ.П_ПРКОК_ИД
from Н_УЧЕНИКИ
         join Н_ЛЮДИ on Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
         join Н_ПЛАНЫ on Н_УЧЕНИКИ.ПЛАН_ИД = Н_ПЛАНЫ.ИД
         join Н_ФОРМЫ_ОБУЧЕНИЯ on Н_ПЛАНЫ.ФО_ИД = Н_ФОРМЫ_ОБУЧЕНИЯ.ИД
where ПРИЗНАК = 'отчисл'
  and СОСТОЯНИЕ = 'утвержден'
  and (Н_УЧЕНИКИ.КОГДА_СОЗДАЛ::DATE) < '2012-09-01'
  and НАИМЕНОВАНИЕ = 'Очная';

-- 7.	Сформировать запрос для получения числа в группе No 3100 хорошистов.
select count(*) as ЧИСЛО_ХОРОШИСТОВ_В_ГРУППЕ_3100
from Н_УЧЕНИКИ
         join Н_ВЕДОМОСТИ on Н_УЧЕНИКИ.ЧЛВК_ИД = Н_ВЕДОМОСТИ.ЧЛВК_ИД
where Н_УЧЕНИКИ.ГРУППА = '3100'
  and Н_ВЕДОМОСТИ.ОЦЕНКА = '4';