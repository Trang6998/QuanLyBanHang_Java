package nhom18.com.restful.entities;

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
@Table(name="thuoctinh")
public class ThuocTinh {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "thuoctinhid")
	private Integer ThuocTinhID;

	@Column(name = "tenthuoctinh")
	private String TenThuocTinh;

	@Column(name = "ghichu")
	private String GhiChu;


	@OneToMany(mappedBy = "thuocTinh", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ThuocTinhSanPham> lstThuocTinhSanPham;
	
	public ThuocTinh() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ThuocTinh(String tenThuocTinh, String ghiChu, List<ThuocTinhSanPham> thuocTinhSanPham) {
		super();
		TenThuocTinh = tenThuocTinh;
		GhiChu = ghiChu;
		lstThuocTinhSanPham = thuocTinhSanPham;
	}

	public Integer getThuocTinhID() {
		return ThuocTinhID;
	}

	public void setThuocTinhID(Integer thuocTinhID) {
		ThuocTinhID = thuocTinhID;
	}

	public String getTenThuocTinh() {
		return TenThuocTinh;
	}

	public void setTenThuocTinh(String tenThuocTinh) {
		TenThuocTinh = tenThuocTinh;
	}

	public String getGhiChu() {
		return GhiChu;
	}

	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

//	public List<ThuocTinhSanPham> getThuocTinhSanPham() {
//		return lstThuocTinhSanPham;
//	}
//
//	public void setThuocTinhSanPham(List<ThuocTinhSanPham> thuocTinhSanPham) {
//		lstThuocTinhSanPham = thuocTinhSanPham;
//	}

}
