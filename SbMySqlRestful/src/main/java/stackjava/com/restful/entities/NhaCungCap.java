package stackjava.com.restful.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="nhacungcap")
public class NhaCungCap {
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name = "nhacungcapid")
	 private Integer NhaCungCapID;

	 @Column(name = "tennhacungcap")
     private String TenNhaCungCap;

	 @Column(name = "diachi")
     private String DiaChi;

	 @Column(name = "sodienthoai")
     private String SoDienThoai;

	 @Column(name = "email")
     private String Email;

	 @Column(name = "ghichu")
     private String GhiChu;


	 @OneToMany(mappedBy = "nhaCungCap", cascade = CascadeType.ALL)
	 private List<SanPham> lstSanPham;
	 
     public NhaCungCap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NhaCungCap(String tenNhaCungCap, String diaChi, String soDienThoai, String email, String ghiChu,
			List<SanPham> sanPham) {
		super();
		TenNhaCungCap = tenNhaCungCap;
		DiaChi = diaChi;
		SoDienThoai = soDienThoai;
		Email = email;
		GhiChu = ghiChu;
		lstSanPham = sanPham;
	}

	public Integer getNhaCungCapID() {
		return NhaCungCapID;
	}

	public void setNhaCungCapID(Integer nhaCungCapID) {
		NhaCungCapID = nhaCungCapID;
	}

	public String getTenNhaCungCap() {
		return TenNhaCungCap;
	}

	public void setTenNhaCungCap(String tenNhaCungCap) {
		TenNhaCungCap = tenNhaCungCap;
	}

	public String getDiaChi() {
		return DiaChi;
	}

	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}

	public String getSoDienThoai() {
		return SoDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		SoDienThoai = soDienThoai;
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

	public List<SanPham> getSanPham() {
		return lstSanPham;
	}

	public void setSanPham(List<SanPham> sanPham) {
		lstSanPham = sanPham;
	}

}
