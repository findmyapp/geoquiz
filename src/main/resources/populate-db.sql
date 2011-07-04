CREATE table place(id int, name varchar(100), PRIMARY KEY(id));
INSERT into place values(
	0,
	'NTNU'
);
CREATE table event(id int, title varchar(100), event_date date, place_id int, is_open boolean, PRIMARY KEY(id));
INSERT into event values(
	0,
	'Bedpres ntnu',
	'2011-10-10',
	0,
	'true'
);
CREATE table question(id int, question varchar(255), answer varchar(100), PRIMARY KEY(id));
INSERT into question values(
	0,
	'intvalue("leet")=?',
	'1337'
);
CREATE table event_question(question_id int, event_id int, post_num int, description varchar(255), activation_code varchar(100), PRIMARY KEY(question_id, event_id));
INSERT into event_question values(
	0,
	0,
	1,
	'EL5',
	'1337'
);
CREATE table event_user(event_id int, email varchar(100), nick varchar(100), phone varchar(12), PRIMARY KEY(event_id, email));
INSERT into event_user values(
	0,
	'abc@de.fg',
	'nickman',
	'48199669'
);
CREATE table postAnswer(event_id int, user_email int, question_id int, answer varchar(100) PRIMARY KEY(event_id, user_email, question_id));