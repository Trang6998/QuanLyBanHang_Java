package stackjava.com.restful.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import stackjava.com.restful.entities.ChiTietDonDatHang;
import stackjava.com.restful.entities.DichVu;
import stackjava.com.restful.entities.LoaiSanPham;

@Repository
public interface LoaiSanPhamRepository extends JpaRepository<LoaiSanPham, Integer> {
	 @Query("SELECT lsp FROM LoaiSanPham lsp "
			   + "WHERE (lsp.TenLoai like CONCAT('%',:tenLoai,'%') OR :tenLoai is NULL) "
			   + "AND ((:laDanhMucCha = true AND (lsp.LoaiSanPhamPID is NULL)) "
			   + "OR (:laDanhMucCha = false AND (lsp.LoaiSanPhamPID is not NULL)) OR :laDanhMucCha is NULL) "
			   + "AND (lsp.LoaiSanPhamPID = :loaiSanPhamPID OR :loaiSanPhamPID is NULL) "
			   + "AND (lsp.LoaiSanPhamID = :loaiSanPhamID OR :loaiSanPhamID is NULL)")
	 public List<LoaiSanPham> findByProperty( @Param("tenLoai") String donDatHangID,
			 										@Param("laDanhMucCha") Boolean laDanhMucCha,
													@Param("loaiSanPhamPID") Integer loaiSanPhamPID,
			 										@Param("loaiSanPhamID") Integer loaiSanPhamID);
}