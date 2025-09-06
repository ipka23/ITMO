-- 1.	Сделать запрос для получения атрибутов из указанных таблиц, применив фильтры по указанным условиям:
-- Таблицы: Н_ОЦЕНКИ, Н_ВЕДОМОСТИ.
-- Вывести атрибуты: Н_ОЦЕНКИ.ПРИМЕЧАНИЕ, Н_ВЕДОМОСТИ.ИД.
-- Фильтры (AND):
-- a) Н_ОЦЕНКИ.КОД > 5.
-- b) Н_ВЕДОМОСТИ.ДАТА = 2010-06-18.
-- Вид соединения: RIGHT JOIN.

select Н_ОЦЕНКИ.ПРИМЕЧАНИЕ, Н_ВЕДОМОСТИ.ИД
from Н_ВЕДОМОСТИ
         right join Н_ОЦЕНКИ on Н_ВЕДОМОСТИ.ОЦЕНКА = Н_ОЦЕНКИ.КОД
where Н_ОЦЕНКИ.КОД ~ '^\d+$'
    and Н_ОЦЕНКИ.КОД::integer < 5
    and Н_ВЕДОМОСТИ.ДАТА::date = '2010-06-18';


EXPLAIN ANALYZE 1 запроса:
QUERY PLAN
-----------------------------------------------------------------------------
 Nested Loop  (cost=273.82..4812.77 rows=124 width=422) (actual time=2.222..32.640 rows=94 loops=1)
   ->  Seq Scan on "Н_ОЦЕНКИ"  (cost=0.00..1.18 rows=1 width=452) (actual time=0.040..0.063 rows=3 loops=1)
         Filter: ((("КОД")::text ~ '^\d+$'::text) AND (("КОД")::integer < 5))
         Rows Removed by Filter: 6
   ->  Bitmap Heap Scan on "Н_ВЕДОМОСТИ"  (cost=273.82..4810.35 rows=124 width=10) (actual time=1.417..10.840 rows=31 loops=3)
         Recheck Cond: (("ОЦЕНКА")::text = ("Н_ОЦЕНКИ"."КОД")::text)
         Filter: (("ДАТА")::date = '2010-06-18'::date)
         Rows Removed by Filter: 25127
         Heap Blocks: exact=8770
         ->  Bitmap Index Scan on "ВЕД_ОЦЕНКА_I"  (cost=0.00..273.79 rows=24716 width=0) (actual time=1.038..1.038 rows=25158 loops=3)
               Index Cond: (("ОЦЕНКА")::text = ("Н_ОЦЕНКИ"."КОД")::text)
 Planning Time: 0.312 ms
 Execution Time: 32.699 ms
(13 строк)

-- т.к. людей с одной оценкой много, создадим индекс для оценки чтобы поиск выполнялся только по строкам с нужной оценкой а также для более быстрого соединения таблиц
create index grade_index on Н_ОЦЕНКИ (КОД);

-- т.к. дат очень много, нужен индекс для того чтобы не перебирать все даты с 1998 г.
create index date_index on Н_ВЕДОМОСТИ (ДАТА);



-- 2.	Сделать запрос для получения атрибутов из указанных таблиц, применив фильтры по указанным условиям:
-- Таблицы: Н_ЛЮДИ, Н_ОБУЧЕНИЯ, Н_УЧЕНИКИ.
-- Вывести атрибуты: Н_ЛЮДИ.ФАМИЛИЯ, Н_ОБУЧЕНИЯ.ЧЛВК_ИД, Н_УЧЕНИКИ.НАЧАЛО.
-- Фильтры: (AND)
-- a) Н_ЛЮДИ.ИД < 100012.
-- b) Н_ОБУЧЕНИЯ.НЗК > 001000.
-- c) Н_УЧЕНИКИ.ГРУППА = 4103.
-- Вид соединения: RIGHT JOIN.


select Н_ЛЮДИ.ФАМИЛИЯ, Н_ОБУЧЕНИЯ.ЧЛВК_ИД, Н_УЧЕНИКИ.НАЧАЛО
from Н_УЧЕНИКИ
    right join Н_ОБУЧЕНИЯ on Н_ОБУЧЕНИЯ.ЧЛВК_ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
    right join Н_ЛЮДИ on Н_ЛЮДИ.ИД = Н_ОБУЧЕНИЯ.ЧЛВК_ИД
where Н_ЛЮДИ.ИД < 100012
    and Н_ОБУЧЕНИЯ.НЗК::integer > 001000
    and Н_УЧЕНИКИ.ГРУППА = '4103';

EXPLAIN ANALYZE 2 запроса:
QUERY PLAN
-----------------------------------------------------------------------------
 Nested Loop  (cost=0.85..18.48 rows=1 width=28) (actual time=0.005..0.007 rows=0 loops=1)
   ->  Nested Loop  (cost=0.56..16.62 rows=1 width=24) (actual time=0.005..0.006 rows=0 loops=1)
         ->  Index Scan using "ЧЛВК_PK" on "Н_ЛЮДИ"  (cost=0.28..8.30 rows=1 width=20) (actual time=0.004..0.005 rows=0 loops=1)
               Index Cond: ("ИД" < 100012)
         ->  Index Scan using "ОБУЧ_ЧЛВК_FK_I" on "Н_ОБУЧЕНИЯ"  (cost=0.28..8.31 rows=1 width=4) (never executed)
               Index Cond: ("ЧЛВК_ИД" = "Н_ЛЮДИ"."ИД")
               Filter: (("НЗК")::integer > 1000)
   ->  Index Scan using "УЧЕН_ОБУЧ_FK_I" on "Н_УЧЕНИКИ"  (cost=0.29..1.85 rows=1 width=12) (never executed)
         Index Cond: ("ЧЛВК_ИД" = "Н_ОБУЧЕНИЯ"."ЧЛВК_ИД")
         Filter: (("ГРУППА")::text = '4103'::text)
 Planning Time: 0.697 ms
 Execution Time: 0.053 ms
(12 строк)

-- для более быстрого соединения
create index person_id_index_1 on Н_УЧЕНИКИ (ЧЛВК_ИД);

create index person_id_index_2 on Н_ОБУЧЕНИЯ (ЧЛВК_ИД);

create index person_id_index_3 on Н_ЛЮДИ (ИД);

-- для поиска соответствия сразу в нужной нам группе (B-tree)
create index group_index on Н_УЧЕНИКИ (ГРУППА);
-- для быстрого перехода к группе кортежей которые удовлетворяют условию (B-tree)
create index nzk_index on Н_ОБУЧЕНИЯ (НЗК);







