ALTER TABLE users
ADD CONSTRAINT check_user_type CHECK (user_type IN ('admin', 'user'));

ALTER TABLE hotel
ADD CONSTRAINT check_hotel_rate CHECK (rate >= 1 AND rate <= 5);

ALTER TABLE room_size_type
ADD CONSTRAINT check_room_capacity CHECK (room_capacity >= 1 AND room_capacity <= 6);

ALTER TABLE room_size_type
ADD CONSTRAINT check_room_type CHECK (room_type IN ('single', 'double', 'triple', 'apartment', 'presidential'));

ALTER TABLE occupancy
ADD CONSTRAINT check_dates CHECK (to_date >= from_date + INTERVAL '1 DAY');