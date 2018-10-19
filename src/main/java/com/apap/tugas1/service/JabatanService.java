package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

/**
 * JabatanService
 */
public interface JabatanService {
	List<JabatanModel> getAllJabatan();
	void addJabatan(JabatanModel jabatan);
	JabatanModel getJabatanModelById(long id);
	void editJabatan(JabatanModel newJabatan, long id);
}
