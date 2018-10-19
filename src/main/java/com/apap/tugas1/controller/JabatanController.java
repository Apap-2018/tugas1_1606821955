package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

/**
 * JabatanController
 */
@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	public String addJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	public String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		
		model.addAttribute("jabatan", jabatan);
		
		return "add-jabatan-berhasil";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam("idJabatan") String id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanModelById(Long.parseLong(id));
		
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	public String editJabatan(@RequestParam("idJabatan") String id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanModelById(Long.parseLong(id));
		
		model.addAttribute("jabatan", jabatan);
		return "edit-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	public String editJabatanPost(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.editJabatan(jabatan, jabatan.getId());
		JabatanModel jabatanChange = jabatanService.getJabatanModelById(jabatan.getId());
		
		model.addAttribute("jabatan", jabatanChange);
		return "edit-jabatan-berhasil";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	public String deleteJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel jabatanToDelete = jabatanService.getJabatanModelById(jabatan.getId());
		String nama = jabatanToDelete.getNama();
		
		boolean deleteSuccess = jabatanService.deleteJabatan(jabatanToDelete);
		
		if (deleteSuccess) {
			model.addAttribute("status", "sukses");
		} else {
			model.addAttribute("status", "gagal");
		}
		
		model.addAttribute("nama", nama);
		return "delete-jabatan";
	}
}