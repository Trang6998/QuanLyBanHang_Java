package stackjava.com.restful.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import stackjava.com.restful.entities.DatDichVu;

@Repository
public interface DatDichVuRepository extends JpaRepository<DatDichVu, Integer> {
	 @Query("SELECT ddv FROM DatDichVu ddv "
	 			   + "INNER JOIN DichVu dv ON dv.DichVuID = ddv.DichVuID "
	 			   + "WHERE (dv.TenDichVu like CONCAT('%',:tenDichVu,'%') OR :tenDichVu is NULL) "
	 			   + "AND (ddv.NgayDat >= :tuNgay OR :tuNgay is NULL) "
	 			   + "AND (ddv.NgayDat <= :denNgay OR :denNgay is NULL) "
	 			   + "AND (ddv.DichVuID = :dichVuID OR :dichVuID is NULL)")
     public List<DatDichVu> findByProperty( @Param("tenDichVu") String tenDichVu,
    		 								@Param("tuNgay") Date tuNgay,
    		 								@Param("denNgay") Date denNgay,
    		 								@Param("dichVuID") Integer dichVuID);
}


