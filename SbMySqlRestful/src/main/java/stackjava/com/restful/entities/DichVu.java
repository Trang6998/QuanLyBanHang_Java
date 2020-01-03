package stackjava.com.restful.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="dichvu")
public class DichVu {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "dichvuid")
	private Integer DichVuID;
	
	@Column(name = "tendichvu")
	private String TenDichVu;
	
	@Column(name = "anhdaidien")
	private String AnhDaiDien;
	
	@Column(name = "motangan")
	private String MoTaNgan;
	
	@Column(name = "trangthai")
	private Boolean TrangThai;
	
	@Column(name = "baiviet")
	private String BaiViet;
	
	@Column(name = "toanha")
	private String ToaNha;
	
	
	public Integer getDichVuID() {
		return DichVuID;
	}
	public void setDichVuID(Integer DichVuID) {
		this.DichVuID = DichVuID;
	}
	public String getTenDichVu() {
		return TenDichVu;
	}
	public void setTenDichVu(String TenDichVu) {
		this.TenDichVu = TenDichVu;
	}
	public String getAnhDaiDien() {
		return AnhDaiDien;
	}
	public void setAnhDaiDien(String AnhDaiDien) {
		this.AnhDaiDien = AnhDaiDien;
	}
	public String getMoTaNgan() {
		return MoTaNgan;
	}
	public void setMoTaNgan(String MoTaNgan) {
		this.MoTaNgan = MoTaNgan;
	}
	public Boolean getTrangThai() {
		return TrangThai;
	}
	public void setTrangThai(Boolean TrangThai) {
		this.TrangThai = TrangThai;
	}
	public String getBaiViet() {
		return BaiViet;
	}
	public DichVu() {
		super();
	}
	public DichVu(String TenDichVu, String AnhDaiDien, String MoTaNgan, Boolean TrangThai, String BaiViet,
			String ToaNha, List<DatDichVu> DatDichVu) {
		super();
		this.TenDichVu = TenDichVu;
		this.AnhDaiDien = AnhDaiDien;
		this.MoTaNgan = MoTaNgan;
		this.TrangThai = TrangThai;
		this.BaiViet = BaiViet;
		this.ToaNha = ToaNha;
		this.lstDatDichVu = DatDichVu;
	}
	public void setBaiViet(String BaiViet) {
		this.BaiViet = BaiViet;
	}
	public String getToaNha() {
		return ToaNha;
	}
	public void setToaNha(String ToaNha) {
		this.ToaNha = ToaNha;
	}
	
	@OneToMany(mappedBy = "dichVu", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DatDichVu> lstDatDichVu = new ArrayList<DatDichVu>();

//	public List<DatDichVu> getDatDichVu() {
//		return lstDatDichVu;
//	}
//	public void setDatDichVu(List<DatDichVu> DatDichVu) {
//		this.lstDatDichVu = DatDichVu;
//	}

	
}
