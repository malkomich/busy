/* 
 * Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database initialization with the basic data to check the finished user stories
 */

INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Nombre', 'Apellidos', 'user@domain.com', 'pass', true, false);
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Juan Carlos', 'González', 'malkomich@gmail.com', 'pass', true, false);
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Admin', 'Admin', 'admin@busy.com', 'pass', true, true);

INSERT INTO country(name, code) VALUES('España', 'ES');
INSERT INTO city(name, country_id) VALUES('Valladolid', (SELECT id FROM country WHERE code='ES'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Mayor', null, '47005', (SELECT id FROM city WHERE name='Valladolid'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Toreros', null, '47007', (SELECT id FROM city WHERE name='Valladolid'));

INSERT INTO category(name) VALUES('Mobiliario');
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('Boom', 'Boom S.A.', 'jefe@boom.com', 'B12345678', true, (SELECT id FROM category WHERE name='Mobiliario'));
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE cif='B12345678'), (SELECT id FROM address WHERE address1='Calle Mayor'), true, '902202122');
INSERT INTO role(person_id, branch_id, is_manager, activity) VALUES((SELECT id FROM person WHERE email='user@domain.com'), (SELECT id FROM branch WHERE phone='902202122'), true, 'Jefe');

INSERT INTO category(name) VALUES('Software');
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('Busy', 'Busy S.A.', 'busy.validation@gmail.com', 'B12345679', false, (SELECT id FROM category WHERE name='Software'));
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE cif='B12345679'), (SELECT id FROM address WHERE address1='Calle Mayor'), true, '902202123');
INSERT INTO role(person_id, branch_id, is_manager, activity) VALUES((SELECT id FROM person WHERE email='malkomich@gmail.com'), (SELECT id FROM branch WHERE phone='902202122'), true, 'Jefe')

INSERT INTO notification(person_id, notif_type, message, read, create_date) VALUES((SELECT id FROM person WHERE email='user@domain.com'), 'Gestión de empresa', 'Su empresa está pendiente de aprobación', true, DEFAULT);
INSERT INTO notification(person_id, notif_type, message, read, create_date) VALUES((SELECT id FROM person WHERE email='user@domain.com'), 'Gestión de empresa', 'Su empresa ha sido aprobada con éxito', DEFAULT, DEFAULT);

INSERT INTO notification(person_id, notif_type, message, read, create_date) VALUES((SELECT id FROM person WHERE email='malkomich@gmail.com'), 'Gestión de empresa', 'Su empresa está pendiente de aprobación', DEFAULT, DEFAULT);
