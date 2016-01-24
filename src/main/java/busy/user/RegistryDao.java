package busy.user;

public interface RegistryDao {
	
	void save(int userId, String token);
	
	void delete(int userId);

	Verification findByToken(String token);

}
