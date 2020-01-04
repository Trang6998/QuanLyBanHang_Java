package nhom18.com.restful.service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nhom18.com.restful.entities.SanPham;
import nhom18.com.restful.entities.DonDatHang;
import nhom18.com.restful.repository.SanPhamRepository;

@Service
public class SanPhamService {
	@Autowired
	private SanPhamRepository sanPhamRepository;
	
	public Iterable<SanPham> findAll(){
		return sanPhamRepository.findAll();
	}

	public List<SanPham> findByProperty(String tenSanPham, Double toiThieu, Double toiDa, Integer nhaCungCapID, Integer loaiSanPhamID, Boolean trangThaiAnHien){
		return sanPhamRepository.findByProperty(tenSanPham, toiThieu, toiDa,nhaCungCapID, loaiSanPhamID, trangThaiAnHien);
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
