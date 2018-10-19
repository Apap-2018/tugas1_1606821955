package com.apap.tugas1.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.PegawaiDb;

/**
 * PegawaiServiceImpl
 */
@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Autowired
	private InstansiDb instansiDb;
	
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
	
	@Override
	public String generateNip(String id_instansi, Date tanggalLahir, String tahunMasuk) throws ParseException {
		java.text.SimpleDateFormat sdf =
				new java.text.SimpleDateFormat("dd-MM-yyyy");
		
		String tanggalLahir2 = sdf.format(tanggalLahir);
		String[] tanggalLahirSplit = tanggalLahir2.split("-");
		
		String nip = id_instansi;
		for (String split : tanggalLahirSplit) {
			nip += split;
		}
		
		nip += tahunMasuk;
		
		for (int i = 1; i < 100; i++) {
			String nipDummy = nip;
			if (i < 10) {
				nipDummy += 0;
			}
			
			nipDummy += i;
			
			if (this.getPegawaiModelByNip(nipDummy) == null) {
				nip = nipDummy;
				return nip;
			}
		}
		
		return null;
	}
	
	@Override
	public PegawaiModel getPegawaiTermuda(String id_instansi) {
		InstansiModel instansi = instansiDb.findById(Long.parseLong(id_instansi));
		PegawaiModel pegawaiTermuda = new PegawaiModel();
		
		java.text.SimpleDateFormat sdf =
				new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		String[] dateTertua = sdf.format(this.getPegawaiTertua(id_instansi).getTanggalLahir()).split("-");
		
		List<PegawaiModel> pegawaiList = instansi.getPegawai();
		for (PegawaiModel pegawai : pegawaiList) {
			String[] tanggalLahir = sdf.format(pegawai.getTanggalLahir()).split("-");
			if(Integer.parseInt(tanggalLahir[0]) > Integer.parseInt(dateTertua[0])) {
				pegawaiTermuda = pegawai;
				
			} else if(Integer.parseInt(tanggalLahir[0]) == Integer.parseInt(dateTertua[0])) {
				if (Integer.parseInt(tanggalLahir[1]) > Integer.parseInt(dateTertua[1])) {
					pegawaiTermuda = pegawai;
					
				} else if (Integer.parseInt(tanggalLahir[1]) == Integer.parseInt(dateTertua[1])) {
					if (Integer.parseInt(tanggalLahir[2]) > Integer.parseInt(dateTertua[2])) {
						pegawaiTermuda = pegawai;
						
					} else {
						continue;
					}
				} else {
					continue;
				}
			} else {
				continue;
			}
			
			dateTertua = new String[tanggalLahir.length];
			System.arraycopy(tanggalLahir, 0, dateTertua, 0, tanggalLahir.length);
		}
		
		return pegawaiTermuda;
	}
	
	@Override
	public PegawaiModel getPegawaiTertua(String id_instansi) {
		InstansiModel instansi = instansiDb.findById(Long.parseLong(id_instansi));
		PegawaiModel pegawaiTertua = new PegawaiModel();
		
		java.text.SimpleDateFormat sdf =
				new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		java.util.Date dt = new java.util.Date();
		String[] dateTermuda = sdf.format(dt).split("-");
		
		List<PegawaiModel> pegawaiList = instansi.getPegawai();
		for (PegawaiModel pegawai : pegawaiList) {
			String[] tanggalLahir = sdf.format(pegawai.getTanggalLahir()).split("-");
			if(Integer.parseInt(tanggalLahir[0]) < Integer.parseInt(dateTermuda[0])) {
				pegawaiTertua = pegawai;
				
			} else if(Integer.parseInt(tanggalLahir[0]) == Integer.parseInt(dateTermuda[0])) {
				if (Integer.parseInt(tanggalLahir[1]) < Integer.parseInt(dateTermuda[1])) {
					pegawaiTertua = pegawai;
					
				} else if (Integer.parseInt(tanggalLahir[1]) == Integer.parseInt(dateTermuda[1])) {
					if (Integer.parseInt(tanggalLahir[2]) < Integer.parseInt(dateTermuda[2])) {
						pegawaiTertua = pegawai;
						
					} else {
						continue;
					}
				} else {
					continue;
				}
			} else {
				continue;
			}
			
			dateTermuda = new String[tanggalLahir.length];
			System.arraycopy(tanggalLahir, 0, dateTermuda, 0, tanggalLahir.length);
		}
		
		return pegawaiTertua;
	}
}