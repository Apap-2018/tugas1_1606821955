package com.apap.tugas1.service;

import java.sql.Date;
import java.text.ParseException;

import java.util.List;

import com.apap.tugas1.model.PegawaiModel;

/**
 * PegawaiService
 */
public interface PegawaiService {
	PegawaiModel getPegawaiModelByNip(String nip);
	PegawaiModel getPegawaiModelById(long id);
	long hitungGajiPegawai(String nip);
	void addPegawai(PegawaiModel pegawai);
	void editPegawai(PegawaiModel newPegawai, long id) throws ParseException;
	Date getTodayDefaultDate() throws ParseException ;
	String generateNip(String id_instansi, Date tanggalLahir, String tahunMasuk) throws ParseException;
	PegawaiModel getPegawaiTermuda(String id_instansi);
	PegawaiModel getPegawaiTertua(String id_instansi);
	List<PegawaiModel> getSelectedPegawai(long idInstansi, long idJabatan);
}
