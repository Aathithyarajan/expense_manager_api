package in.aathi.expensetrackerapi.service;

import in.aathi.expensetrackerapi.entity.User;
import in.aathi.expensetrackerapi.entity.UserModel;

public interface UserService {
	
	User createUser(UserModel user);
	
	User readUser();
	
	User updateUser(UserModel user);
	
	void deleteUser();
	
	User getLoggedInUser();

}
