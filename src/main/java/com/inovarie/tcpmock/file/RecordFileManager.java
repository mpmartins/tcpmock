package com.inovarie.tcpmock.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import com.inovarie.tcpmock.model.Record;

public class RecordFileManager {

	private static final String FILE_EXTENSION = ".record";

	public static Record loadRecord(String name) {
		Record record = null;
		try {
			URL file = Thread.currentThread().getContextClassLoader().getResource(name + FILE_EXTENSION);
			FileInputStream fin = new FileInputStream(new File(file.getFile()));
			ObjectInputStream ois = new ObjectInputStream(fin);
			record = (Record) ois.readObject();
			ois.close();
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return record;
	}
	
	public static void saveRecord(Record record) {
		try {
			FileOutputStream fout = new FileOutputStream(record.getName() + FILE_EXTENSION);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(record);
			oos.close();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}
