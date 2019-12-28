package stackjava.com.restful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stackjava.com.restful.entities.DichVu;

@Repository
public interface DichVuRepository extends JpaRepository<DichVu, Integer> {
	 @Query("SELECT t FROM DichVu t where t.TenDichVu like CONCAT('%',:tenDichVu,'%')")
     public List<DichVu> findByName(@Param("tenDichVu") String tenDichVu);
	 @Query("SELECT t FROM DichVu t where t.TrangThai = :trangThai")
	 public List<DichVu> findByTrangThai(@Param("trangThai") Boolean trangThai);
}