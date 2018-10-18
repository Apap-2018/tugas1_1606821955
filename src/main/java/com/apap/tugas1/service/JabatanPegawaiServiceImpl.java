package com.apap.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.repository.JabatanPegawaiDb;

import com.apap.tugas1.model.JabatanPegawaiModel;

/**
 * JabatanPegawaiServiceImpl
 */
@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService {
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
	public void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai) {
		jabatanPegawaiDb.save(jabatanPegawai);
	}
}
