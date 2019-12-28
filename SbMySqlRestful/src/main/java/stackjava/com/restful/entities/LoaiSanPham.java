package stackjava.com.restful.entities;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="loaisanpham")
public class LoaiSanPham {

	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  @Column(name = "loaisanphamid")
	  private Integer LoaiSanPhamID;

      @Column(name = "loaisanphampid")
	  private Integer LoaiSanPhamPID;

      @Column(name = "tenloai")
	  private String TenLoai;

      @Column(name = "mota")
	  private String MoTa;

      @Column(name = "anhdaidien")
	  private String AnhDaiDien;


 	  @OneToMany(mappedBy = "loaiSanPhamCha", cascade = CascadeType.ALL)
      private List<LoaiSanPham> lstLoaiSanPhamCon;

 	  @ManyToOne
      @JoinColumn(name="loaisanphampid",nullable = true, insertable = false, updatable = false)	
 	  @JsonIgnore
      private LoaiSanPham loaiSanPhamCha;

 	  @OneToMany(mappedBy = "loaiSanPham", cascade = CascadeType.ALL)
      private List<SanPham> lstSanPham;

 	  
	  public Integer getLoaiSanPhamID() {
		return LoaiSanPhamID;
	  }

	  public void setLoaiSanPhamID(Integer loaiSanPhamID) {
		  LoaiSanPhamID = loaiSanPhamID;
	  }

	  public Integer getLoaiSanPhamPID() {
		  return LoaiSanPhamPID;
	  }

	  public void setLoaiSanPhamPID(Integer loaiSanPhamPID) {
		  LoaiSanPhamPID = loaiSanPhamPID;
	  }

	  public String getTenLoai() {
		  return TenLoai;
	  }

	  public void setTenLoai(String tenLoai) {
		  TenLoai = tenLoai;
	  }

	  public String getMoTa() {
		  return MoTa;
	  }

	  public void setMoTa(String moTa) {
		  MoTa = moTa;
	  }

	  public String getAnhDaiDien() {
		  return AnhDaiDien;
	  }

	  public void setAnhDaiDien(String anhDaiDien) {
		  AnhDaiDien = anhDaiDien;
	  }

	  public List<LoaiSanPham> getLoaiSanPhamCon() {
		  return lstLoaiSanPhamCon;
	  }

	  public LoaiSanPham(Integer loaiSanPhamPID, String tenLoai, String moTa, String anhDaiDien,
			  List<LoaiSanPham> LoaiSanPhamCon, LoaiSanPham LoaiSanPhamCha, List<SanPham> sanPham) {
		super();
		LoaiSanPhamPID = loaiSanPhamPID;
		TenLoai = tenLoai;
		MoTa = moTa;
		AnhDaiDien = anhDaiDien;
		lstLoaiSanPhamCon = LoaiSanPhamCon;
		loaiSanPhamCha = LoaiSanPhamCha;
		lstSanPham = sanPham;
	  }

	  public LoaiSanPham() {
		super();
		// TODO Auto-generated constructor stub
	  }

	  public void setLoaiSanPhamCon(List<LoaiSanPham> LoaiSanPhamCon) {
		  lstLoaiSanPhamCon = LoaiSanPhamCon;
	  }

	  public LoaiSanPham getLoaiSanPhamCha() {
		  return loaiSanPhamCha;
	  }

	  public void setLoaiSanPhamCha(LoaiSanPham LoaiSanPhamCha) {
		  loaiSanPhamCha = LoaiSanPhamCha;
	  }

	  public List<SanPham> getSanPham() {
		  return lstSanPham;
	  }

	  public void setSanPham(List<SanPham> sanPham) {
		  lstSanPham = sanPham;
	  }

}
