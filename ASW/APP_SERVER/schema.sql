DROP TABLE IF EXISTS users;
create table users(
uid int primary key auto_increment,
username char(20),
password char(40),
email char(60)
);
insert into users(uid,username,password,email) values(1,"Anonymous","---","anonymous@localhost");
DROP TABLE IF EXISTS messages;
create table messages(
mid int primary key auto_increment,
owner int,
message text
);

DROP TABLE IF EXISTS labels;
create table labels(
lid int primary key auto_increment,
title varchar(50),
importance int default 1 /*importance default 1, user messages*/
);

DROP TABLE IF EXISTS msg_lbl;
create table msg_lbl(
mid int,
lid int,
uid int
);

DROP TABLE IF EXISTS images;
create table images(
name varchar(20),
mid int
);

DROP TABLE IF EXISTS ignored;
create table ignored(
uid int,
uid_ign int/*who will be forever ignored*/
);


insert into labels(title,importance) values ("Funny",6);
insert into labels(title,importance) values ("Important",5);
insert into labels(title,importance) values ("Insightful",4);
insert into labels(title,importance) values ("Fake",3);
insert into labels(title,importance) values ("SPAM",2);


insert into users(username,password,email) values("admin","08506d2487e78caf8d27ef22cfe5e3d436160d5f","admin@co.za");
insert into users(username,password,email) values("member1","08506d2487e78caf8d27ef22cfe5e3d436160d5f","admin@whitehouse.gov");
insert into users(username,password,email) values("member2","08506d2487e78caf8d27ef22cfe5e3d436160d5f","admin@nsa.gov");

/*passw*/
insert into messages(message,owner) values("VGhpcyBpcyBzcGFydGE=",1);
insert into messages(message,owner) values("J1dlbGNvbWUnIGlzIGEgcG93ZXJmdWwgd29yZC4=",1);
insert into messages(message,owner) values("QSBkdWJpb3VzIGZyaWVuZCBtYXkgYmUgYW4gZW5lbXkgaW4gY2Ftb3VmbGFnZS4=",1);
insert into messages(message,owner) values("QSBmZWF0aGVyIGluIHRoZSBoYW5kIGlzIGJldHRlciB0aGFuIGEgYmlyZCBpbiB0aGUgYWlyLg==",2);
insert into messages(message,owner) values("QSBmcmVzaCBzdGFydCB3aWxsIHB1dCB5b3Ugb24geW91ciB3YXku",2);
insert into messages(message,owner) values("QSBmcmllbmQgYXNrcyBvbmx5IGZvciB5b3VyIHRpbWUgbm90IHlvdXIgbW9uZXku",2);
insert into messages(message,owner) values("QSBmcmllbmQgaXMgYSBwcmVzZW50IHlvdSBnaXZlIHlvdXJzZWxmLg==",1);
insert into messages(message,owner) values("QSBnYW1ibGVyIG5vdCBvbmx5IHdpbGwgbG9zZSB3aGF0IGhlIGhhcywgYnV0IGFsc28gd2lsbCBsb3NlIHdoYXQgaGUgZG9lc26SdCBoYXZlLg==",2);

insert into msg_lbl(mid,lid,uid) values(8,2,1);
insert into msg_lbl(mid,lid,uid) values(8,2,2);
insert into msg_lbl(mid,lid,uid) values(8,5,2);
insert into msg_lbl(mid,lid,uid) values(8,3,2);

INSERT INTO images('name','mid') values('wr.gif', '7');

insert into ignored(uid,uid_ign) values(2,1);
insert into ignored(uid,uid_ign) values(2,3);
insert into ignored(uid,uid_ign) values(2,4);


