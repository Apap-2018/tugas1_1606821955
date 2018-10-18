package com.apap.tugas1.controller;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;
import com.apap.tugas1.service.InstansiService;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PegawaiController
 */
@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	public String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiModelByNip(nip);
		long gajiPegawai = pegawaiService.hitungGajiPegawai(nip);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", gajiPegawai);
		return "view-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	public String addPegawai(Model model) {
		List<ProvinsiModel> provinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> instansi = instansiService.getAllInstansi(); 
		List<JabatanModel> jabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("pegawai", new PegawaiModel());
		model.addAttribute("provinsiAll", provinsi);
		model.addAttribute("instansiAll", instansi);
		model.addAttribute("jabatanAll", jabatan);
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai) {
		pegawaiService.addPegawai(pegawai);
		
		return "add";
	}
}
