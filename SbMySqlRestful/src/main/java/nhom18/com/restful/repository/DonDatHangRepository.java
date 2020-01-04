package nhom18.com.restful.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nhom18.com.restful.entities.ChiTietDonDatHang;
import nhom18.com.restful.entities.DatDichVu;
import nhom18.com.restful.entities.DonDatHang;

@Repository
public interface DonDatHangRepository extends JpaRepository<DonDatHang, Integer> {
	 @Query("SELECT ddh FROM DonDatHang ddh "
	 			   + "WHERE (ddh.SoHieuDon like CONCAT('%',:soHieuDon,'%') OR :soHieuDon is NULL) "
	 			   + "AND (ddh.TaiKhoanDatHangID = :taiKhoanDatHangID OR :taiKhoanDatHangID is NULL) "
	 			   + "AND (ddh.TaiKhoanNhanVienID = :taiKhoanNhanVienID OR :taiKhoanNhanVienID is NULL) "
	 			   + "AND (ddh.NgayDat >= :tuNgay OR :tuNgay is NULL) "
	 			   + "AND (ddh.NgayDat <= :denNgay OR :denNgay is NULL) "
	 			   + "AND (ddh.TinhTrang = :tinhTrang OR :tinhTrang is NULL)"
	 			   + "AND ddh.TinhTrang <> 0")
     public List<DonDatHang> findByProperty( @Param("soHieuDon") String soHieuDon,
	 										 @Param("taiKhoanDatHangID") Integer taiKhoanDatHangID,
	 										 @Param("taiKhoanNhanVienID") Integer taiKhoanNhanVienID,
	 										 @Param("tuNgay") Date tuNgay,
	 										 @Param("denNgay") Date denNgay,
	 										 @Param("tinhTrang") Integer tinhTrang);
	 @Query(value = "SELECT * FROM dondathang ddh "
			   + "WHERE ((ddh.taikhoandathangid = :taiKhoanDatHangID OR :taiKhoanDatHangID is NULL) "
			   + "AND ddh.tinhtrang = 0) "
			   + "LIMIT 1", nativeQuery = true)
	 public Optional<DonDatHang> findByTaiKhoan( @Param("taiKhoanDatHangID") Integer taiKhoanDatHangID);
}


