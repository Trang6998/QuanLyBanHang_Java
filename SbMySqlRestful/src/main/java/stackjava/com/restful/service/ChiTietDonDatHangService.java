package stackjava.com.restful.service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import stackjava.com.restful.entities.ChiTietDonDatHang;
import stackjava.com.restful.entities.DonDatHang;
import stackjava.com.restful.repository.ChiTietDonDatHangRepository;

@Service
public class ChiTietDonDatHangService {
	@Autowired
	private ChiTietDonDatHangRepository chiTietDonDatHangRepository;
	
	public Iterable<ChiTietDonDatHang> findAll(){
		return chiTietDonDatHangRepository.findAll();
	}

	public List<ChiTietDonDatHang> findByProperty(Integer donDatHangID, Integer sanPhamID, Integer nhaCungCapID, Date tuNgay, Date denNgay, Integer trangThai){
		return chiTietDonDatHangRepository.findByProperty(donDatHangID, sanPhamID, nhaCungCapID, tuNgay, denNgay, trangThai);
	}
	public ChiTietDonDatHang findBySanPham(Integer donDatHangID, Integer sanPhamID){
		return chiTietDonDatHangRepository.findBySanPham(donDatHangID, sanPhamID);
	}
	public Optional<ChiTietDonDatHang> findById(int id){
		return chiTietDonDatHangRepository.findById(id);
	}
    public ChiTietDonDatHang save(ChiTietDonDatHang dv) {
        return chiTietDonDatHangRepository.save(dv);
    }
    public void delete(int id) {
    	chiTietDonDatHangRepository.deleteById(id);
    }
}
