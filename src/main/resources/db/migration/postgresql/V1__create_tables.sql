create table user_info (
    id SERIAL PRIMARY KEY,
    first_name varchar(255),
    last_name varchar(255),
    patronymic varchar(255),
    birthday timestamp,
    email varchar(255) not null CONSTRAINT user_email_unique UNIQUE,
    phone_number varchar(10)
);

create table images (
    id SERIAL PRIMARY KEY,
    url varchar(255) not null
);


create table users (
    id SERIAL PRIMARY KEY,
    username varchar(255) not null CONSTRAINT user_username_unique UNIQUE,
    password varchar(255) not null,
    enabled boolean not null,
    last_password_reset_date timestamp,
    user_info_id integer REFERENCES user_info(id),
    image_id integer REFERENCES images(id)
);

create table roles (
    id SERIAL PRIMARY KEY,
    name varchar(255) not null CONSTRAINT role_name_unique UNIQUE
);

create table user_role (
    user_id integer REFERENCES users(id),
    role_id integer REFERENCES roles(id)
);