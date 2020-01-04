package nhom18.com.restful.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nhom18.com.restful.entities.DonViTinh;
import nhom18.com.restful.repository.DonViTinhRepository;;

@Service
public class DonViTinhService {
	@Autowired
	private DonViTinhRepository donViTinhRepository;
	
	public Iterable<DonViTinh> findAll(){
		return donViTinhRepository.findAll();
	}
	
	public Iterable<DonViTinh> findByName(String tenDonVi){
		return donViTinhRepository.findByName(tenDonVi);
	}
	
	public Optional<DonViTinh> findById(int id){
		return donViTinhRepository.findById(id);
	}
	
    public DonViTinh save(DonViTinh dvt) {
        return donViTinhRepository.save(dvt);
    }
    public void delete(int id) {
    	donViTinhRepository.deleteById(id);
    }
}
