package nhom18.com.restful.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nhom18.com.restful.entities.ChiTietDonDatHang;
import nhom18.com.restful.entities.DatDichVu;

@Repository
public interface ChiTietDonDatHangRepository extends JpaRepository<ChiTietDonDatHang, Integer> {
	 @Query("SELECT ctd FROM ChiTietDonDatHang ctd "
	 			   + "INNER JOIN SanPham sp ON sp.SanPhamID = ctd.SanPhamID "
	 			   + "INNER JOIN DonDatHang ddh ON ddh.DonDatHangID = ctd.DonDatHangID "
	 			   + "WHERE (ctd.DonDatHangID = :donDatHangID OR :donDatHangID is NULL) "
	 			   + "AND (ctd.SanPhamID = :sanPhamID OR :sanPhamID is NULL) "
	 			   + "AND (sp.NhaCungCapID = :nhaCungCapID OR :nhaCungCapID is NULL) "
	 			   + "AND (ddh.NgayDat >= :tuNgay OR :tuNgay is NULL) "
	 			   + "AND (ddh.NgayDat <= :denNgay OR :denNgay is NULL) "
	 			   + "AND (ddh.TinhTrang = :tinhTrang OR :tinhTrang is NULL)")
     public List<ChiTietDonDatHang> findByProperty( @Param("donDatHangID") Integer donDatHangID,
    		 										@Param("sanPhamID") Integer sanPhamID,
    		 										@Param("nhaCungCapID") Integer nhaCungCapID,
    		 										@Param("tuNgay") Date tuNgay,
    		 										@Param("denNgay") Date denNgay,
    		 										@Param("tinhTrang") Integer tinhTrang);
	 @Query("SELECT ctd FROM ChiTietDonDatHang ctd "
			   + "WHERE (ctd.DonDatHangID = :donDatHangID OR :donDatHangID is NULL) "
			   + "AND (ctd.SanPhamID = :sanPhamID OR :sanPhamID is NULL)")
	 public ChiTietDonDatHang findBySanPham(@Param("donDatHangID") Integer donDatHangID,
		 									@Param("sanPhamID") Integer sanPhamID);
}


