package nhom18.com.restful.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="datdichvu")
public class DatDichVu {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "datdichvuid")
	private Integer DatDichVuID;
	
	@Column(name = "dichvuid")
	private Integer DichVuID;
	
	@Column(name = "userid")
	private Integer UserID;
	
	@Column(name = "ngaydat")
	private Date NgayDat;
	
	@Column(name = "yeucau")
	private String YeuCau;
	
	@Column(name = "ghichu")
	private String GhiChu;
	
	@ManyToOne
    @JoinColumn(name="userid",nullable = true, insertable = false, updatable = false)	
	private Users users;

	@ManyToOne
	@JoinColumn(name="dichvuid",nullable = true, insertable = false, updatable = false)
	private DichVu dichVu;

	
	public Integer getDatDichVuID() {
		return DatDichVuID;
	}
	public void setDatDichVuID(Integer DatDichVuID) {
		this.DatDichVuID = DatDichVuID;
	}
	public Integer getDichVuID() {
		return DichVuID;
	}
	public void setDichVuID(Integer DichVuID) {
		this.DichVuID = DichVuID;
	}
	public Integer getUserID() {
		return UserID;
	}
	public void setUserID(Integer UserID) {
		this.UserID = UserID;
	}
	public Date getNgayDat() {
		return NgayDat;
	}
	public void setNgayDat(Date NgayDat) {
		this.NgayDat = NgayDat;
	}
	public String getYeuCau() {
		return YeuCau;
	}
	public void setYeuCau(String YeuCau) {
		this.YeuCau = YeuCau;
	}
	public String getGhiChu() {
		return GhiChu;
	}
	public void setGhiChu(String GhiChu) {
		this.GhiChu = GhiChu;
	}
	public DatDichVu() {
		super();
	}
	public DatDichVu(Integer DichVuID, Integer UserID, Date NgayDat, String YeuCau, String GhiChu, DichVu DichVu, Users users) {
		super();
		this.DichVuID = DichVuID;
		this.UserID = UserID;
		this.NgayDat = NgayDat;
		this.YeuCau = YeuCau;
		this.GhiChu = GhiChu;
		this.dichVu = DichVu;
		this.users = users;
	}
	
	
	public DichVu getDichVu() {
		return dichVu;
	}
	public void setDichVu(DichVu DichVu) {
		this.dichVu = DichVu;
	}

	public Users getUsers() {
		return users;
	}
	public void setUsers(Users Users) {
		this.users = Users;
	}
	
}

