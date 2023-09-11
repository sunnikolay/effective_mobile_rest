-- Create table Following
create table followers (
  id int auto_increment,
  created DATETIME NOT NULL,
  account_from int not null,
  account_to int not null,
  constraint followers_pk primary key (id),
  constraint followers_account_from_fk foreign key (account_from) references accounts (id),
  CONSTRAINT followers_account_to_fk FOREIGN KEY (account_to) REFERENCES accounts (id)
);