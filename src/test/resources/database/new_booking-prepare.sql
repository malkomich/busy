/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database prepare for the feature about creating a new booking
 */

INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Aurelio', 'González', 'peluqueria@domain.x', 'pass', true, false);

INSERT INTO country(name, code) VALUES('España', 'ES');
INSERT INTO city(name, country_id) VALUES('Valladolid', (SELECT id FROM country WHERE code='ES'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Mayor', null, '47005', (SELECT id FROM city WHERE name='Valladolid'));

INSERT INTO category(name) VALUES('Peluquería');
INSERT INTO company(id, trade_name, business_name, email, cif, active, category_id) VALUES(1, 'El Corta Pelos', 'El Corta Pelos S.A.', 'busy.validation@gmail.com', 'B12345678', true, (SELECT id FROM category WHERE name='Peluquería'));
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE cif='B12345678'), (SELECT id FROM address WHERE address1='Calle Mayor'), true, '902202122');
INSERT INTO role(person_id, branch_id, is_manager) VALUES((SELECT id FROM person WHERE email='peluqueria@domain.x'), (SELECT id FROM branch WHERE phone='902202122'), true);

INSERT INTO service_type(name, description, bookings_per_role, company_id) VALUES('Peluquero', NULL, 1, (SELECT id FROM company WHERE cif='B12345678'));