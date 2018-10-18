package com.apap.tugas1.controller;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.PegawaiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
}
