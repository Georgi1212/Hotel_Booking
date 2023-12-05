insert into users (username, email, password, first_name, last_name, phone_number, date_of_birth, address, created_at, last_modified_date, is_enabled, user_type)
values ('Georgi', 'georgi@test.com', '123', 'Georgi', 'Tsekov', '0877568974', null, null, '2023-05-29 00:00:00.000000', '2023-05-29 00:00:00.000000', TRUE, 'USER');

insert into users (username, email, password, first_name, last_name, phone_number, date_of_birth, address, created_at, last_modified_date, is_enabled, user_type)
values ('Petar', 'petar@test.com', '123', 'Petar', 'Nikolov', '0877578974', null, null, '2023-05-29 00:00:00.000000', '2023-05-29 00:00:00.000000', TRUE, 'ADMIN');

------------------
-- here you have to check if the host_id is an admin (user can book and see the other hotels,
-- but the admin can add, update and delete its own place, which he will share in the platform
-- and see when the user has booked a room - observer pattern! and maybe see in his calendar the already booked rooms for a definite period)
insert into hotel (host_id, hotel_name, street, city, country, hotel_description, hotel_image, rate)
values (2, 'Ritz', 'ul. bla bla', 'Paris', 'France', null, null, 5.2); --can be added also coordinates of the place

--------------------
--here there are the room types
insert into room_size_type (room_type, room_capacity) values ('SINGLE', 1);
insert into room_size_type (room_type, room_capacity) values ('DOUBLE', 2);
insert into room_size_type (room_type, room_capacity) values ('TRIPLE', 3);
insert into room_size_type (room_type, room_capacity) values ('APARTMENT', 2);
insert into room_size_type (room_type, room_capacity) values ('APARTMENT', 3);
insert into room_size_type (room_type, room_capacity) values ('APARTMENT', 4);
insert into room_size_type (room_type, room_capacity) values ('PRESIDENTIAL', 1);
insert into room_size_type (room_type, room_capacity) values ('PRESIDENTIAL', 2);

------------------

insert into room (hotel_id, room_price, room_type, room_description, num_children, num_adults) values (1, 50.00, 1, null, 0, 1);
insert into room (hotel_id, room_price, room_type, room_description, num_children, num_adults) values (1, 150.00, 4, null, 0, 2);
insert into room (hotel_id, room_price, room_type, room_description, num_children, num_adults) values (1, 75.50, 3, null, 1, 2);

-------------------------
--created_date as a date must be equal ot less than check_in
insert into occupancy (room_id, check_in, check_out, created_at, deleted_at) values (1, '2023-05-29', '2023-05-31', '2023-05-29 00:00:00.000000', null);
--insert into occupancy (room_id, check_in, check_out, created_at, deleted_at) values (1, '2023-05-31', '2023-06-03', '2023-05-29 00:00:00.000000', null);

-------------------------
-- I have to get the check_in and check_out table, then I have to calculate the nights and after that to caluclate the (room price * nights));
-- maybe with using automatically calculating function ??
-- check id on the room's table - occupancy_id id not null, because when I book a room, the gets occupied (reserved)
insert into booking (occupancy_id, user_id, sum_price) values (1, 1, 100.0);
--room_id