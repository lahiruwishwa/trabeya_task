create table request_log (id integer not null auto_increment, ip varchar(45), http_method varchar(6), http_status integer, path varchar(100), request_body varchar(1000), response varchar(1000), time datetime, primary key (id)) engine=InnoDB
