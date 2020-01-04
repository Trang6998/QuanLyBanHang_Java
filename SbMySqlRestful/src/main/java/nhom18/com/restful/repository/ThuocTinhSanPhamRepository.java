package nhom18.com.restful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nhom18.com.restful.entities.ThuocTinhSanPham;
@Repository
public interface ThuocTinhSanPhamRepository extends JpaRepository<ThuocTinhSanPham,Integer> {
	 @Query("SELECT t FROM ThuocTinhSanPham t where t.SanPhamID = :sanPhamID ")
     public List<ThuocTinhSanPham> findBySanPhamId(@Param("sanPhamID") Integer sanPhamID);
}
