package nhom18.com.restful.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nhom18.com.restful.entities.Users;
import nhom18.com.restful.repository.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;
	
	public Iterable<Users> findAll(){
		return usersRepository.findAll();
	}
	public Users findByTaiKhoan(String taikhoan, String password){
		return usersRepository.findByTaiKhoan(taikhoan, password);
	}
	public Optional<Users> findById(int id){
		return usersRepository.findById(id);
	}
    public Users save(Users dv) {
        return usersRepository.save(dv);
    }
    public void delete(int id) {
    	usersRepository.deleteById(id);
    }
}
