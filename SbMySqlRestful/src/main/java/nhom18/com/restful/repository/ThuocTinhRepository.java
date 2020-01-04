package nhom18.com.restful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nhom18.com.restful.entities.ThuocTinh;
@Repository
public interface ThuocTinhRepository extends JpaRepository<ThuocTinh, Integer>{
	 @Query("SELECT t FROM ThuocTinh t where t.TenThuocTinh like CONCAT('%',:tenThuocTinh,'%')")
     public List<ThuocTinh> findByName(@Param("tenThuocTinh") String tenThuocTinh);
}
