package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;

/**
 * PegawaiService
 */
public interface PegawaiService {
	PegawaiModel getPegawaiModelByNip(String nip);
	long hitungGajiPegawai(String nip);
	void addPegawai(PegawaiModel pegawai);
}
