/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database prepare for the feature of searching a company by name
 */

INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Nombre', 'Apellidos', 'user@domain.com', 'pass', true, false);
INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Jessy', 'Pinkman', 'busy.validation@gmail.com', 'pass', true, false);

INSERT INTO country(name, code) VALUES('España', 'ES');
INSERT INTO city(name, country_id) VALUES('Valladolid', (SELECT id FROM country WHERE code='ES'));
INSERT INTO address(address1, address2, zip_code, city_id) VALUES('Calle Mayor', null, '47005', (SELECT id FROM city WHERE name='Valladolid'));

INSERT INTO category(name) VALUES('Mobiliario');
INSERT INTO company(trade_name, business_name, email, cif, active, category_id) VALUES('Busy', 'Busy S.A.', 'busy.validation@gmail.com', 'B12345678', true, (SELECT id FROM category WHERE name='Mobiliario'));
INSERT INTO branch(company_id, address_id, main, phone) VALUES((SELECT id FROM company WHERE cif='B12345678'), (SELECT id FROM address WHERE address1='Calle Mayor'), true, '902202122');
INSERT INTO role(person_id, branch_id, is_manager, activity) VALUES((SELECT id FROM person WHERE email='busy.validation@gmail.com'), (SELECT id FROM branch WHERE phone='902202122'), true, 'Jefe');

INSERT INTO year_schedule(branch_id, year) VALUES((SELECT id FROM branch WHERE phone='902202122'), 2016);
INSERT INTO week_schedule(year_schedule_id, week_of_year, is_default) VALUES((SELECT id FROM year_schedule WHERE year=2016), 1, false);
INSERT INTO week_schedule(year_schedule_id, week_of_year, is_default) VALUES((SELECT id FROM year_schedule WHERE year=2016), 2, true);
INSERT INTO day_schedule(week_schedule_id, day_of_week) VALUES((SELECT id FROM week_schedule WHERE week_of_year=1), 1);
INSERT INTO day_schedule(week_schedule_id, day_of_week) VALUES((SELECT id FROM week_schedule WHERE week_of_year=2), 2);
INSERT INTO hour_schedule(day_schedule_id, start_time, end_time) VALUES((SELECT id FROM day_schedule WHERE day_of_week=1), '23:00:00', '00:30:00');
INSERT INTO hour_schedule(day_schedule_id, start_time, end_time) VALUES((SELECT id FROM day_schedule WHERE day_of_week=2), '12:00:00', '13:30:00');
INSERT INTO hour_schedule(day_schedule_id, start_time, end_time) VALUES((SELECT id FROM day_schedule WHERE day_of_week=2), '15:00:00', '16:00:00');

INSERT INTO booking((SELECT id FROM person WHERE email='user@domain.com'), (SELECT id FROM hour_schedule WHERE start_time='23:00:00'))
INSERT INTO booking((SELECT id FROM person WHERE email='user@domain.com'), (SELECT id FROM hour_schedule WHERE start_time='12:00:00'))
