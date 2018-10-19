package com.apap.tugas1.controller;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanPegawaiService;

import java.util.List;
import java.text.ParseException;
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
	private JabatanPegawaiService jabatanPegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> jabatan = jabatanService.getAllJabatan();
		List<InstansiModel> instansi = instansiService.getAllInstansi();
		
		model.addAttribute("jabatanAll", jabatan);
		model.addAttribute("instansiAll", instansi);
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
	public String addPegawai(Model model) throws ParseException {
		List<ProvinsiModel> provinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> instansi = instansiService.getAllInstansi(); 
		List<JabatanModel> jabatan = jabatanService.getAllJabatan();
		
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setTanggalLahir(pegawaiService.getTodayDefaultDate());
		List<JabatanPegawaiModel> listJabatanPegawai = new ArrayList<>();
		pegawai.setJabatanPegawai(listJabatanPegawai);
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		pegawai.getJabatanPegawai().add(jabatanPegawai);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("provinsiAll", provinsi);
		model.addAttribute("instansiAll", instansi);
		model.addAttribute("jabatanAll", jabatan);
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", params = {"addJabatanPegawai"})
	public String addJabatanPegawai(@ModelAttribute PegawaiModel pegawai, Model model) {
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		List<ProvinsiModel> provinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> instansi = instansiService.getAllInstansi(); 
		List<JabatanModel> jabatan = jabatanService.getAllJabatan();
		
		pegawai.getJabatanPegawai().add(jabatanPegawai);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("provinsiAll", provinsi);
		model.addAttribute("instansiAll", instansi);
		model.addAttribute("jabatanAll", jabatan);
		return "add-pegawai";
	}
	
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) throws ParseException {
		List<JabatanPegawaiModel> jabatanPegawai = pegawai.getJabatanPegawai();
		
		for(JabatanPegawaiModel jabatanPeg : jabatanPegawai) {
			jabatanPeg.setPegawai(pegawai);
			jabatanPegawaiService.addJabatanPegawai(jabatanPeg);
		}
		
		pegawai.setNip(pegawaiService.generateNip(Long.toString(pegawai.getInstansi().getId()), pegawai.getTanggalLahir()));
		pegawaiService.addPegawai(pegawai);
		
		instansiService.getInstansiModelById(pegawai.getInstansi().getId()).getPegawai().add(pegawai);
		
		model.addAttribute("pegawai", pegawai);
		
		return "add-pegawai-berhasil";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	public String viewPegawaiTermudaTertua(@RequestParam("idInstansi") String id, Model model) {
		PegawaiModel pegawaiTermuda = pegawaiService.getPegawaiTermuda(id);
		PegawaiModel pegawaiTertua = pegawaiService.getPegawaiTertua(id);
		long gajiPegawaiTermuda = pegawaiService.hitungGajiPegawai(pegawaiTermuda.getNip());
		long gajiPegawaiTertua = pegawaiService.hitungGajiPegawai(pegawaiTertua.getNip());
		
		model.addAttribute("pegawaiMuda", pegawaiTermuda);
		model.addAttribute("pegawaiTua", pegawaiTertua);
		model.addAttribute("gajiPegawaiMuda", gajiPegawaiTermuda);
		model.addAttribute("gajiPegawaiTua", gajiPegawaiTertua);
		return "view-pegawai-termuda-tertua";
	}
}