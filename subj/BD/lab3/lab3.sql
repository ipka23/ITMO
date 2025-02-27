drop table if exists Person cascade;
drop table if exists Robot cascade;
drop table if exists Action cascade;
drop table if exists Location cascade;
drop table if exists Event cascade;
drop table if exists Signalization cascade;
drop table if exists Corridor cascade;
drop table if exists Characters cascade;

drop type if exists person_sex cascade;
drop type if exists character_type cascade;
create type person_sex as enum ('Male', 'Female');
create type character_type as enum ('Person', 'Robot-helper', 'Robot-security');


create table Action
(
    id   int primary key,
    name text
);
create table Location
(
    id   int primary key,
    name text
);
create table Characters
(
    id   int primary key,
    name varchar(20)
);

create table Event
(
    id             int primary key,
    Action_id      int not null references Action (id),
    Location_id    int not null references Location (id),
    Characters_id  int not null references Characters (id),
    begin_time     timestamp,
    end_time       timestamp,
    character_type character_type
);
create table Person
(
    id  int references Characters (id),
    sex person_sex,
    age int check (age >= 0 and age <= 130)
);
create table Robot
(
    id      int references Characters (id),
    purpose text
);
create table Signalization
(
    Location_id int references Location (id),
    is_active   boolean
);
create table Corridor
(
    Location_id_1 int not null references Location (id),
    Location_id_2 int not null references Location (id),
    check (Location_id_1 != Location_id_2)
    );




insert into Location(id, name)
values (1, 'Променад');
insert into Location(id, name)
values (2, 'Столовая');
insert into Location(id, name)
values (3, 'Шлюз');

insert into Action(id, name)
values (1, 'Пылесосить');
insert into Action(id, name)
values (2, 'Кушать');
insert into Action(id, name)
values (3, 'Курить');
insert into Action(id, name)
values (4, 'Охранять');

insert into Characters(id, name)
values (1, 'Чандра');
insert into Characters(id, name)
values (2, 'Cандра');
insert into Characters(id, name)
values (3, 'ЭАЛ');
insert into Characters(id, name)
values (4, 'ПЭЛ');


drop trigger if exists check_action on Event;


create or replace function check_character_action()
returns trigger as
$$
begin
   if (new.Action_id = 1 and new.character_type = 'Robot-security') then raise exception 'Робот-охранник не может выполнять действие "Пылесосить"!';
   elsif (new.Action_id = 2 and new.character_type = 'Robot-security') then raise exception 'Робот-охранник не может выполнять действие "Кушать"!';
   elsif (new.Action_id = 2 and new.character_type = 'Robot-helper') then raise exception 'Робот-помощник не может выполнять действие "Кушать"!';
   elsif (new.Action_id = 3 and new.character_type = 'Robot-security') then raise exception 'Робот-охранник не может выполнять действие "Курить"!';
   elsif (new.Action_id = 3 and new.character_type = 'Robot-helper') then raise exception 'Робот-помощник не может выполнять действие "Курить"!';
   elsif (new.Action_id = 4 and new.character_type = 'Robot-helper') then raise exception 'Робот-помощник не может выполнять действие "Охранять"!';
end if;
return new;
end;
$$ language plpgsql;

create trigger check_action
before insert or update on Event
for each row execute function check_character_action();

insert into Event(id, Action_id, Location_id, Characters_id, begin_time, end_time, character_type)
values (2, 3, 3, 1, '2125-02-23 22:15:23', '2125-02-23 22:20:12', 'Person');
insert into Event(id, Action_id, Location_id, Characters_id, begin_time, end_time, character_type)
values (1, 2, 2, 2, '2125-02-23 15:00:11', '2125-02-23 15:30:33', 'Person');
insert into Event(id, Action_id, Location_id, Characters_id, begin_time, end_time, character_type)
values (3, 1, 1, 3, '2125-02-23 18:00:00', '2125-02-23 18:10:00', 'Robot-helper');
insert into Event(id, Action_id, Location_id, Characters_id, begin_time, end_time, character_type)
values (4, 4, 1, 4, '2120-12-01 18:00:00', NULL, 'Robot-security');





insert into Person(id, sex, age)
values (1, 'Male', 30);
insert into Person(id, sex, age)
values (2, 'Female', 25);

insert into Robot(id, purpose)
values (3, 'Робот-помощник');
insert into Robot(id, purpose)
values (4, 'Робот-охранник');

insert into Signalization(Location_id, is_active)
values (1, FALSE);
insert into Signalization(Location_id, is_active)
values (2, FALSE);
insert into Signalization(Location_id, is_active)
values (3, TRUE);

insert into Corridor(Location_id_1, Location_id_2)
values (1, 2);
insert into Corridor(Location_id_1, Location_id_2)
values (1, 3);