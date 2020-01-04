package nhom18.com.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nhom18.com.restful.entities.NhaCungCap;

public interface NhaCungCapRepository extends JpaRepository<NhaCungCap,Integer> {
	 @Query("SELECT n FROM NhaCungCap n where n.TenNhaCungCap like CONCAT('%',:tenNhaCungCap,'%')")
     public List<NhaCungCap> findByName(@Param("tenNhaCungCap") String tenNhaCungCap);
}
