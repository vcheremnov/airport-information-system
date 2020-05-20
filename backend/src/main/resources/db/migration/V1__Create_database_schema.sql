CREATE TABLE person
(
	id				bigserial 		PRIMARY KEY,
	name           	varchar(100) 	NOT NULL,
	birth_date      date 			NOT NULL,
	sex            	varchar(30)     NOT NULL
);


CREATE TABLE passenger
(
    id 		bigint 			PRIMARY KEY		REFERENCES person   ON DELETE CASCADE
);


CREATE TABLE chief
(
	id 	bigint 	PRIMARY KEY		REFERENCES person   ON DELETE CASCADE
);


CREATE TABLE department
(
	id 				bigserial 		PRIMARY KEY,
	name         	varchar(100) 	NOT NULL,
	chief_id      	bigint 			NOT NULL		REFERENCES chief
);


CREATE TABLE team
(
	id       		bigserial	PRIMARY KEY,
	department_id 	bigint 	    NOT NULL		REFERENCES department   ON DELETE CASCADE
);


CREATE TABLE employee
(
	id 		        bigint 			PRIMARY KEY		REFERENCES person   ON DELETE CASCADE,
	team_id   		bigint 			NOT NULL		REFERENCES team,
	employment_date date 			NOT NULL,
	salary         	integer 		NOT NULL
);


CREATE TABLE medical_examination
(
	id				bigserial 	PRIMARY KEY,
	employee_id 	bigint 		NOT NULL		REFERENCES employee     ON DELETE CASCADE,
	exam_date		date 		NOT NULL,
	is_passed 		boolean 	NOT NULL
);


CREATE TABLE airplane_type
(
	id 					bigserial 		PRIMARY KEY,
	name           		varchar(100) 	NOT NULL,
	capacity       		integer 		NOT NULL,
	speed          		integer 		NOT NULL
);


CREATE TABLE airplane
(
	id        			bigserial	PRIMARY KEY,
	airplane_type_id   	bigint 	    NOT NULL		REFERENCES airplane_type,
	pilot_team_id      	bigint 	    NOT NULL		REFERENCES team,
	tech_team_id       	bigint 	    NOT NULL		REFERENCES team,
	service_team_id    	bigint 	    NOT NULL		REFERENCES team,
	commissioning_date 	date 	    NOT NULL
);


CREATE TABLE repair
(
    id  		bigserial 	PRIMARY KEY,
	airplane_id bigint 		NOT NULL		REFERENCES airplane     ON DELETE CASCADE,
	start_time  timestamp 	NOT NULL,
	finish_time timestamp 	NOT NULL
);


CREATE TABLE tech_inspection
(
	id 				bigserial 	PRIMARY KEY,
	airplane_id   	bigint 		NOT NULL		REFERENCES airplane     ON DELETE CASCADE,
	inspection_time timestamp 	NOT NULL,
	is_passed     	boolean 	NOT NULL
);


CREATE TABLE city
(
	id   		bigserial 	PRIMARY KEY,
	name     	varchar(50) NOT NULL,
	distance 	integer 	NOT NULL
);


CREATE TABLE flight
(
	id     			bigserial 		    PRIMARY KEY,
	type            varchar(30)         NOT NULL,
	airplane_id   	bigint 		        NOT NULL		REFERENCES airplane,
	city_id       	bigint 		        NOT NULL		REFERENCES city,
	flight_time     timestamp 	        NOT NULL,
	is_cancelled    boolean             NOT NULL
);

CREATE TABLE flight_delay
(
	flight_id      		bigint 		    PRIMARY KEY		REFERENCES flight       ON DELETE CASCADE,
	delay_reason  	    varchar(100) 	NOT NULL
);


CREATE TABLE ticket
(
	id    			    bigserial 			PRIMARY KEY,
	passenger_id 	    bigint 			    NOT NULL        REFERENCES passenger,
	flight_id    	    bigint 			    NOT NULL		REFERENCES flight       ON DELETE CASCADE,
    status              varchar(30) 	    NOT NULL
);



