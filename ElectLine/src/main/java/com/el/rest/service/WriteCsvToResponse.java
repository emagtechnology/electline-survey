package com.el.rest.service;

import java.io.PrintWriter;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

public class WriteCsvToResponse {

	public static void writeSurvey(PrintWriter writer, List<SurvData> srvMain)  {

        try {

            ColumnPositionMappingStrategy<SurvData> mapStrategy
                    = new ColumnPositionMappingStrategy<>();

           mapStrategy.setType(SurvData.class);
          
           mapStrategy.generateHeader(new SurvData());

           String[] columns = new String[]{"surveyorName","surveyorEmail","boothName","surveeName",
        		   				"surveeContact", "surveeAge", "surveeAddress", "surveeEmployed", "surveeVoterIdAvl",
        		   				"surveeFamily", "surveeRelative", "partyLeading", "bestCandidateArea", "castEquation",
        		   				"totalVoter","issues", "suggestions", "partyWish","partyWiseCandidate", "bestCmCandidate",
        		   				"proposedCm", "bestPm", "influenceVoter", "rebelParty", "bestMedia", "freeComments","dynQues"};
            mapStrategy.setColumnMapping(columns);

            StatefulBeanToCsv btcsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mapStrategy)
                    
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withEscapechar(CSVWriter.NO_ESCAPE_CHARACTER)
                    .build();

            btcsv.write(srvMain);

        } catch (CsvException ex) {

        	System.err.println(ex);
            //LOGGER.error("Error mapping Bean to CSV", ex);
        }
    }
	
}
