package com.apap.tugas1.service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProvinsiServiceImpl
 */
@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService {
	@Autowired
	private ProvinsiDb provinsiDb;
	
	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		return provinsiDb.findAll();
	}
}
