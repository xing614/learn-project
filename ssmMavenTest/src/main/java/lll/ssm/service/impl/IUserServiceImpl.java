package lll.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lll.ssm.dao.IUserDao;
import lll.ssm.entity.User;
import lll.ssm.service.IUserService;

@Service("userService")
public class IUserServiceImpl  implements IUserService{

	@Autowired
	public IUserDao udao;
	
	@Override
	public User getUserById(int id) {
		return udao.selectByPrimaryKey(id);
	}

}