package nhom18.com.restful.service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nhom18.com.restful.entities.DonDatHang;
import nhom18.com.restful.entities.LoaiSanPham;
import nhom18.com.restful.repository.LoaiSanPhamRepository;

@Service
public class LoaiSanPhamService {
	@Autowired
	private LoaiSanPhamRepository loaiSanPhamRepository;
	
	public Iterable<LoaiSanPham> findAll(){
		return loaiSanPhamRepository.findAll();
	}
	public List<LoaiSanPham> findByProperty(String tenLoai, Boolean laDanhMucCha, Integer loaiSanPhamPID, Integer loaiSanPhamID){
		return loaiSanPhamRepository.findByProperty(tenLoai, laDanhMucCha, loaiSanPhamPID, loaiSanPhamID);
	}
	public Optional<LoaiSanPham> findById(int id){
		return loaiSanPhamRepository.findById(id);
	}
    public LoaiSanPham save(LoaiSanPham dv) {
        return loaiSanPhamRepository.save(dv);
    }
    public void delete(int id) {
    	loaiSanPhamRepository.deleteById(id);
    }
}
