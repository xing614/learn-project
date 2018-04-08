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

}