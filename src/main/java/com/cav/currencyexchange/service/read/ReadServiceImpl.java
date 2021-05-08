package com.cav.currencyexchange.service.read;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.cav.currencyexchange.models.CurrencyOrderRead;

@Service
public class ReadServiceImpl implements ReadService {

	@Override
	public List <CurrencyOrderRead>  readData(String path) {
		List <CurrencyOrderRead> datas = new ArrayList<CurrencyOrderRead>();
		File data = new File(path);
		Scanner scnr = null;
		try {
			scnr = new Scanner(data);
			 while(scnr.hasNextLine()){
				 datas.add(getCurrencyOrder(scnr.nextLine()));
			 }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(scnr != null) {
				scnr.close();
			}
		}
		return datas;
	}
	
	private CurrencyOrderRead getCurrencyOrder(String line) {
		String[] fields = line.split(",");
		
		return new CurrencyOrderRead(getField(fields[0]), 
				getField(fields[1]), 
				getField(fields[2]), 
				getField(fields[3]), 
				getField(fields[3]), 
				getField(fields[3]), 
				getField(fields[3]), 
				getField(fields[3]), 
				getField(fields[3]));
	}
	
	private String getField(String line) {
		String[] fields = line.split("=");
		return fields[1];
	}

}
