package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.ArrayList;

import com.apap.tugas1.service.ProvinsiService;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

/**
 * InstansiController
 */
@Controller
public class InstansiController {
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping(value = "/instansi/get", method = RequestMethod.GET)
	public @ResponseBody List<InstansiModel> getInstansi(@RequestParam("idProvinsi") long id) {
		ProvinsiModel provinsi = provinsiService.getProvinsiModelById(id);
		List<InstansiModel> instansiSelected;
		
		if (provinsi != null) {
			instansiSelected = provinsi.getInstansi();
		} else {
			instansiSelected = new ArrayList<>();
		}
		
		return instansiSelected;
	}
}
