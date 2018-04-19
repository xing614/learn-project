package demo.service;



import demo.entity.Role;
import demo.entity.User;

public interface IUserService {

	public User getUserById(Long id);
	
	public Role getRoleById(Long id);

	public User selectByNameAndPass(String username, String password);
	
	public User selectByEmailAndPawd(String email,String password);
	
	public int updateByPrimaryKeySelective(User record);
	
	public int insertUser(User record);

}