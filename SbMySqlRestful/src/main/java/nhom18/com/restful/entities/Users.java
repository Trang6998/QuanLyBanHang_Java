package nhom18.com.restful.entities;
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
@Table(name="users")
public class Users {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userid")
	private Integer UserId;
	
	@Column(name = "ngaylap")
	private Date NgayLap;

	@Column(name = "hoten")
	private String HoTen;

	@Column(name = "email")
	private String Email;

	@Column(name = "ghichu")
	private String GhiChu;

	@Column(name = "diachi")
	private String DiaChi;

	@Column(name = "loaiuser")
	private Integer LoaiUser;

	@Column(name = "password")
	private String PassWord;

	@Column(name = "taikhoan")
	private String TaiKhoan;

	@Column(name = "sodienthoai")
	private String SoDienThoai;

	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DatDichVu> lstDatDichVu;
	
	@OneToMany(mappedBy = "userDatHang", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DonDatHang> lstDonDatHang;

	@OneToMany(mappedBy = "userNhanVien", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DonDatHang> lstDonDatHang1;
	
    public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Users(Date ngayLap, String hoTen, String email, String ghiChu, String diaChi, Integer loaiUser, String passWord,
			String taiKhoan, String soDienThoai, List<DatDichVu> datDichVu,
			List<DonDatHang> donDatHang, List<DonDatHang> donDatHang1) {
		super();
		NgayLap = ngayLap;
		HoTen = hoTen;
		Email = email;
		GhiChu = ghiChu;
		DiaChi = diaChi;
		LoaiUser = loaiUser;
		PassWord = passWord;
		TaiKhoan = taiKhoan;
		SoDienThoai = soDienThoai;
		lstDatDichVu = datDichVu;
		lstDonDatHang = donDatHang;
		lstDonDatHang1 = donDatHang1;
	}

	public Integer getUserId() {
		return UserId;
	}

	public void setUserId(Integer userId) {
		UserId = userId;
	}

	public Date getNgayLap() {
		return NgayLap;
	}

	public void setNgayLap(Date ngayLap) {
		NgayLap = ngayLap;
	}

	public String getHoTen() {
		return HoTen;
	}

	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getGhiChu() {
		return GhiChu;
	}

	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

	public String getDiaChi() {
		return DiaChi;
	}

	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}

	public Integer getLoaiUser() {
		return LoaiUser;
	}

	public void setLoaiUser(Integer loaiUser) {
		LoaiUser = loaiUser;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	public String getTaiKhoan() {
		return TaiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		TaiKhoan = taiKhoan;
	}

	public String getSoDienThoai() {
		return SoDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		SoDienThoai = soDienThoai;
	}

//	public List<DatDichVu> getDatDichVu() {
//		return lstDatDichVu;
//	}
//
//	public void setDatDichVu(List<DatDichVu> datDichVu) {
//		lstDatDichVu = datDichVu;
//	}
//
//	public List<DonDatHang> getDonDatHang() {
//		return lstDonDatHang;
//	}
//
//	public void setDonDatHang(List<DonDatHang> donDatHang) {
//		lstDonDatHang = donDatHang;
//	}
//
//	public List<DonDatHang> getDonDatHang1() {
//		return lstDonDatHang1;
//	}
//
//	public void setDonDatHang1(List<DonDatHang> donDatHang1) {
//		lstDonDatHang1 = donDatHang1;
//	}

	
}
