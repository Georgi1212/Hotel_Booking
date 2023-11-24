ALTER TABLE users
ADD CONSTRAINT check_user_type CHECK (user_type IN ('admin', 'user'));

ALTER TABLE users
ADD CONSTRAINT check_unique_email UNIQUE (email);

ALTER TABLE hotel
ADD CONSTRAINT check_hotel_rate CHECK (rate >= 1 AND rate <= 5);

ALTER TABLE hotel
ADD CONSTRAINT check_hotel_address UNIQUE (address);

ALTER TABLE room_size_type
ADD CONSTRAINT check_room_capacity CHECK (room_capacity >= 1 AND room_capacity <= 4);

ALTER TABLE room_size_type
ADD CONSTRAINT check_room_type CHECK (room_type IN ('single', 'double', 'triple', 'apartment', 'presidential'));

/* check that on the service side
ALTER TABLE room
ADD CONSTRAINT check_capacity CHECK ((SELECT room_capacity FROM room_size_type rst JOIN room r ON rst.id = r.room_type) >= (num_adults + num_children));
*/
CREATE EXTENSION btree_gist;
ALTER TABLE occupancy
ADD CONSTRAINT prevent_double_room_bookings EXCLUDE USING gist
(
  room_id WITH =,
  daterange(check_in, check_out, '[)') WITH &&
) WHERE (deleted_at IS NULL);

ALTER TABLE occupancy
ADD CONSTRAINT check_dates CHECK (check_out >= check_in + INTERVAL '1 DAY');