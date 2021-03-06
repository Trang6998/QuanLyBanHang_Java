package nhom18.com.restful.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nhom18.com.restful.entities.ChiTietDonDatHang;
import nhom18.com.restful.entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	@Query("SELECT u FROM Users u "
			   + "WHERE u.TaiKhoan = :taiKhoan "
			   + "AND u.PassWord = :password")
	 public Users findByTaiKhoan(@Param("taiKhoan") String taiKhoan,
		 						 @Param("password") String password);
}


