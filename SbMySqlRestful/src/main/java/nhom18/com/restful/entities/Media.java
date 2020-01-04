package nhom18.com.restful.entities;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="media")
public class Media {
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name = "mediaid")
	 private Integer MediaID;
	 
	 @Column(name = "dichvuid")
     private Integer DichVuID;

	 @Column(name = "sanphamid")
     private Integer SanPhamID;

	 @Column(name = "laanh")
     private Integer LaAnh;

	 @Column(name = "duonglink")
     private String DuongLink;

	 @Column(name = "mota")
     private String MoTa;


	 @ManyToOne
	 @JoinColumn(name="dichvuid",nullable = true, insertable = false, updatable = false)	
	 @JsonIgnore
	 private DichVu dichVu;

	@ManyToOne
	@JoinColumn(name="sanphamid",nullable = true, insertable = false, updatable = false)	
	@JsonIgnore
	private SanPham sanPham;
		
     public Media(Integer dichVuID, Integer sanPhamID, Integer laAnh, String duongLink, String moTa, DichVu dichVu,
			SanPham sanPham) {
		super();
		DichVuID = dichVuID;
		SanPhamID = sanPhamID;
		LaAnh = laAnh;
		DuongLink = duongLink;
		MoTa = moTa;
		dichVu = dichVu;
		sanPham = sanPham;
	}

	public Media() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getMediaID() {
		return MediaID;
	}

	public void setMediaID(Integer mediaID) {
		MediaID = mediaID;
	}

	public Integer getDichVuID() {
		return DichVuID;
	}

	public void setDichVuID(Integer dichVuID) {
		DichVuID = dichVuID;
	}

	public Integer getSanPhamID() {
		return SanPhamID;
	}

	public void setSanPhamID(Integer sanPhamID) {
		SanPhamID = sanPhamID;
	}

	public Integer getLaAnh() {
		return LaAnh;
	}

	public void setLaAnh(Integer laAnh) {
		LaAnh = laAnh;
	}

	public String getDuongLink() {
		return DuongLink;
	}

	public void setDuongLink(String duongLink) {
		DuongLink = duongLink;
	}

	public String getMoTa() {
		return MoTa;
	}

	public void setMoTa(String moTa) {
		MoTa = moTa;
	}

	public DichVu getDichVu() {
		return dichVu;
	}

	public void setDichVu(DichVu dichVu) {
		dichVu = dichVu;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		sanPham = sanPham;
	}
	
}
