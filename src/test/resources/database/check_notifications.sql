/* Busy 
 * Author: Juan Carlos González Cabrero 
 *
 * Database script to check the last stored notification
 */
	
SELECT * FROM notification WHERE person_id=(SELECT id FROM person WHERE email='user@domain.com') ORDER BY create_date DESC LIMIT 1;