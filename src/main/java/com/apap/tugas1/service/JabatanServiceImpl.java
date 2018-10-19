package com.apap.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;

/**
 * JabatanServiceImpl
 */
@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public List<JabatanModel> getAllJabatan() {
		return jabatanDb.findAll();
	}
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}
	
	@Override
	public JabatanModel getJabatanModelById(long id) {
		return jabatanDb.findById(id);
	}
	
	@Override
	public void editJabatan(JabatanModel newJabatan, long id) {
		JabatanModel jabatan = jabatanDb.findById(id);
		
		jabatan.setNama(newJabatan.getNama());
		jabatan.setDeskripsi(newJabatan.getDeskripsi());
		jabatan.setGajiPokok(newJabatan.getGajiPokok());
		jabatanDb.save(jabatan);
	}
	
	@Override
	public boolean deleteJabatan(JabatanModel jabatan) {
		List<JabatanPegawaiModel> listJabatanPegawai = jabatan.getJabatanPegawai();
		
		if (listJabatanPegawai.size() != 0) {
			return false;	// Gagal dihapus
		} else {
			jabatanDb.delete(jabatan);
			return true;	// Berhasil dihapus
		}
	}
}
