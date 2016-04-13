/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database script to add a new service and a booking for this service
 */

INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Raúl', 'García', 'user1@domain.com', 'pass', true, false);

INSERT INTO year_schedule(branch_id, year) VALUES((SELECT id FROM branch WHERE phone='902202122'), 2016);
INSERT INTO week_schedule(year_schedule_id, week_of_year, is_default) VALUES((SELECT id FROM year_schedule WHERE year=2016), 23, true);
INSERT INTO day_schedule(week_schedule_id, day_of_week) VALUES((SELECT id FROM week_schedule WHERE week_of_year=23), 7);
INSERT INTO hour_schedule(day_schedule_id, start_time, end_time) VALUES((SELECT id FROM day_schedule WHERE day_of_week=7), '22:00:00', '21:00:00');

INSERT INTO service(service_type_id, hour_schedule_id, role_id) VALUES((SELECT id FROM service_type WHERE name='Engineer'), (SELECT id FROM hour_schedule WHERE start_time='22:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902202122')));

INSERT INTO booking(person_id, service_id) VALUES((SELECT id FROM person WHERE email='user1@domain.com'), (SELECT id FROM service WHERE hour_schedule_id=(SELECT id FROM hour_schedule WHERE start_time='23:00:00')));