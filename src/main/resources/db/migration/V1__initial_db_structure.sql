

create table areas_of_availability
(
	id bigint auto_increment
		primary key,
	city varchar(255) null,
	country varchar(255) null
);

create table authentications
(
	id bigint auto_increment
		primary key,
	password varchar(255) null,
	user_name varchar(255) null
);

create table categories
(
	id bigint auto_increment
		primary key,
	category_name varchar(255) null
);

create table users
(
	id bigint auto_increment
		primary key,
	email varchar(255) null,
	first_name varchar(255) null,
	last_name varchar(255) null,
	phone varchar(255) null,
	authentication_id bigint null,
	constraint FK_users_authentication_id
		foreign key (authentication_id) references authentications (id)
);

create table donations
(
	id bigint auto_increment
		primary key,
	description varchar(255) null,
	status varchar(255) null,
	title varchar(255) null,
	area_id bigint null,
	category_id bigint null,
	donor_id bigint null,
	constraint FK_donations_area_id
		foreign key (area_id) references areas_of_availability (id),
	constraint FK_donations_category_id
		foreign key (category_id) references categories (id),
	constraint FK_donations_donor_id_to_users
		foreign key (donor_id) references users (id)
);

create table donation_pictures
(
	id bigint auto_increment
		primary key,
	picture mediumblob null,
	donation_id bigint null,
	constraint FK_pictures_donation_id
		foreign key (donation_id) references donations (id)
);

create table requests
(
	donation_id bigint not null,
	user_id bigint not null,
	primary key (donation_id, user_id),
	constraint FK_requests_donation_id
		foreign key (donation_id) references donations (id),
	constraint FK_requests_user_id
		foreign key (user_id) references users (id)
);

create table transactions
(
	id bigint auto_increment
		primary key,
	donation_id bigint null,
	receiver_id bigint null,
	constraint FK_transactions_receiver_id_to_users
		foreign key (receiver_id) references users (id)
);

