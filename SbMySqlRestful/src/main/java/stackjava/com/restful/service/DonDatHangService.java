package stackjava.com.restful.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import stackjava.com.restful.entities.ChiTietDonDatHang;
import stackjava.com.restful.entities.DonDatHang;
import stackjava.com.restful.repository.DonDatHangRepository;

@Service
public class DonDatHangService {
	@Autowired
	private DonDatHangRepository donDatHangRepository;
	
	public Iterable<DonDatHang> findAll(){
		return donDatHangRepository.findAll();
	}
	public List<DonDatHang> findByProperty(String soHieuDon, Integer taiKhoanDatHangID, Integer taiKhoanNhanVienID, Date tuNgay, Date denNgay, Integer trangThai){
		return donDatHangRepository.findByProperty(soHieuDon, taiKhoanDatHangID, taiKhoanNhanVienID, tuNgay, denNgay, trangThai);
	}
	public List<ChiTietDonDatHang> findByTaiKhoan(Integer taiKhoanDatHangID){
		return donDatHangRepository.findByTaiKhoan(taiKhoanDatHangID).isPresent() ? donDatHangRepository.findByTaiKhoan(taiKhoanDatHangID).get().getLstChiTietDonDatHang() : new ArrayList<ChiTietDonDatHang>();
	}
	public DonDatHang getGioHang(Integer taiKhoanDatHangID){
		return donDatHangRepository.findByTaiKhoan(taiKhoanDatHangID).isPresent() ? donDatHangRepository.findByTaiKhoan(taiKhoanDatHangID).get() : new DonDatHang();
	}
	public Optional<DonDatHang> findById(int id){
		return donDatHangRepository.findById(id);
	}
    public DonDatHang save(DonDatHang dv) {
        return donDatHangRepository.save(dv);
    }
    public void delete(int id) {
    	donDatHangRepository.deleteById(id);
    }
}
