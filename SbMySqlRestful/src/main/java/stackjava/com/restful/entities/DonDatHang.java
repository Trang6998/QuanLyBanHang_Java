package stackjava.com.restful.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="dondathang")
public class DonDatHang {
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name = "dondathangid")
	 private Integer DonDatHangID;
	 
	 @Column(name = "taikhoandathangid")
     private Integer TaiKhoanDatHangID;

	 @Column(name = "taikhoannhanvienid")	 
     private Integer TaiKhoanNhanVienID;

	 @Column(name = "sohieudon")
     private String SoHieuDon;

	 @Column(name = "ngaydat")
     private Date NgayDat;

	 @Column(name = "henlaytu")
     private Date HenLayTu;

	 @Column(name = "henlayden")
     private Date HenLayDen;

	 @Column(name = "hentratu")
     private Date HenTraTu;

	 @Column(name = "hentraden")
     private Date HenTraDen;

	 @Column(name = "latre")
     private Boolean LaTre;

	 @Column(name = "lydotratre")
     private String LyDoTraTre;

	 @Column(name = "ngaygiotrathucte")
     private Date NgayGioTraThucTe;

	 @Column(name = "ghichu")
	 private String GhiChu;
	 
	 @Column(name = "tinhtrang")
     private Integer TinhTrang;
     
	 @OneToMany(mappedBy = "donDatHang", cascade = CascadeType.ALL)
	 private List<ChiTietDonDatHang> lstChiTietDonDatHang;
	 
     @ManyToOne
     @JoinColumn(name="taikhoandathangid",nullable = true, insertable = false, updatable = false)
     @JsonIgnore
     private Users userDatHang;
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="taikhoannhanvienid",nullable = true, insertable = false, updatable = false)	
 	 @JsonIgnore
 	 private Users userNhanVien;
     
     private DonDatHang() {
		super();
		// TODO Auto-generated constructor stub
	}

	private DonDatHang(Integer taiKhoanDatHangID, Integer taiKhoanNhanVienID, String soHieuDon, Date ngayDat, Date henLayTu,
			Date henLayDen, Date henTraTu, Date henTraDen, Boolean laTre, String lyDoTraTre, Date ngayGioTraThucTe,
			String ghiChu, Integer tinhTrang, List<ChiTietDonDatHang> chiTietDonDatHang, Users userDatHang,
			Users userNhanVien) {
		super();
		TaiKhoanDatHangID = taiKhoanDatHangID;
		TaiKhoanNhanVienID = taiKhoanNhanVienID;
		SoHieuDon = soHieuDon;
		NgayDat = ngayDat;
		HenLayTu = henLayTu;
		HenLayDen = henLayDen;
		HenTraTu = henTraTu;
		HenTraDen = henTraDen;
		LaTre = laTre;
		LyDoTraTre = lyDoTraTre;
		NgayGioTraThucTe = ngayGioTraThucTe;
		GhiChu = ghiChu;
		TinhTrang = tinhTrang;
		lstChiTietDonDatHang = chiTietDonDatHang;
		this.userDatHang = userDatHang;
		this.userNhanVien = userNhanVien;
	}

	private Integer getDonDatHangID() {
		return DonDatHangID;
	}

	private void setDonDatHangID(Integer donDatHangID) {
		DonDatHangID = donDatHangID;
	}

	private Integer getTaiKhoanDatHangID() {
		return TaiKhoanDatHangID;
	}

	private void setTaiKhoanDatHangID(Integer taiKhoanDatHangID) {
		TaiKhoanDatHangID = taiKhoanDatHangID;
	}

	private Integer getTaiKhoanNhanVienID() {
		return TaiKhoanNhanVienID;
	}

	private void setTaiKhoanNhanVienID(Integer taiKhoanNhanVienID) {
		TaiKhoanNhanVienID = taiKhoanNhanVienID;
	}

	private String getSoHieuDon() {
		return SoHieuDon;
	}

	private void setSoHieuDon(String soHieuDon) {
		SoHieuDon = soHieuDon;
	}

	private Date getNgayDat() {
		return NgayDat;
	}

	private void setNgayDat(Date ngayDat) {
		NgayDat = ngayDat;
	}

	private Date getHenLayTu() {
		return HenLayTu;
	}

	private void setHenLayTu(Date henLayTu) {
		HenLayTu = henLayTu;
	}

	private Date getHenLayDen() {
		return HenLayDen;
	}

	private void setHenLayDen(Date henLayDen) {
		HenLayDen = henLayDen;
	}

	private Date getHenTraTu() {
		return HenTraTu;
	}

	private void setHenTraTu(Date henTraTu) {
		HenTraTu = henTraTu;
	}

	private Date getHenTraDen() {
		return HenTraDen;
	}

	private void setHenTraDen(Date henTraDen) {
		HenTraDen = henTraDen;
	}

	private Boolean getLaTre() {
		return LaTre;
	}

	private void setLaTre(Boolean laTre) {
		LaTre = laTre;
	}

	private String getLyDoTraTre() {
		return LyDoTraTre;
	}

	private void setLyDoTraTre(String lyDoTraTre) {
		LyDoTraTre = lyDoTraTre;
	}

	private Date getNgayGioTraThucTe() {
		return NgayGioTraThucTe;
	}

	private void setNgayGioTraThucTe(Date ngayGioTraThucTe) {
		NgayGioTraThucTe = ngayGioTraThucTe;
	}

	private String getGhiChu() {
		return GhiChu;
	}

	private void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

	private Integer getTinhTrang() {
		return TinhTrang;
	}

	private void setTinhTrang(Integer tinhTrang) {
		TinhTrang = tinhTrang;
	}

	private List<ChiTietDonDatHang> getChiTietDonDatHangs() {
		return lstChiTietDonDatHang;
	}

	private void setChiTietDonDatHangs(List<ChiTietDonDatHang> chiTietDonDatHangs) {
		lstChiTietDonDatHang = chiTietDonDatHangs;
	}

	private Users getUserDatHang() {
		return userDatHang;
	}

	private void setUserDatHang(Users userDatHang) {
		userDatHang = userDatHang;
	}

	private Users getUserNhanVien() {
		return userNhanVien;
	}

	private void setUserNhanVien(Users userNhanVien) {
		userNhanVien = userNhanVien;
	}
	
    
}
