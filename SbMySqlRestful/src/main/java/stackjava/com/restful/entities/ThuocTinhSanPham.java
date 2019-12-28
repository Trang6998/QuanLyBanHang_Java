package stackjava.com.restful.entities;
import java.util.List;

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
@Table(name="thuoctinhsanpham")
public class ThuocTinhSanPham {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "thuoctinhsanphamid")
	private Integer ThuocTinhSanPhamID;

	@Column(name = "sanphamid")
    private Integer SanPhamID;

	@Column(name = "thuoctinhid")
    private Integer ThuocTinhID;

	@Column(name = "noidungmota")
    private String NoiDungMoTa;

	@Column(name = "ghichu")
    private String GhiChu;

	@ManyToOne
    @JoinColumn(name="sanphamid",nullable = true, insertable = false, updatable = false)	
	@JsonIgnore
	private SanPham sanPham;

	@ManyToOne
    @JoinColumn(name="thuoctinhid",nullable = true, insertable = false, updatable = false)	
	@JsonIgnore
    private ThuocTinh thuocTinh;
	
    public ThuocTinhSanPham() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ThuocTinhSanPham(Integer sanPhamID, Integer thuocTinhID, String noiDungMoTa, String ghiChu,
			SanPham sanPham, ThuocTinh thuocTinh) {
		super();
		SanPhamID = sanPhamID;
		ThuocTinhID = thuocTinhID;
		NoiDungMoTa = noiDungMoTa;
		GhiChu = ghiChu;
		this.sanPham = sanPham;
		this.thuocTinh = thuocTinh;
	}

	public Integer getThuocTinhSanPhamID() {
		return ThuocTinhSanPhamID;
	}

	public void setThuocTinhSanPhamID(Integer thuocTinhSanPhamID) {
		ThuocTinhSanPhamID = thuocTinhSanPhamID;
	}

	public Integer getSanPhamID() {
		return SanPhamID;
	}

	public void setSanPhamID(Integer sanPhamID) {
		SanPhamID = sanPhamID;
	}

	public Integer getThuocTinhID() {
		return ThuocTinhID;
	}

	public void setThuocTinhID(Integer thuocTinhID) {
		ThuocTinhID = thuocTinhID;
	}

	public String getNoiDungMoTa() {
		return NoiDungMoTa;
	}

	public void setNoiDungMoTa(String noiDungMoTa) {
		NoiDungMoTa = noiDungMoTa;
	}

	public String getGhiChu() {
		return GhiChu;
	}

	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public ThuocTinh getThuocTinh() {
		return thuocTinh;
	}

	public void setThuocTinh(ThuocTinh thuocTinh) {
		this.thuocTinh = thuocTinh;
	}

}
