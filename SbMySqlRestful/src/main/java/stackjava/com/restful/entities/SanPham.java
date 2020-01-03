package stackjava.com.restful.entities;

import java.util.Date;
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
@Table(name="sanpham")
public class SanPham {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "sanphamid")
	private Integer SanPhamID;

	@Column(name = "loaisanphamid")
	private Integer LoaiSanPhamID;

	@Column(name = "donvitinhid")
	private Integer DonViTinhID;

	@Column(name = "kyhieusanpham")
	private String KyHieuSanPham;

	@Column(name = "tensanpham")
	private String TenSanPham;

	@Column(name = "anhsanpham")
    private String AnhSanPham;

	@Column(name = "motangan")
    private String MoTaNgan;

	@Column(name = "mota")
    private String MoTa;

	@Column(name = "nhasanxuat")
    private String NhaSanXuat;

	@Column(name = "anhiensanpham")
    private Boolean AnHienSanPham;

	@Column(name = "anhiennhasanxuat")
    private Boolean AnHienNhaSanXuat;

	@Column(name = "giaban")
    private Double GiaBan;

	@Column(name = "toanha")
    private String ToaNha;

	@Column(name = "thutu")
    private Integer ThuTu;

	@Column(name = "ngaydang")
    private Date NgayDang;

	@Column(name = "lasanphammoi")
    private Boolean LaSanPhamMoi;

	@Column(name = "nhacungcapid")
    private Integer NhaCungCapID;

	@OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ChiTietDonDatHang> lstChiTietDonDatHang;
	
	@OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    private List<Media> lstMedia;
	
	@OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<ThuocTinhSanPham> lstThuocTinhSanPham;
	
	@ManyToOne
    @JoinColumn(name="donvitinhid",nullable = true, insertable = false, updatable = false)	
	@JsonIgnore
    private DonViTinh donViTinh;
	
	@ManyToOne
    @JoinColumn(name="loaisanphamid",nullable = true, insertable = false, updatable = false)	
	@JsonIgnore
    private LoaiSanPham loaiSanPham;
	
	@ManyToOne
    @JoinColumn(name="nhacungcapid",nullable = true, insertable = false, updatable = false)	
	@JsonIgnore
    private NhaCungCap nhaCungCap;
    
	@Transient
	private String TenLoai;

	public String getTenLoai() {
	    return loaiSanPham.getTenLoai();
	}
	
    public SanPham() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SanPham(Integer loaiSanPhamID, Integer donViTinhID, String kyHieuSanPham, String tenSanPham, String anhSanPham,
			String moTaNgan, String moTa, String nhaSanXuat, Boolean anHienSanPham, Boolean anHienNhaSanXuat,
			Double giaBan, String toaNha, Integer thuTu, Date ngayDang, Boolean laSanPhamMoi, Integer nhaCungCapID,
			List<ChiTietDonDatHang> chiTietDonDatHang, DonViTinh donViTinh,
			LoaiSanPham loaiSanPham, NhaCungCap nhaCungCap, List<Media> media,
			List<ThuocTinhSanPham> thuocTinhSanPham) {
		super();
		LoaiSanPhamID = loaiSanPhamID;
		DonViTinhID = donViTinhID;
		KyHieuSanPham = kyHieuSanPham;
		TenSanPham = tenSanPham;
		AnhSanPham = anhSanPham;
		MoTaNgan = moTaNgan;
		MoTa = moTa;
		NhaSanXuat = nhaSanXuat;
		AnHienSanPham = anHienSanPham;
		AnHienNhaSanXuat = anHienNhaSanXuat;
		GiaBan = giaBan;
		ToaNha = toaNha;
		ThuTu = thuTu;
		NgayDang = ngayDang;
		LaSanPhamMoi = laSanPhamMoi;
		NhaCungCapID = nhaCungCapID;
		lstChiTietDonDatHang = chiTietDonDatHang;
		donViTinh = donViTinh;
		loaiSanPham = loaiSanPham;
		nhaCungCap = nhaCungCap;
		lstMedia = media;
		lstThuocTinhSanPham = thuocTinhSanPham;
	}

	public Integer getSanPhamID() {
		return SanPhamID;
	}

	public void setSanPhamID(Integer sanPhamID) {
		SanPhamID = sanPhamID;
	}

	public Integer getLoaiSanPhamID() {
		return LoaiSanPhamID;
	}

	public void setLoaiSanPhamID(Integer loaiSanPhamID) {
		LoaiSanPhamID = loaiSanPhamID;
	}

	public Integer getDonViTinhID() {
		return DonViTinhID;
	}

	public void setDonViTinhID(Integer donViTinhID) {
		DonViTinhID = donViTinhID;
	}

	public String getKyHieuSanPham() {
		return KyHieuSanPham;
	}

	public void setKyHieuSanPham(String kyHieuSanPham) {
		KyHieuSanPham = kyHieuSanPham;
	}

	public String getTenSanPham() {
		return TenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		TenSanPham = tenSanPham;
	}

	public String getAnhSanPham() {
		return AnhSanPham;
	}

	public void setAnhSanPham(String anhSanPham) {
		AnhSanPham = anhSanPham;
	}

	public String getMoTaNgan() {
		return MoTaNgan;
	}

	public void setMoTaNgan(String moTaNgan) {
		MoTaNgan = moTaNgan;
	}

	public String getMoTa() {
		return MoTa;
	}

	public void setMoTa(String moTa) {
		MoTa = moTa;
	}

	public String getNhaSanXuat() {
		return NhaSanXuat;
	}

	public void setNhaSanXuat(String nhaSanXuat) {
		NhaSanXuat = nhaSanXuat;
	}

	public Boolean getAnHienSanPham() {
		return AnHienSanPham;
	}

	public void setAnHienSanPham(Boolean anHienSanPham) {
		AnHienSanPham = anHienSanPham;
	}

	public Boolean getAnHienNhaSanXuat() {
		return AnHienNhaSanXuat;
	}

	public void setAnHienNhaSanXuat(Boolean anHienNhaSanXuat) {
		AnHienNhaSanXuat = anHienNhaSanXuat;
	}

	public Double getGiaBan() {
		return GiaBan;
	}

	public void setGiaBan(Double giaBan) {
		GiaBan = giaBan;
	}

	public String getToaNha() {
		return ToaNha;
	}

	public void setToaNha(String toaNha) {
		ToaNha = toaNha;
	}

	public Integer getThuTu() {
		return ThuTu;
	}

	public void setThuTu(Integer thuTu) {
		ThuTu = thuTu;
	}

	public Date getNgayDang() {
		return NgayDang;
	}

	public void setNgayDang(Date ngayDang) {
		NgayDang = ngayDang;
	}

	public Boolean getLaSanPhamMoi() {
		return LaSanPhamMoi;
	}

	public void setLaSanPhamMoi(Boolean laSanPhamMoi) {
		LaSanPhamMoi = laSanPhamMoi;
	}

	public Integer getNhaCungCapID() {
		return NhaCungCapID;
	}

	public void setNhaCungCapID(Integer nhaCungCapID) {
		NhaCungCapID = nhaCungCapID;
	}

//	public List<ChiTietDonDatHang> getChiTietDonDatHang() {
//		return lstChiTietDonDatHang;
//	}
//
//	public void setChiTietDonDatHang(List<ChiTietDonDatHang> chiTietDonDatHang) {
//		lstChiTietDonDatHang = chiTietDonDatHang;
//	}

	public DonViTinh getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(DonViTinh donViTinh) {
		this.donViTinh = donViTinh;
	}

	public LoaiSanPham getLoaiSanPham() {
		return loaiSanPham;
	}

	public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
		loaiSanPham = loaiSanPham;
	}

	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		nhaCungCap = nhaCungCap;
	}

	public List<Media> getMedia() {
		return lstMedia;
	}

	public void setMedia(List<Media> media) {
		lstMedia = media;
	}

	public List<ThuocTinhSanPham> getThuocTinhSanPham() {
		return lstThuocTinhSanPham;
	}

	public void setThuocTinhSanPham(List<ThuocTinhSanPham> thuocTinhSanPham) {
		lstThuocTinhSanPham = thuocTinhSanPham;
	}

	
}
