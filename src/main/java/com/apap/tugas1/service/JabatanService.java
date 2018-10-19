package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;

/**
 * JabatanService
 */
public interface JabatanService {
	List<JabatanModel> getAllJabatan();
	void addJabatan(JabatanModel jabatan);
	JabatanModel getJabatanModelById(long id);
	void editJabatan(JabatanModel newJabatan, long id);
	boolean deleteJabatan(JabatanModel jabatan);
	void deleteJabatanPegawaiModel(JabatanPegawaiModel jabatanPegawai, JabatanModel jabatan);
}
