package com.guzman.projects.teamgenerator.teamgen.dao;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.guzman.projects.teamgenerator.teamgen.Member;
import com.guzman.projects.teamgenerator.teamgen.TeamGenerator;

/**
 * 
 * @author Jonathan Guzman
 */
public class TeamGeneratorCsvDao implements IDataLoader {

	private String filePath;
	private String headerRow;

	private List<Member> members;
	
	private static final Logger logger = Logger.getLogger(TeamGeneratorCsvDao.class.getName());

	public TeamGeneratorCsvDao(String csvFileName) {
		filePath = csvFileName;
	}

	/**
	 * Helper method to read csv
	 * 
	 * @throws Exception
	 */
	private void readCsvFile() throws Exception {
		members = new ArrayList<Member>();

		try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {

			String name = null;

			headerRow = in.readLine() + "\n";
			while ((name = in.readLine()) != null) {

				String[] temp = name.split(",");
				if (name.contains(",")) {
					members.add(new Member(
							Integer.parseInt(temp[0]),
							temp[1],
							temp[2],
							Integer.parseInt(temp[3])));
				} 
				else {
					logger.error("invalid entry");
				}
			}
		}
	}

	@Override
	public List<Member> getUsers() {
		try {
			readCsvFile();
		} catch (Exception e) {
			logger.error("error when returning users\n" + e.getStackTrace(), e);
		}
		return members;
	}

	@Override
	public void addMember(Member m) throws Exception {
		members.add(m);
		
		save();
	}

	@Override
	public void deleteMember(Member m) throws Exception {

		for (Member mem : members) {
			if (mem.getId() == m.getId()) {
				members.remove(mem);
				break;
			}
		}
		
		save();
	}

	@Override
	public void updateMember(Member m) throws Exception {
		
		for(Member member : members) {
			if(member.getId() == m.getId())
				member = m;
		}
		
		save();
	}

	private void save() throws Exception {
		new File("./target/csv").mkdir();
		String outFW = "./target/csv" + filePath.substring(filePath.lastIndexOf('/'));

		try (BufferedWriter out = new BufferedWriter(new FileWriter(outFW))) {
			out.write(headerRow);
			for (Member m : members) {
				out.write(m.getId() + "," + m.getFirstName() + "," +
							m.getLastName() + "," + m.getAge() + "\n");
			}
		}
	}
}
