create sequence user_id_seq start with 1 increment by 1;
create sequence role_id_seq start with 1 increment by 1;
create sequence user_details_id_seq start with 1 increment by 1;
create sequence image_id_seq start with 1 increment by 1;

create table users (
    id bigint DEFAULT nextval('user_id_seq') not null,
    username varchar(255) not null CONSTRAINT user_username_unique UNIQUE,
    password varchar(255) not null,
    enabled boolean not null,
    last_password_reset_date timestamp,
    user_detail_id REFERENCES user_details(id),
    image_id REFERENCES images(id),
    primary key (id)
);

create table roles (
    id bigint DEFAULT nextval('role_id_seq') not null,
    name varchar(255) not null CONSTRAINT role_name_unique UNIQUE,
    primary key (id)
);

create table user_role (
    user_id bigint REFERENCES users(id),
    role_id bigint REFERENCES roles(id)
);

create table user_details (
    id bigint DEFAULT nextval('user_details_id_seq') not null,
    first_name varchar(255),
    last_name varchar(255),
    patronymic varchar(255),
    birthday timestamp,
    email varchar(255) not null CONSTRAINT user_email_unique UNIQUE,
    phone_number varchar(10),
    primary key (id)
);

create table images (
    id bigint DEFAULT nextval('image_id_seq') not null,
    url varchar(255) not null,
    primary key (id)
);