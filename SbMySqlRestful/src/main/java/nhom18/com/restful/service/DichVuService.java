package nhom18.com.restful.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nhom18.com.restful.entities.DichVu;
import nhom18.com.restful.repository.DichVuRepository;

@Service
public class DichVuService {
	@Autowired
	private DichVuRepository dichVuRepository;
	
	public Iterable<DichVu> findAll(){
		return dichVuRepository.findAll();
	}
	public List<DichVu> findByName(String tenDichVu){
		return dichVuRepository.findByName(tenDichVu);
	}
	public List<DichVu> findByTrangThai(Boolean trangThai){
		return dichVuRepository.findByTrangThai(trangThai);
	}
	public Optional<DichVu> findById(int id){
		return dichVuRepository.findById(id);
	}
    public DichVu save(DichVu dv) {
        return dichVuRepository.save(dv);
    }
    public void delete(int id) {
    	dichVuRepository.deleteById(id);
    }
}
