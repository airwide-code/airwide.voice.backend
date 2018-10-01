
-- Tests

-- welcome

-- Welcome to the Agriculture Portal,
-- which educates farmers to improve their productivity of crops,
-- and help in producing with great gain and efficiency
-- Press 1 for English
-- Press 0 for Help Menu
-- Press* to repeat the Menu

-- test menu build
-- Press 1 for crops
-- Press 2 for poultry
-- press 9 to unsubscribe
-- Press * to repeat this menu

-- test menu build: crops
-- Press 1 for Potatoes
-- Press 9 to unsubscribe
-- Press * to repeat this menu

-- test menu build: poultry
-- Press 1 for Broilers
-- Press 9 to unsubscribe
-- Press * to repeat this menu

-- test menu build: control
-- Press 1 to listen to this session
-- press 2 to skip this show and go to the next section
-- press 3 to listen to the previous session
-- press 4 to repeat the session
-- press 0 for main menu
-- press * to repeat this menu again

-- media: welcome
create table ivr_services (service_id varchar(20), media_path varchar(255), primary key (service_id));
insert into ivr_services (service_id, media_path)
values
('789', 'welcome_agriculture_service');

-- media: controls

-- setup: bundle types
drop table bundle_types;
create table bundle_types (service_id varchar(20), bundle_id varchar(20), bundle_name varchar(20), bundle_price decimal(12,2), bundle_window bigint);
insert into bundle_types (service_id, bundle_id, bundle_name, bundle_price, bundle_window)
values
('789','1','monthly',1.00, 60 * 300),
('789','2','weekly',0.35, 60 * 80),
('789','3','daily',0.10, 60 * 15);

-- setup: subscriptions
drop table ivr_subscription;
create table ivr_subscription(id int not null AUTO_INCREMENT, service_id varchar(20), subscriber_id varchar(60), bundle_id varchar(20), balance_seconds bigint, balance_value decimal(12,2), update_date TIMESTAMP, primary key(id));

drop table product_media_tracker;
create table product_media_tracker (service_id varchar(20), subscriber_id varchar(60), category_id integer, product_id integer, current_track_id integer not null default 1, primary key (service_id, subscriber_id, category_id, product_id));

drop table categories;
create table categories (service_id varchar(20), category_id integer, category_name varchar(60), media_path varchar(255), primary key (service_id, category_id));
insert into categories (service_id, category_id, category_name, media_path)
values
('789', 1, 'crops', 'menu/crop'),
('789', 2, 'poultry', 'menu/poultry');

drop table products;
create table products (service_id varchar(20), category_id integer, product_id integer, product_name varchar(60), product_media_path varchar(255), primary key (service_id, category_id, product_id));
insert into products (service_id, category_id, product_id, product_name, product_media_path)
values
('789', 1, 1, 1, 'crops/potatoes'),
('789', 2, 2, 1, 'poultry/broilers');

drop table product_media;
create table product_media (service_id varchar(20), category_id varchar(20), product_id varchar(20), track_id integer, track_path varchar(60), track_title_path varchar(60), track_duration long, primary key (service_id, category_id, product_id, track_id));
insert into product_media (service_id, category_id, product_id, track_id, track_title_path, track_path, track_duration)
values
('789',1,1,1,'potato_title','01_potatoes_day_1_20_sprouting', 32),
('789',1,1,2,'potato_title','02_potatoes_day_21_planting', 34),
('789',1,1,3,'potato_title','03_potatoes_day_21_35_germination', 9),
('789',1,1,4,'potato_title','04_potatoes_day_35_49_germination', 14),
('789',1,1,5,'potato_title','05_potatoes_day_49_63_tuber_initiation',30),
('789',1,1,6,'potato_title','06_potatoes_day_63_153_tuber_bulking',15),
('789',1,1,7,'potato_title','07_potatoes_day_153_163_maturation',12),
('789',1,1,8,'potato_title','08_potatoes_day_153_163_harvesting',7);

insert into product_media (service_id, category_id, product_id, track_id, track_title_path, track_path, track_duration)
values
('789',2,1,1,'broiler_title','01_broiler_day_1', 92),
('789',2,1,2,'broiler_title','02_broiler_day_2', 92),
('789',2,1,3,'broiler_title','03_broiler_day_3', 4),
('789',2,1,4,'broiler_title','04_broiler_day_4', 6),
('789',2,1,5,'broiler_title','05_broiler_day_5', 7),
('789',2,1,6,'broiler_title','06_broiler_day_6', 8),
('789',2,1,7,'broiler_title','07_broiler_day_7', 25),
('789',2,1,8,'broiler_title','08_broiler_day_8_13', 9),
('789',2,1,9,'broiler_title','09_broiler_day_14', 50),
('789',2,1,10,'broiler_title','10_broiler_day_15_20', 6),
('789',2,1,11,'broiler_title','11_broiler_day_21', 72),
('789',2,1,12,'broiler_title','12_broiler_day_42',21);
