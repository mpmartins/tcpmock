package com.inovarie.tcpmock.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.inovarie.tcpmock.model.Record;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Component;

@Component
public class RecordFileManager {

	private static final String FILE_EXTENSION = ".json";

	public Record loadRecord(String filename) {
		Gson gson = new GsonBuilder().create();
		Record record = null;

		try (JsonReader reader = new JsonReader(new FileReader(filename + FILE_EXTENSION))) {
			record = gson.fromJson(reader, Record.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return record;
	}
	
	public void saveRecord(Record record) {
	    Gson gson = new GsonBuilder().create();
		
	    try (Writer writer = new FileWriter(record.getName() + FILE_EXTENSION)) {
		    gson.toJson(record, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
