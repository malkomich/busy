/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database script to reject the company created
 */

DELETE FROM company;

INSERT INTO notification(person_id, notif_type, message, read, create_date) VALUES((SELECT id FROM person WHERE email='user@domain.com'), 'notification.type.company', 'notification.message.company.rejected', DEFAULT, DEFAULT);