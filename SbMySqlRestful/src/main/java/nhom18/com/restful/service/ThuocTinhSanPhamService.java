package nhom18.com.restful.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nhom18.com.restful.entities.ThuocTinhSanPham;

import nhom18.com.restful.repository.ThuocTinhSanPhamRepository;
@Service
public class ThuocTinhSanPhamService {
	@Autowired
	private ThuocTinhSanPhamRepository thuocTinhSanPhamRepository;
	
	public Iterable<ThuocTinhSanPham> findAll(){
		return thuocTinhSanPhamRepository.findAll();
	}
	
	public Iterable<ThuocTinhSanPham> findBySanPhamId(Integer sanPhamID){
		return thuocTinhSanPhamRepository.findBySanPhamId(sanPhamID);
	}
	
	public Optional<ThuocTinhSanPham> findById(int id){
		return thuocTinhSanPhamRepository.findById(id);
	}
	
    public ThuocTinhSanPham save(ThuocTinhSanPham tt) {
        return thuocTinhSanPhamRepository.save(tt);
    }
    public void delete(int id) {
    	thuocTinhSanPhamRepository.deleteById(id);
    }
}
