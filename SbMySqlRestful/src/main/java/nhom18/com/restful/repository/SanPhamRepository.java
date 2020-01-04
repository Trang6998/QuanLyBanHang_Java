package nhom18.com.restful.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nhom18.com.restful.entities.ChiTietDonDatHang;
import nhom18.com.restful.entities.DatDichVu;
import nhom18.com.restful.entities.SanPham;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
	 @Query("SELECT sp FROM SanPham sp "
	 			   + "INNER JOIN LoaiSanPham lsp ON sp.LoaiSanPhamID = lsp.LoaiSanPhamID "
	 			   + "INNER JOIN LoaiSanPham lspc ON lspc.LoaiSanPhamID = lsp.LoaiSanPhamPID "
	 			   + "WHERE (sp.TenSanPham like CONCAT('%',:tenSanPham,'%') OR :tenSanPham is NULL) "
	 			   + "AND (sp.GiaBan >= :toiThieu OR :toiThieu is NULL) "
	 			   + "AND (sp.GiaBan <= :toiDa OR :toiDa is NULL) "
	 			   + "AND (sp.NhaCungCapID = :nhaCungCapID OR :nhaCungCapID is NULL) "
	 			   + "AND (lspc.LoaiSanPhamID = :loaiSanPhamID "
	 			   		+ "OR lsp.LoaiSanPhamID = :loaiSanPhamID "
	 			   		+ "OR :loaiSanPhamID is NULL) "
	 			   + "AND (sp.AnHienSanPham = :trangThaiAnHien OR :trangThaiAnHien is NULL) ")
     public List<SanPham> findByProperty( @Param("tenSanPham") String tenSanPham,
    		 										@Param("toiThieu") Double toiThieu,
    		 										@Param("toiDa") Double toiDa,
    		 										@Param("nhaCungCapID") Integer nhaCungCapID,
    		 										@Param("loaiSanPhamID") Integer loaiSanPhamID,
    		 										@Param("trangThaiAnHien") Boolean trangThaiAnHien);
	
}


