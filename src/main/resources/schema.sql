create table if not exists app_db.permission
(
	id bigint auto_increment
		primary key,
	url varchar(255) not null
);

create table if not exists app_db.role
(
	id bigint auto_increment
		primary key,
	name varchar(255) not null
);

create table if not exists app_db.role_permission
(
	id bigint auto_increment
		primary key,
	permission_id bigint not null,
	role_id bigint not null,
	constraint FKa6jx8n8xkesmjmv6jqug6bg68
		foreign key (role_id) references app_db.role (id),
	constraint FKf8yllw1ecvwqy3ehyxawqa1qp
		foreign key (permission_id) references app_db.permission (id)
);

create table if not exists app_db.user
(
	id bigint auto_increment
		primary key,
	email varchar(255) null,
	is_account_non_expired bit not null,
	is_account_non_locked bit not null,
	is_credentials_non_expired bit not null,
	is_enabled bit not null,
	password varchar(255) not null,
	username varchar(255) not null,
	constraint UK_ob8kqyqqgmefl0aco34akdtpe
		unique (email),
	constraint UK_sb8bbouer5wak8vyiiy4pf2bx
		unique (username)
);

create table if not exists app_db.user_role
(
	id bigint auto_increment
		primary key,
	role_id bigint not null,
	user_id bigint not null,
	constraint FK859n2jvi8ivhui0rl0esws6o
		foreign key (user_id) references app_db.user (id),
	constraint FKa68196081fvovjhkek5m97n3y
		foreign key (role_id) references app_db.role (id)
);
