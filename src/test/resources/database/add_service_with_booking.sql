/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database script to add a new service and a booking for this service
 */

INSERT INTO person(first_name, last_name, email, password, active, admin_role) VALUES('Raúl', 'García', 'user1@domain.com', 'pass', true, false);

INSERT INTO service(service_type_id) VALUES((SELECT id FROM service_type WHERE name='Tipo 1'));

INSERT INTO time_slot(start_time, service_id) VALUES('2016-02-07 21:00:00', (SELECT id FROM service WHERE service_type_id=(SELECT id FROM service_type WHERE name='Tipo 1')));

INSERT INTO schedule(time_slot_id, role_id) VALUES((SELECT id FROM time_slot WHERE start_time='2016-02-07 21:00:00'), (SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902202122')));

INSERT INTO booking(person_id, schedule_id) VALUES((SELECT id FROM person WHERE email='user1@domain.com'), (SELECT id FROM schedule WHERE time_slot_id=(SELECT id FROM time_slot WHERE start_time='2016-02-07 21:00:00')));