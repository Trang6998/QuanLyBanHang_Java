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
import javax.persistence.Transient;

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
	 @JsonIgnore
	 private List<ChiTietDonDatHang> lstChiTietDonDatHang;
	 
     public List<ChiTietDonDatHang> getLstChiTietDonDatHang() {
		return lstChiTietDonDatHang;
	}

	public void setLstChiTietDonDatHang(List<ChiTietDonDatHang> lstChiTietDonDatHang) {
		this.lstChiTietDonDatHang = lstChiTietDonDatHang;
	}

	 @ManyToOne
     @JoinColumn(name="taikhoandathangid",nullable = true, insertable = false, updatable = false)
     @JsonIgnore
     private Users userDatHang;
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="taikhoannhanvienid",nullable = true, insertable = false, updatable = false)	
 	 @JsonIgnore
 	 private Users userNhanVien;
     
     @Transient
     private Double TongTien;
     
     public Double getTongTien() {
   	  	 Double tong = 0.0;
		 if (lstChiTietDonDatHang.size() == 0)
			 return 0.0;
		 else {
			 for (ChiTietDonDatHang ct : lstChiTietDonDatHang){
				 tong += ct.getGiaXuat() * ct.getSoLuong();
			 }
			 return tong;
		 }
	 }
     
     @Transient
     private String NguoiDat;
     
     public String getNguoiDat() {
    	 return userDatHang != null? userDatHang.getHoTen() : "";
     }
     
     @Transient
     private String SoDienThoai;
     
     public String getSoDienThoai() {
    	 return userDatHang != null? userDatHang.getSoDienThoai() : "";
     }
     
     public DonDatHang() {
		super();
		// TODO Auto-generated constructor stub
	}

     public DonDatHang(Integer taiKhoanDatHangID, Integer taiKhoanNhanVienID, String soHieuDon, Date ngayDat, Date henLayTu,
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

    public Integer getDonDatHangID() {
		return DonDatHangID;
	}

    public void setDonDatHangID(Integer donDatHangID) {
		DonDatHangID = donDatHangID;
	}

    public Integer getTaiKhoanDatHangID() {
		return TaiKhoanDatHangID;
	}

	public void setTaiKhoanDatHangID(Integer taiKhoanDatHangID) {
		TaiKhoanDatHangID = taiKhoanDatHangID;
	}

	public Integer getTaiKhoanNhanVienID() {
		return TaiKhoanNhanVienID;
	}

	public void setTaiKhoanNhanVienID(Integer taiKhoanNhanVienID) {
		TaiKhoanNhanVienID = taiKhoanNhanVienID;
	}

	public String getSoHieuDon() {
		return SoHieuDon;
	}

	public void setSoHieuDon(String soHieuDon) {
		SoHieuDon = soHieuDon;
	}

	public Date getNgayDat() {
		return NgayDat;
	}

	public void setNgayDat(Date ngayDat) {
		NgayDat = ngayDat;
	}

	public Date getHenLayTu() {
		return HenLayTu;
	}

	public void setHenLayTu(Date henLayTu) {
		HenLayTu = henLayTu;
	}

	public Date getHenLayDen() {
		return HenLayDen;
	}

	public void setHenLayDen(Date henLayDen) {
		HenLayDen = henLayDen;
	}

	public Date getHenTraTu() {
		return HenTraTu;
	}

	public void setHenTraTu(Date henTraTu) {
		HenTraTu = henTraTu;
	}

	public Date getHenTraDen() {
		return HenTraDen;
	}

	public void setHenTraDen(Date henTraDen) {
		HenTraDen = henTraDen;
	}

	public Boolean getLaTre() {
		return LaTre;
	}

	public void setLaTre(Boolean laTre) {
		LaTre = laTre;
	}

	public String getLyDoTraTre() {
		return LyDoTraTre;
	}

	public void setLyDoTraTre(String lyDoTraTre) {
		LyDoTraTre = lyDoTraTre;
	}

	public Date getNgayGioTraThucTe() {
		return NgayGioTraThucTe;
	}

	public void setNgayGioTraThucTe(Date ngayGioTraThucTe) {
		NgayGioTraThucTe = ngayGioTraThucTe;
	}

	public String getGhiChu() {
		return GhiChu;
	}

	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

	public Integer getTinhTrang() {
		return TinhTrang;
	}

	public void setTinhTrang(Integer tinhTrang) {
		TinhTrang = tinhTrang;
	}

	public Users getUserDatHang() {
		return userDatHang;
	}

	public void setUserDatHang(Users userDatHang) {
		userDatHang = userDatHang;
	}

	public Users getUserNhanVien() {
		return userNhanVien;
	}

	public void setUserNhanVien(Users userNhanVien) {
		userNhanVien = userNhanVien;
	}
	
    
}
