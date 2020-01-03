package stackjava.com.restful.service;

import stackjava.com.restful.entities.Media;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stackjava.com.restful.repository.MediaRepository;

@Service
public class MediaService {
	@Autowired
	private MediaRepository mediaRepository;
	public Iterable<Media> findAll(){
		return mediaRepository.findAll();
	}
	public Optional<Media> findById(int id){
		return mediaRepository.findById(id);
	}
    public Media save(Media me) {
        return mediaRepository.save(me);
    }
    public void delete(int id) {
    	mediaRepository.deleteById(id);
    }
    
	public List<Media> findByProperty( Integer dichVuID, Integer sanPhamID){
		return mediaRepository.findByProperty(dichVuID, sanPhamID);
	}
}
