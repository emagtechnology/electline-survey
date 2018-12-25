package com.el.rest.modelImp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.el.rest.model.BoothOfVidhansabha;
import com.el.rest.model.VidhanSabhaOfIndia;

@Repository
public interface BoothOfVidhansabhaImp extends CrudRepository<BoothOfVidhansabha, Long> {

	List<BoothOfVidhansabha> findAllByVidhanSAndActive(VidhanSabhaOfIndia vis, boolean active);
	boolean existsByBoothNumber(int bn);
	boolean existsByBoothNumberAndVidhanS(int bn, VidhanSabhaOfIndia vsi);
	
	BoothOfVidhansabha findByBoothNumberAndVidhanS(int bn, VidhanSabhaOfIndia vsi);
	
}
