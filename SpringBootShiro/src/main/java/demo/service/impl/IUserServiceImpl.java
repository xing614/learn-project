package demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.dao.IRoleDao;
import demo.dao.IUserDao;
import demo.entity.Role;
import demo.entity.User;
import demo.service.IUserService;

@Service
public class IUserServiceImpl  implements IUserService{

	@Autowired
	public IUserDao udao;
	
	@Autowired
	public IRoleDao	rdao;
	
	@Override
	public User getUserById(Long id) {
		return udao.selectByPrimaryKey(id);
	}

	@Override
	public Role getRoleById(Long id) {
		// TODO Auto-generated method stub
		return rdao.selectByPrimaryKey(id);
	}
	
	@Override
	public User selectByNameAndPass(String username,String password) {
		return udao.selectByNameAndPass(username,password);
	}

	@Override
	public User selectByEmailAndPawd(String email, String password) {
		// TODO Auto-generated method stub
		return udao.selectByEmailAndPawd(email, password);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		// TODO Auto-generated method stub
		return udao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertUser(User record) {
		// TODO Auto-generated method stub
		if(udao.selectByName(record.getUsername()) != null) {
			return -1;
		}
		return udao.insertSelective(record);
	}	

}