/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database script to approve all the companies created
 */

UPDATE company SET active=TRUE;

INSERT INTO notification(person_id, notif_type, message, read, create_date) VALUES((SELECT id FROM person WHERE email='user@domain.com'), 'notification.type.company', 'notification.message.company.approved', DEFAULT, DEFAULT);