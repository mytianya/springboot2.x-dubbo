create table tb_user(
	 user_id int(11) not null auto_increment,
	 user_name varchar(50),
	 user_account VARCHAR(80) not null unique,
	 user_status int(2) not null DEFAULT 0,
	 user_password varchar (80) not null,
	 user_create_time datetime,
	 user_last_login_time datetime,
	 PRIMARY key (user_id)
)ENGINE=Innodb DEFAULT CHARSET=utf8;