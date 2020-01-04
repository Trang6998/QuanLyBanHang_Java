package stackjava.com.restful.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import stackjava.com.restful.entities.ThuocTinh;

import stackjava.com.restful.repository.ThuocTinhRepository;

@Service
public class ThuocTinhService {
	@Autowired
	private ThuocTinhRepository thuocTinhRepository;
	
	public Iterable<ThuocTinh> findAll(){
		return thuocTinhRepository.findAll();
	}
	
	public Iterable<ThuocTinh> findByName(String tenThuocTinh){
		return thuocTinhRepository.findByName(tenThuocTinh);
	}
	
	public Optional<ThuocTinh> findById(int id){
		return thuocTinhRepository.findById(id);
	}
	
    public ThuocTinh save(ThuocTinh tt) {
        return thuocTinhRepository.save(tt);
    }
    public void delete(int id) {
    	thuocTinhRepository.deleteById(id);
    }
}
