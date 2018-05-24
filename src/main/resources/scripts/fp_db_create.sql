SET FOREIGN_KEY_CHECKS=0;

drop table if exists confs;

drop table if exists reports;

drop table if exists roles;

drop table if exists users;

drop table if exists users_reports;
SET FOREIGN_KEY_CHECKS=1;

create table confs
(
  conf_id              int not null AUTO_INCREMENT,
  conf_name            varchar(44) not null,
  conf_place           varchar(44) not null,
  confDate            timestamp not null,
  primary key (conf_id)
)
  CHARACTER SET utf8 COLLATE utf8_unicode_ci;

create table reports
(
  report_id            int not null AUTO_INCREMENT,
  conf_id              int not null,
  report_name          varchar(22) not null,
  report_desk          varchar(255) not null,
  primary key (report_id)
);

create table roles
(
  role_id              int not null AUTO_INCREMENT,
  role_name            varchar(22) not null,
  primary key (role_id)
)
  CHARACTER SET utf8 COLLATE utf8_unicode_ci;

create table users
(
  user_id              int not null AUTO_INCREMENT,
  role_id              int not null,
  email                varchar(44) not null UNIQUE ,
  login                varchar(44) not null UNIQUE ,
  password             varchar(44) not null,
  rating               int DEFAULT 0,
  primary key (user_id)
)
  CHARACTER SET utf8 COLLATE utf8_unicode_ci;

create table users_reports
(
  user_id              int not null,
  report_id            int not null,
  active_speaker       BOOLEAN DEFAULT FALSE,
  by_speaker           BOOLEAN DEFAULT FALSE,
  by_moder             BOOLEAN DEFAULT FALSE,
  confirmed            BOOLEAN DEFAULT FALSE,
  rating               int DEFAULT 0,
  primary key (user_id, report_id)
)
  CHARACTER SET utf8 COLLATE utf8_unicode_ci;

alter table reports add constraint FK_Relationship_2 foreign key (conf_id)
references confs (conf_id) on delete restrict on update restrict;

alter table users add constraint FK_Relationship_1 foreign key (role_id)
references roles (role_id) on delete restrict on update restrict;

alter table users_reports add constraint FK_Relationship_4 foreign key (user_id)
references users (user_id) on delete restrict on update restrict;

alter table users_reports add constraint FK_Relationship_5 foreign key (report_id)
references reports (report_id) on delete restrict on update restrict;

