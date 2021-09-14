CREATE TABLE city_weather
    (
        city_id bigint(10) not null,
	    city_name varchar(50) not null,
        weather_main varchar(50),
        wind_speed decimal(5,2),
        temp decimal(5,2),
        feels_like decimal(5,2),
        temp_min decimal(5,2),
        temp_max decimal(5,2),
        pressure bigint(10),
        humidity bigint(10),
        created_timestamp datetime,
        updated_timestamp datetime,
        primary key (city_id)
    );

CREATE TABLE user_details
    (
        id bigint(10) not null auto_increment,
        name varchar(50) not null,
        email varchar(50) not null,
        created_timestamp datetime,
        updated_timestamp datetime,
        primary key (id)
    );

CREATE TABLE city_config
    (
        city_id bigint(10) not null,
	    city_name varchar(50) not null,
        created_timestamp datetime,
        updated_timestamp datetime,
        primary key (city_id)
    );

CREATE TABLE weather_profile
    (
        id bigint(10) not null auto_increment,
        user_id bigint(10) not null,
        profile_name varchar(50) not null,
        created_timestamp datetime,
        updated_timestamp datetime,
        primary key (id)
    );

CREATE TABLE profile_city_map
    (
        profile_id bigint(10),
        city_id bigint(10),
        created_timestamp datetime,
        updated_timestamp datetime,
        primary key (profile_id, city_id)
    );

alter table profile_city_map
  add constraint profile_city_map_uk unique (profile_id, city_id);

insert into city_config (city_id, city_name,created_timestamp, updated_timestamp)
  values ('2147714', 'Sydney', now(), now());
insert into city_config (city_id, city_name,created_timestamp, updated_timestamp)
  values ('2158177', 'Melbourne', now(), now());
insert into city_config (city_id, city_name,created_timestamp, updated_timestamp)
  values ('2174003', 'Brisbane', now(), now());
insert into city_config (city_id, city_name,created_timestamp, updated_timestamp)
  values ('2172517', 'Canberra', now(), now());
insert into city_config (city_id, city_name,created_timestamp, updated_timestamp)
  values ('2078025', 'Adelaide', now(), now());
insert into city_config (city_id, city_name,created_timestamp, updated_timestamp)
  values ('2063523', 'Perth', now(), now());
insert into city_config (city_id, city_name,created_timestamp, updated_timestamp)
  values ('2073124', 'Darwin', now(), now());
