-- Create table Posts
create table posts (
  id int auto_increment,
  account_id int not null,
  created DATETIME not null,
  updated DATETIME default NULL null,
  title VARCHAR(255) not null,
  message TEXT null,
  image_code VARCHAR(255) default '' null,
  image_name VARCHAR(255) default '' null,
  image_path VARCHAR(255) default '' null,
  deleted bit default 0 null,
  constraint posts_pk primary key (id),
  constraint posts_accounts_id_fk foreign key (account_id) references accounts (id)
);

