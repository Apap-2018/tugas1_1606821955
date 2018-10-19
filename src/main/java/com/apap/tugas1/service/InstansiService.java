package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;

/**
 * InstansiService
 */
public interface InstansiService {
	List<InstansiModel> getAllInstansi();
	InstansiModel getInstansiModelById(long id);
	void deletePegawaiInstansi(PegawaiModel pegawai, InstansiModel instansi);
}
