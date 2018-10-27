package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.ProvinsiModel;

/**
 * ProvinsiService
 */
public interface ProvinsiService {
	List<ProvinsiModel> getAllProvinsi();
	ProvinsiModel getProvinsiModelById(long id);
}
