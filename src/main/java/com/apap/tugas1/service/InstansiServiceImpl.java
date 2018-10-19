package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;

/**
 * InstansiServiceImpl
 */
@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	@Autowired
	private InstansiDb instansiDb;
	
	@Override
	public InstansiModel getInstansiModelById(long id) {
		return instansiDb.findById(id);
	}
	
	@Override
	public List<InstansiModel> getAllInstansi() {
		return instansiDb.findAll();
	}
	
	@Override
	public void deletePegawaiInstansi(PegawaiModel pegawai, InstansiModel instansi) {
		List<PegawaiModel> pegawaiList = instansi.getPegawai();
		
		for (int i = 0; i < pegawaiList.size(); i++) {
			if (pegawaiList.get(i) == pegawai) {
				pegawaiList.remove(i);
			}
		}
	}
}
