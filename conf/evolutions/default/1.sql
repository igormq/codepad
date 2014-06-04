# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table block (
  id                        bigint not null,
  pad_id                    bigint not null,
  type                      varchar(255),
  content                   text,
  constraint pk_block primary key (id))
;

create table pad (
  id                        bigint not null,
  title                     varchar(255),
  constraint pk_pad primary key (id))
;

create sequence block_seq;

create sequence pad_seq;

alter table block add constraint fk_block_pad_1 foreign key (pad_id) references pad (id);
create index ix_block_pad_1 on block (pad_id);



# --- !Downs

drop table if exists block cascade;

drop table if exists pad cascade;

drop sequence if exists block_seq;

drop sequence if exists pad_seq;

