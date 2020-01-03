package stackjava.com.restful.service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import stackjava.com.restful.entities.SanPham;
import stackjava.com.restful.entities.DonDatHang;
import stackjava.com.restful.repository.SanPhamRepository;

@Service
public class SanPhamService {
	@Autowired
	private SanPhamRepository sanPhamRepository;
	
	public Iterable<SanPham> findAll(){
		return sanPhamRepository.findAll();
	}

	public List<SanPham> findByProperty(String tenSanPham, Double toiThieu, Double toiDa, Integer loaiSanPhamID, Boolean trangThaiAnHien){
		return sanPhamRepository.findByProperty(tenSanPham, toiThieu, toiDa, loaiSanPhamID, trangThaiAnHien);
	}
	public Optional<SanPham> findById(int id){
		return sanPhamRepository.findById(id);
	}
    public SanPham save(SanPham dv) {
        return sanPhamRepository.save(dv);
    }
    public void delete(int id) {
    	sanPhamRepository.deleteById(id);
    }
}