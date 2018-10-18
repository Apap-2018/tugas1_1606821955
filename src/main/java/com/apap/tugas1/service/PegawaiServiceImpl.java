package com.apap.tugas1.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	
	@Override
	public Date getTodayDefaultDate() throws ParseException {
		java.util.Date dt = new java.util.Date();
		
		java.text.SimpleDateFormat sdf =
				new java.text.SimpleDateFormat("dd-MM-yyyy");
		
		String currentTime = sdf.format(dt);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date date = sdf2.parse(currentTime);
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
		
		return sqlStartDate;
	}
	
	public String generateNip(String id_instansi, Date tanggalLahir) throws ParseException {
		java.text.SimpleDateFormat sdf =
				new java.text.SimpleDateFormat("dd-MM-yyyy");
		
		String tanggalLahir2 = sdf.format(tanggalLahir);
		String[] tanggalLahirSplit = tanggalLahir2.split("-");
		
		String nip = id_instansi;
		for (String split : tanggalLahirSplit) {
			nip += split;
		}
		
		String tahunSekarang = sdf.format(this.getTodayDefaultDate()).split("-")[2];
		nip += tahunSekarang;
		
		for (int i = 1; i < 100; i++) {
			String nipDummy = nip;
			if (i < 10) {
				nipDummy += 0;
			}
			
			nipDummy += i;
			
			if (this.getPegawaiModelByNip(nipDummy) != null) {
				nip = nipDummy;
				return nip;
			}
		}
		
		return null;
	}
}
