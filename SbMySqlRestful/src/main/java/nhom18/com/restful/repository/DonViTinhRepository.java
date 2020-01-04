package nhom18.com.restful.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nhom18.com.restful.entities.DonViTinh;


@Repository
public interface DonViTinhRepository extends JpaRepository<DonViTinh, Integer> {
	 @Query("SELECT d FROM DonViTinh d where d.TenDonVi like CONCAT('%',:tenDonVi,'%')")
     public List<DonViTinh> findByName(@Param("tenDonVi") String tenDonVi);
}
