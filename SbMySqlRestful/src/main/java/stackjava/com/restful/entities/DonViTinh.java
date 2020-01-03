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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="donvitinh")
public class DonViTinh {
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name = "donvitinhid")
	 private Integer DonViTinhID;

	 @Column(name = "tendonvi")
     private String TenDonVi;
 	
	 @Column(name = "ghichu")
     private String GhiChu;

	 @OneToMany(mappedBy = "donViTinh", cascade = CascadeType.ALL)
	 @JsonIgnore
     private List<SanPham> lstSanPham;
	 
     public DonViTinh() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getDonViTinhID() {
		return DonViTinhID;
	}

	public void setDonViTinhID(Integer donViTinhID) {
		DonViTinhID = donViTinhID;
	}

	public DonViTinh(String tenDonVi, String ghiChu, List<SanPham> sanPham) {
		super();
		TenDonVi = tenDonVi;
		GhiChu = ghiChu;
		lstSanPham = sanPham;
	}

	public String getTenDonVi() {
		return TenDonVi;
	}

	public void setTenDonVi(String tenDonVi) {
		TenDonVi = tenDonVi;
	}

	public String getGhiChu() {
		return GhiChu;
	}

	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

//	public List<SanPham> getSanPham() {
//		return lstSanPham;
//	}

//	public void setSanPham(List<SanPham> sanPham) {
//		lstSanPham = sanPham;
//	}

}
