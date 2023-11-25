CREATE TABLE users
(
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    username            VARCHAR(32)           NOT NULL,
    email               VARCHAR(64)    UNIQUE NOT NULL,
    password            VARCHAR(255)          NOT NULL,
    first_name          VARCHAR(32),
    last_name           VARCHAR(32),
    phone_number        VARCHAR(15)           NOT NULL,
    date_of_birth       DATE,
    address             VARCHAR(64),
    created_at          TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date  TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_enabled          BOOLEAN               NOT NULL,
    user_type           VARCHAR(5)            NOT NULL --user or admin (constraint)
);

CREATE TABLE hotel
(
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    host_id             BIGINT                NOT NULL,
    hotel_name          VARCHAR(255)          NOT NULL,
    street              VARCHAR(64) UNIQUE    NOT NULL,
    city                VARCHAR(64)           NOT NULL,
    country             VARCHAR(100)          NOT NULL,
    rate                DECIMAL               NOT NULL, --constraint (from 1 to 5, ex. 3.5)
    is_pet_available    BOOLEAN,
    FOREIGN KEY (host_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE TABLE room_size_type
(
    id                 BIGSERIAL PRIMARY KEY NOT NULL,
    room_type          VARCHAR(15)           NOT NULL, --constraint (single, double, triple, apartment, presidential)
    room_capacity      INTEGER                         --constraint (1-4)
);

CREATE TABLE room
(
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    hotel_id            BIGINT                NOT NULL, --FK
    room_price          DECIMAL               NOT NULL,
    room_type           BIGINT                NOT NULL, --FK
    room_description    VARCHAR(500),
    num_children        INTEGER               NOT NULL,
    num_adults          INTEGER               NOT NULL,
    FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE,
    FOREIGN KEY (room_type) REFERENCES room_size_type (id) ON DELETE CASCADE
);

CREATE TABLE room_image
(
    id                 BIGSERIAL PRIMARY KEY NOT NULL,
    room_id            BIGINT                NOT NULL,
    image_url          VARCHAR(255),
    FOREIGN KEY (room_id) REFERENCES room (id) ON DELETE CASCADE
);

CREATE TABLE occupancy
(
    id                 BIGSERIAL PRIMARY KEY NOT NULL,
    room_id            BIGINT,
    check_in           DATE                  NOT NULL DEFAULT CURRENT_DATE,
    check_out          DATE                  NOT NULL DEFAULT CURRENT_DATE + INTERVAL '1 DAY', --constraint (check_out after check_in - at least 1 night)
    created_at         TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at         TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES room (id) ON DELETE CASCADE
);

CREATE TABLE booking
(
    id                 BIGSERIAL PRIMARY KEY NOT NULL,
    room_id            BIGINT                NOT NULL,
    user_id            BIGINT                NOT NULL,
    sum_price          DECIMAL               NOT NULL, --CALCULATE DATES FROM OCCUPANCY TABLE
    created_at       TIMESTAMP             NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (room_id) REFERENCES room (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);