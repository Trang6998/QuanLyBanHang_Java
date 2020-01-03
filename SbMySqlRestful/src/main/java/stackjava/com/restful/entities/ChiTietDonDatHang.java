package stackjava.com.restful.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@Table(name="chitietdondathang")
public class ChiTietDonDatHang {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "chitietdondathangid")
	private Integer ChiTietDonDatHangID;
	
	@Column(name = "dondathangid")
    private Integer DonDatHangID;
	
	@Column(name = "sanphamid")
	private Integer SanPhamID;

	@Column(name = "soluong")
    private Integer SoLuong;

	@Column(name = "giaxuat")
    private Double GiaXuat;

	@ManyToOne
    @JoinColumn(name="dondathangid",insertable = false, updatable = false)	
	@JsonIgnore
    private DonDatHang donDatHang;
	
	@ManyToOne
    @JoinColumn(name="sanphamid",nullable = true, insertable = false, updatable = false)	
    private SanPham sanPham;

    @Transient
    private Double ThanhTien;

    public Double getThanhTien() {
    	return GiaXuat*SoLuong;
    }

    public ChiTietDonDatHang(Integer donDatHangID, Integer sanPhamID, Integer soLuong, Double giaXuat,
			DonDatHang donDatHang, SanPham sanPham) {
		super();
		this.DonDatHangID = donDatHangID;
		this.SanPhamID = sanPhamID;
		this.SoLuong = soLuong;
		this.GiaXuat = giaXuat;
		this.donDatHang = donDatHang;
		this.sanPham = sanPham;
	}

	public ChiTietDonDatHang() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getChiTietDonDatHangID() {
		return ChiTietDonDatHangID;
	}

	public void setChiTietDonDatHangID(Integer chiTietDonDatHangID) {
		this.ChiTietDonDatHangID = chiTietDonDatHangID;
	}

	public Integer getDonDatHangID() {
		return DonDatHangID;
	}

	public void setDonDatHangID(Integer donDatHangID) {
		this.DonDatHangID = donDatHangID;
	}

	public Integer getSanPhamID() {
		return SanPhamID;
	}

	public void setSanPhamID(Integer sanPhamID) {
		this.SanPhamID = sanPhamID;
	}

	public Integer getSoLuong() {
		return SoLuong;
	}

	public void setSoLuong(Integer soLuong) {
		this.SoLuong = soLuong;
	}

	public Double getGiaXuat() {
		return GiaXuat;
	}

	public void setGiaXuat(Double giaXuat) {
		this.GiaXuat = giaXuat;
	}

	public DonDatHang getDonDatHang() {
		return this.donDatHang;
	}

	public void setDonDatHang(DonDatHang donDatHang) {
		this.donDatHang = donDatHang;
	}

	public SanPham getSanPham() {
		return this.sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
}
