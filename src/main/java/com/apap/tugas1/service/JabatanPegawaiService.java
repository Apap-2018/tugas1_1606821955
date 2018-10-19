package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanPegawaiModel;

/**
 * JabatanPegawaiService
 */
public interface JabatanPegawaiService {
	void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai);
	JabatanPegawaiModel getJabatanPegawaiModelById(long id);
}
