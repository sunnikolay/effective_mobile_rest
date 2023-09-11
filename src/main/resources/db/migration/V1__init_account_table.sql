create table accounts (
  id int auto_increment,
  username VARCHAR(24) not null,
  full_name VARCHAR(100) null COLLATE utf8_general_ci,
  email VARCHAR(32) not null,
  password VARCHAR(255) not null,
  active BIT default 1 not null,
  constraint accounts_pk primary key (id)
);
create unique index accounts_email_uindex on accounts (email);
create unique index accounts_username_uindex on accounts (username);

create table roles (
  account_id INT NOT NULL ,
  role_name VARCHAR(255),
  FOREIGN KEY (account_id) REFERENCES accounts(id)
);

INSERT INTO accounts (username, full_name, email, password, active)
VALUES ('root', 'Administrator', 'admin@mail.com', '$2a$12$dqMl8aZ/cfaAiS9ea6heQuhVDmHUoZqAoMfFcIP2OYINLfG2Us/Zu',TRUE);

INSERT INTO roles (account_id, role_name) VALUES ( 1, 'ADMIN');