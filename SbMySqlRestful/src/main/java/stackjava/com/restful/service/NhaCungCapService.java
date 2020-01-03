package stackjava.com.restful.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stackjava.com.restful.entities.NhaCungCap;
import stackjava.com.restful.repository.NhaCungCapRepository;

@Service
public class NhaCungCapService {
	@Autowired
	private NhaCungCapRepository nhaCungCapRepository;
	
	public Iterable<NhaCungCap> findAll(){
		return nhaCungCapRepository.findAll();
	}
	
	public Iterable<NhaCungCap> findByName(String tenNhaCungCap){
		return nhaCungCapRepository.findByName(tenNhaCungCap);
	}
	
	public Optional<NhaCungCap> findById(int id){
		return nhaCungCapRepository.findById(id);
	}
	
    public NhaCungCap save(NhaCungCap ncc) {
        return nhaCungCapRepository.save(ncc);
    }
    public void delete(int id) {
    	nhaCungCapRepository.deleteById(id);
    }
}
