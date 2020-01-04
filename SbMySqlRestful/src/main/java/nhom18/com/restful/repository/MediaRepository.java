package nhom18.com.restful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nhom18.com.restful.entities.Media;


public interface MediaRepository extends JpaRepository<Media, Integer> {
	 @Query("SELECT media FROM Media media "
			   + "WHERE (media.DichVuID = :DichVuID OR :DichVuID is NULL) "
			   + "OR (media.SanPhamID = :SanPhamID OR :SanPhamID is NULL) ")
	 public List<Media> findByProperty( @Param("DichVuID") Integer dichVuID,@Param("SanPhamID") Integer sanPhamID);
}
