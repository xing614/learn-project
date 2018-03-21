package demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.dao.IUserDao;
import demo.entity.User;
import demo.service.IUserService;

@Service
public class IUserServiceImpl  implements IUserService{

	@Autowired
	public IUserDao udao;
	
	@Override
	public User getUserById(int id) {
		return udao.selectByPrimaryKey(id);
	}

}