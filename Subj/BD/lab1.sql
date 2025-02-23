drop table if exists Person cascade;
drop table if exists Robot cascade;
drop table if exists Location cascade;
drop table if exists Action cascade;
drop table if exists Signalization cascade;
drop table if exists Corridor cascade;

create type person_sex as enum ('Male', 'Female');

create table Person(id serial primary key, name varchar(20), sex person_sex, age int check (age >= 0 and age <= 130), Location_id int references Location(id), Action_id int references Action(id));
create table Robot(id serial primary key, name varchar(50), purpose text, Location_id int references Location(id), Action_id int references Action(id));
create table Location(id int primary key, name varchar(50));
create table Action(id int primary key, name varchar(50), begin_time timestamp, end_time timestamp);
create table Signalization(Location_id int references Location(id), is_active boolean);
create table Corridor(Location_id_1 int references Location(id), Location_id_2 int references Location(id), check (Location_id_1 != Location_id_2));

insert into Person(name, sex, age, Location_id, Action_id) values('Чандра', 'Male', 30, 3, 3);
insert into Person(name, sex, age, Location_id, Action_id) values('Cандра', 'Female', '25', 2, 2);


insert into Robot(name, purpose, Location_id, Action_id) values('ЭАЛ', 'Робот-помощник', 1, 1);
insert into Robot(name, purpose, Location_id, Action_id) values('ПЭЛ', 'Робот-охранник', 3, 4);

insert into Location(name) values(1, 'Променад');
insert into Location(name) values(2, 'Столовая');
insert into Location(name) values(3, 'Шлюз');

insert into Action(name, begin_time, end_time) values(1, 'Пылесосить', '2125-02-23 18:00:00', '2125-02-23 18:10:00');
insert into Action(name, begin_time, end_time) values(2, 'Кушать', '2125-02-23 15:00:11', '2125-02-23 15:30:33');
insert into Action(name, begin_time, end_time) values(3, 'Курить', '2125-02-23 22:15:23', '2125-02-23 22:20:12');
insert into Action(name, begin_time, end_time) values(4, 'Охранять', '2120-12-01 18:00:00', NULL);

insert into Signalization(Location_id, is_active) values(1, TRUE);
insert into Signalization(Location_id, is_active) values(2, FALSE);
insert into Signalization(Location_id, is_active) values(3, FALSE);

insert into Corridor(Location_id_1, Location_id_2) values (1, 2);
insert into Corridor(Location_id_1, Location_id_2) values (1, 3);
