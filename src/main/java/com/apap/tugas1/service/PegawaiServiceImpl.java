package com.apap.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

/**
 * PegawaiServiceImpl
 */
@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiModelByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}
	
	@Override
	public long hitungGajiPegawai(String nip) {
		PegawaiModel pegawai = this.getPegawaiModelByNip(nip);
		
		Double gajiPokokTerbesar = 0.0;
		
		for(JabatanPegawaiModel jabatanPegawai : pegawai.getJabatanPegawai()) {
			if(jabatanPegawai.getJabatan().getGajiPokok() > gajiPokokTerbesar) {
				gajiPokokTerbesar = jabatanPegawai.getJabatan().getGajiPokok();
			}
		}
		
		Double totalGajiDouble = gajiPokokTerbesar + ((pegawai.getInstansi().getProvinsi().getPresentaseTunjangan() / 100) *
				gajiPokokTerbesar);
		
		long totalGaji = Math.round(totalGajiDouble);
		
		return totalGaji;
	}
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}
}
