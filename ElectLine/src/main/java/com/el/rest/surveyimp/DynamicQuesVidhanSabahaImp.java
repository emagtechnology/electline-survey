package com.el.rest.surveyimp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.el.rest.model.VidhanSabhaOfIndia;
import com.el.rest.survey.DynamicQuesVidhanSabaha;

@Repository
public interface DynamicQuesVidhanSabahaImp extends CrudRepository<DynamicQuesVidhanSabaha, Integer> {

	List<DynamicQuesVidhanSabaha> findAllByVidhanSAndActive(VidhanSabhaOfIndia vs, Boolean act);
}
