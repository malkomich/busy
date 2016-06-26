/* 
 * Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database initialization with the basic data to check the finished user stories
 */

SET CLIENT_ENCODING TO 'UTF-8';

INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Juan', 'González', 'user1@domain.x', 'pass', true, false);
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Carlos', 'Cabrero', 'user2@domain.x', 'pass', true, false);
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Mario', 'Gómez', 'peluqueria@domain.x', 'pass', true, false);
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Raúl', 'Castro', 'academia@domain.x', 'pass', true, false);
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Admin', 'Istrador', 'admin@domain.x', 'pass', true, true);

INSERT INTO country(name, code) VALUES('España', 'ES');
INSERT INTO city(name, country_id) VALUES('Valladolid', (SELECT id FROM country WHERE code='ES'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Mayor', '2, 1ºC', '47005', (SELECT id FROM city WHERE name='Valladolid'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Toreros', '3, s/n', '47007', (SELECT id FROM city WHERE name='Valladolid'));

INSERT INTO category(name) VALUES('Peluquería');
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('El Corta Pelos', 'El Corta Pelos S.A.', 'peluqueria@domain.x', 'B12345678', false, (SELECT id FROM category WHERE name='Mobiliario'));
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='peluqueria@domain.x'), (SELECT id FROM address WHERE address1='Calle Mayor'), true, '902202122');
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='peluqueria@domain.x'), (SELECT id FROM branch WHERE phone='902202122'), true);

INSERT INTO category(name) VALUES('Academia');
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('AprendeMás', 'AprendeMás S.A.', 'academia@domain.x', 'B12345679', true, (SELECT id FROM category WHERE name='Software'));
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE email='academia@domain.x'), (SELECT id FROM address WHERE address1='Calle Mayor'), true, '902202123');
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='academia@domain.x'), (SELECT id FROM branch WHERE phone='902202123'), true);

INSERT INTO notification(person_id, notif_type, message, read, create_date) VALUES((SELECT id FROM person WHERE email='peluqueria@domain.x'), 'notification.type.company', 'notification.message.company.pending', DEFAULT, DEFAULT);
INSERT INTO notification(person_id, notif_type, message, read, create_date) VALUES((SELECT id FROM person WHERE email='academia@domain.x'), 'notification.type.company', 'notification.message.company.approved', DEFAULT, DEFAULT);

INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Matemáticas', 'Clase teórica de Matemáticas', 2, (SELECT id FROM company WHERE trade_name='AprendeMás'));

INSERT INTO service(service_type_id, repetition_type) VALUES((SELECT id FROM service_type WHERE name='Matemáticas'), 0);

INSERT INTO time_slot(start_time, service_id) VALUES('2016-01-12 23:00:00', (SELECT id FROM service WHERE service_type_id=(SELECT id FROM service_type WHERE name='Matemáticas')));
INSERT INTO time_slot(start_time, service_id) VALUES('2016-01-25 12:00:00', (SELECT id FROM service WHERE service_type_id=(SELECT id FROM service_type WHERE name='Matemáticas')));

INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-01-12 23:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902202123')));
INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-01-25 12:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902202123')));

INSERT INTO booking(person_id, schedule_id) VALUES((SELECT id FROM person WHERE email='user1@domain.x'), (SELECT id FROM schedule WHERE time_slot_id=(SELECT id FROM time_slot WHERE start_time='2016-01-12 23:00:00')));
INSERT INTO booking(person_id, schedule_id) VALUES((SELECT id FROM person WHERE email='user1@domain.x'), (SELECT id FROM schedule WHERE time_slot_id=(SELECT id FROM time_slot WHERE start_time='2016-01-25 12:00:00')));
INSERT INTO booking(person_id, schedule_id) VALUES((SELECT id FROM person WHERE email='user2@domain.x'), (SELECT id FROM schedule WHERE time_slot_id=(SELECT id FROM time_slot WHERE start_time='2016-01-25 12:00:00')));
