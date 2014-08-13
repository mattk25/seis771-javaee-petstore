package com.SEIS771.rest;


import org.junit.Test;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.JsonMappingException;

import com.SEIS771.proj.model.Address;
import com.SEIS771.proj.model.Adoption;
import com.SEIS771.proj.model.Breed;
import com.SEIS771.proj.model.Contract;
import com.SEIS771.proj.model.Animal;
import com.SEIS771.proj.model.MedicalRecord;
import com.SEIS771.proj.model.Person;
import com.SEIS771.proj.model.Species;
import com.SEIS771.proj.model.Staff;
import com.SEIS771.proj.model.Submission;
import com.SEIS771.proj.model.UserGroup;
import com.SEIS771.proj.model.Vaccinated;
import com.SEIS771.proj.model.Vaccine;

public class JsonStringBuilder {
//	@Test
//	public void testAssess() {
//
//		org.junit.Assert.assertTrue(true);
//	}
	
	@Test
	public void TestAddress()  {
		Address adr = new Address();
	ObjectMapper toJsonMapper = new ObjectMapper();
		try {
			// convert user object to json string, and save to a file
			toJsonMapper.writeValue(new File("c:\\seis771\\Address.json"), adr);  
			// display to console
			System.out.println("Address "+ toJsonMapper.writeValueAsString(adr));
			} catch (JsonGenerationException e) {
			e.printStackTrace(); 
			} catch (JsonMappingException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	@Test
	public void TestAdoption()  {
		Adoption adp = new Adoption();
	ObjectMapper toJsonMapper = new ObjectMapper();
	try {
		toJsonMapper.writeValue(new File("c:\\seis771\\Adoption.json"), adp);  
		System.out.println("Adoption "+ toJsonMapper.writeValueAsString(adp));
		} catch (JsonGenerationException e) {
		e.printStackTrace(); 
		} catch (JsonMappingException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	@Test
	public void TestAnimal()  {
	Animal an = new Animal();
	ObjectMapper toJsonMapper = new ObjectMapper();
	try {
		toJsonMapper.writeValue(new File("c:\\seis771\\animal.json"), an);  
		System.out.println("Animal "+ toJsonMapper.writeValueAsString(an));
		} catch (JsonGenerationException e) {
		e.printStackTrace(); 
		} catch (JsonMappingException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	@Test
	public void TestBreed()  {
	Breed br = new Breed();
	ObjectMapper toJsonMapper = new ObjectMapper();
	try {
		toJsonMapper.writeValue(new File("c:\\seis771\\breed.json"), br);  
		System.out.println("Breed "+ toJsonMapper.writeValueAsString(br));
		} catch (JsonGenerationException e) {
		e.printStackTrace(); 
		} catch (JsonMappingException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	@Test
	public void TestContract()  {
		Contract c = new Contract();
		ObjectMapper toJsonMapper = new ObjectMapper();
		try {
			toJsonMapper.writeValue(new File("c:\\seis771\\contract.json"), c);  
			System.out.println("Contract "+ toJsonMapper.writeValueAsString(c));
			} catch (JsonGenerationException e) {
			e.printStackTrace(); 
			} catch (JsonMappingException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	@Test
	public void TestMedicalRecord()  {
		MedicalRecord mr = new MedicalRecord();
		ObjectMapper toJsonMapper = new ObjectMapper();
		try {
			toJsonMapper.writeValue(new File("c:\\seis771\\MedicalRecord.json"), mr);  
			System.out.println("MedicalRecord "+ toJsonMapper.writeValueAsString(mr));
			} catch (JsonGenerationException e) {
			e.printStackTrace(); 
			} catch (JsonMappingException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	@Test
	public void TestPerson()  {
		Person p = new Person();
		ObjectMapper toJsonMapper = new ObjectMapper();
		try {
			toJsonMapper.writeValue(new File("c:\\seis771\\Person.json"), p);  
			System.out.println("Person "+ toJsonMapper.writeValueAsString(p));
			} catch (JsonGenerationException e) {
			e.printStackTrace(); 
			} catch (JsonMappingException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	@Test
	public void TestSpecies()  {
		Species s = new Species();
		ObjectMapper toJsonMapper = new ObjectMapper();
		try {
			toJsonMapper.writeValue(new File("c:\\seis771\\Species.json"), s);  
			System.out.println("Species "+ toJsonMapper.writeValueAsString(s));
			} catch (JsonGenerationException e) {
			e.printStackTrace(); 
			} catch (JsonMappingException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	@Test
	public void TestStaff()  {
		Staff st = new Staff();
		ObjectMapper toJsonMapper = new ObjectMapper();
		try {
			toJsonMapper.writeValue(new File("c:\\seis771\\Staff.json"), st);  
			System.out.println("Staff "+ toJsonMapper.writeValueAsString(st));
			} catch (JsonGenerationException e) {
			e.printStackTrace(); 
			} catch (JsonMappingException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	@Test
	public void TestSubmission()  {
		Submission sub = new Submission();
		ObjectMapper toJsonMapper = new ObjectMapper();
		try {
			toJsonMapper.writeValue(new File("c:\\seis771\\Submission.json"), sub);  
			System.out.println("Submission "+ toJsonMapper.writeValueAsString(sub));
			} catch (JsonGenerationException e) {
			e.printStackTrace(); 
			} catch (JsonMappingException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	@Test
	public void TestUserGroup()  {
		UserGroup ug = new UserGroup();
		ObjectMapper toJsonMapper = new ObjectMapper();
		try {
			toJsonMapper.writeValue(new File("c:\\seis771\\UserGroup.json"), ug);  
			System.out.println("UserGroup "+ toJsonMapper.writeValueAsString(ug));
			} catch (JsonGenerationException e) {
			e.printStackTrace(); 
			} catch (JsonMappingException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	@Test
	public void TestVaccinated()  {
		Vaccinated v1 = new Vaccinated();
		ObjectMapper toJsonMapper = new ObjectMapper();
		try {
			toJsonMapper.writeValue(new File("c:\\seis771\\Vaccinated.json"), v1);  
			System.out.println("Vaccinated "+ toJsonMapper.writeValueAsString(v1));
			} catch (JsonGenerationException e) {
			e.printStackTrace(); 
			} catch (JsonMappingException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	@Test
	public void TestVaccine()  {
		Vaccine v = new Vaccine();
		ObjectMapper toJsonMapper = new ObjectMapper();
		try {
			toJsonMapper.writeValue(new File("c:\\seis771\\Vaccine.json"), v);  
			System.out.println("Vaccine "+ toJsonMapper.writeValueAsString(v));
			} catch (JsonGenerationException e) {
			e.printStackTrace(); 
			} catch (JsonMappingException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
}
