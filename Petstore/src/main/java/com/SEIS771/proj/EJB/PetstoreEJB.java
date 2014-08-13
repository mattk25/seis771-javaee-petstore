package com.SEIS771.proj.EJB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import com.SEIS771.proj.exception.ValueNotFoundException;
import com.SEIS771.proj.model.Address;
import com.SEIS771.proj.model.AdoptionRequest;
import com.SEIS771.proj.model.Animal;
import com.SEIS771.proj.model.Breed;
import com.SEIS771.proj.model.CareRecord;
import com.SEIS771.proj.model.Contract;
import com.SEIS771.proj.model.Facility;
import com.SEIS771.proj.model.MedicalRecord;
import com.SEIS771.proj.model.Person;
import com.SEIS771.proj.model.Species;
import com.SEIS771.proj.model.Submission;
import com.SEIS771.proj.model.UserGroup;
import com.SEIS771.proj.model.Vaccinated;
import com.SEIS771.proj.model.Vaccine;

@Stateless
@EJB(beanInterface = PetstoreEJB.class, beanName = "pejb", name = "pejb")
public class PetstoreEJB {

	@PersistenceContext(unitName = "petstore")
	private EntityManager em;

	public Animal createAnimal(Animal animal) {
		Breed b = getBreedByName(animal.getBreed().getName());
		Species s;

		if (animal.getMedicalRecord() == null) {
			throw new ValueNotFoundException("Medical Record Required.");
		}
		if (animal.getSpecie() != null) {
			s = getSpeciesByName(animal.getSpecie().getName());
		} else {
			s = null;
		}

		if (b != null) {
			animal.setBreed(b);
		}
		em.persist(s);
		if (s != null) {
			animal.setSpecie(s);
		}
		List<Vaccinated> vac = animal.getMedicalRecord().getVaccines();
		em.persist(animal);

		MedicalRecord mr = animal.getMedicalRecord();
		List<Vaccinated> newVac = new ArrayList<Vaccinated>();
		for (Vaccinated vacc : vac) {
			Vaccine v1 = em.find(Vaccine.class, vacc.getVaccine().getId());
			vacc.setVaccine(v1);
			vacc.setMedicalRecord(mr);
			newVac.add(vacc);
		}

		mr.setVaccines(newVac);

		return animal;
	}
	public Submission assignFacilityToSubmission(Submission sub) {
		Submission s = em.find(Submission.class, sub.getId());

		if (s == null && s.getAnimal() == null) {
			throw new ValueNotFoundException("Submission doesn't exist");
		}

		if (!sub.getAnimal().getIsAvailable()) {
			
			sub.getAnimal().setIsAvailable(true);
			Submission v = em.merge(sub);
			
			Animal a = em.find(Animal.class, v.getAnimal().getId());
			a.setIsAvailable(true);
			em.merge(a);
			
			Facility f = em.find(Facility.class, sub.getFacility().getId());
			System.out.println("Logging Facility " + f.getCageCount() + 1);
			f.setCageCount(f.getCageCount() + 1);
			em.merge(f);
		
			return v;

		} else {
			throw new ValueNotFoundException("Animal already assigned to facility");
		}

	}
	public Submission updateSubmission(Submission sub) {
		Submission s = em.find(Submission.class, sub.getId());

		if (s == null && s.getAnimal() == null) {
			throw new ValueNotFoundException("Submission doesn't exist");
		}	
			return em.merge(sub);
	}

	public Animal updateAnimal(Animal na) {
		Animal a = em.find(Animal.class, na.getId());
		if (na.getBreed() != null) {
			if (!a.getBreed().getName().equals(na.getBreed().getName())) {
				List result = em.createNamedQuery("getBreedByName")
						.setParameter("name", na.getBreed().getName())
						.getResultList();
				Breed b;
				if (result.size() == 1) {
					b = (Breed) result.get(0);
				} else {
					throw new ValueNotFoundException("Breed doesn't exist");
				}

				a.setBreed(b);
			}
		}

		if (na.getSpecie() != null) {
			if (na.getSpecie() != null) {
				if (!a.getSpecie().getName().equals(na.getSpecie().getName())) {
					List result = em.createNamedQuery("getSpeciesByName")
							.setParameter("name", na.getSpecie().getName())
							.getResultList();
					Species s;
					if (result.size() == 1) {
						s = (Species) result.get(0);
					} else {
						throw new ValueNotFoundException(
								"Species doesn't exist");
					}

					a.setSpecie(s);
				}
			}
		}

		if (na.getAge() != null) {
			a.setAge(na.getAge());
		}

		if (na.getColor() != null) {
			a.setColor(na.getColor());
		}

		if (na.getImage() != null) {
			a.setImage(na.getImage());
		}

		if (na.getName() != null) {
			a.setName(na.getName());
		}

		if (na.getSex() != null) {
			a.setSex(na.getSex());
		}

		if (na.getTemperment() != null) {
			a.setTemperment(na.getTemperment());
		}

		if (na.getRecords() != null) {
			Animal a1 = (Animal) em.find(Animal.class, na.getId());
			List<CareRecord> cr = a1.getRecords();
			a.setRecords(cr);
		}
		return a;

	}

	public List<Animal> getAnimals() {
		return em.createNamedQuery("getAllAnimals").getResultList();
	}

	private void createCockatielData() {
		Species s1 = new

		Species();
		s1.setName("Cockatiel");

		Vaccine v1 = new Vaccine();
		v1.setName("Cockatiel-vaccine1");

		Vaccine v2 = new Vaccine();
		v2.setName("Cockatiel-vaccine2");
		em.persist(v1);
		v1.setSpecie(s1);
		em.persist(v2);
		v2.setSpecie(s1);

		List<Vaccine> lv = new ArrayList<Vaccine>();
		lv.add(v1);
		lv.add(v2);

		s1.setVaccine(lv);
		em.persist(s1);

		Breed b1 = new Breed();
		b1.setName("Whiteface lutino");
		b1.setSpecie(s1);
		Breed b2 = new Breed();
		b2.setName("Bronzefallow");
		b2.setSpecie(s1);
		Breed b3 = new Breed();
		b3.setName("Ashenfallow");
		b3.setSpecie(s1);
		Breed b4 = new Breed();
		b4.setSpecie(s1);
		b4.setName("ADMpied");

		em.persist(b1);
		em.persist(b2);
		em.persist(b3);
		em.persist(b4);

		Animal an8 = new Animal();
		an8.setBreed(b2);
		an8.setName("Mimi");
		an8.setColor("White");
		an8.setImage("008.jpg");

		an8.setSpecie(s1);
		an8.setSex("F");

		em.persist(an8);

	}

	public void createDefaultData() {

		Address a1 = new Address();

		a1.setStreet("11719 Millpond Ave");
		a1.setCity("Burnsville");
		a1.setState("MN");
		a1.setZipCode("55337");

		Person p1 = new Person();

		p1.setFirstName("George");
		p1.setLastName("Jetson");
		p1.setEmail("George.Jetson@gmail.com");
		p1.setPhoneNumber("612-555-1234");
		p1.setAddress(a1);
		p1.setPassword("admin");
		p1.setUserName("admin");

		UserGroup g1 = new UserGroup();
		g1.setName("admin");
		g1.setDescription("Admin Group");

		List<Person> members = new ArrayList<Person>();
		members.add(p1);
		g1.setMembers(members);
		p1.setGroup(g1);

		a1.setPerson(p1);
		em.persist(a1);
		em.persist(p1);

		Address a2 = new Address();

		a2.setStreet("5666 Winnetka Ave N");
		a2.setCity("New Hope");
		a2.setState("MN");
		a2.setZipCode("55427");
		Person normalUser = new Person();
		normalUser.setFirstName("User1 FirstName");
		normalUser.setLastName("User1 LastName");
		normalUser.setEmail("test@test.com");
		normalUser.setPhoneNumber("344-343-3434");
		normalUser.setAddress(a2);
		normalUser.setUserName("user1");
		normalUser.setPassword("user1");

		Address a3 = new Address();

		a3.setStreet("200 Nathan LN N");
		a3.setCity("Plymoth");
		a3.setState("MN");
		a3.setZipCode("55477");
		Person normalUser2 = new Person();
		normalUser2.setFirstName("User2 FirstName");
		normalUser2.setLastName("USer2 LastName");
		normalUser2.setEmail("user2@test.com");
		normalUser2.setPhoneNumber("952-457-4473");
		normalUser2.setAddress(a3);
		normalUser2.setUserName("user2");
		normalUser2.setPassword("user2");
		em.persist(normalUser);
		em.persist(normalUser2);

		UserGroup g2 = new UserGroup("user", "User Group");
		g2.addMember(normalUser);
		g2.addMember(normalUser2);
		em.persist(g2);

		normalUser.setGroup(g2);
		normalUser2.setGroup(g2);

		/*
		 * Breed b1 = new Breed(); b1.setName("Labrador Retriever");
		 * em.persist(b1);
		 */
		Species s1 = new Species();
		s1.setName("Dog");

		Vaccine v1 = new Vaccine();
		v1.setName("Parvo");

		Vaccine v2 = new Vaccine();
		v2.setName("Rabbis");
		em.persist(v1);
		v1.setSpecie(s1);
		em.persist(v2);
		v2.setSpecie(s1);

		List<Vaccine> lv = new ArrayList<Vaccine>();
		lv.add(v1);
		lv.add(v2);

		s1.setVaccine(lv);
		em.persist(s1);

		Breed b1 = new Breed();
		b1.setName("Corgi"); // Firnando
		b1.setSpecie(s1);
		Breed b2 = new Breed();
		b2.setName("Beagle"); // Scoopy
		b2.setSpecie(s1);
		Breed b3 = new Breed();
		b3.setName("Whipet"); // Mimi
		b3.setSpecie(s1);
		Breed b4 = new Breed();
		b4.setSpecie(s1);
		b4.setName("Blood Hound"); // Rusty
		Breed b5 = new Breed();
		b5.setName("Golden Retriever"); // Zako
		b5.setSpecie(s1);
		Breed b6 = new Breed();
		b6.setName("Poodle"); // Barney
		b6.setSpecie(s1);
		Breed b7 = new Breed();
		b7.setName("Chihuahua"); // Smelly
		b7.setSpecie(s1);

		em.persist(b1);
		em.persist(b2);
		em.persist(b3);
		em.persist(b4);
		em.persist(b5);
		em.persist(b6);
		em.persist(b7);

		Animal an1 = new Animal();
		an1.setBreed(b1);
		an1.setName("Firnando");
		an1.setColor("tri-color");
		an1.setSpecie(s1);
		an1.setSex("M");
		an1.setImage("001.jpg");
		an1.setIsAvailable(true);

		Animal an2 = new Animal();
		an2.setBreed(b2);
		an2.setName("Scoopy");
		an2.setColor("brown/white");
		an2.setImage("002.jpg");
		an2.setSpecie(s1);
		an2.setSex("F");
		an2.setIsAvailable(true);

		Animal an3 = new Animal();
		an3.setBreed(b3);
		an3.setName("Mimi");
		an3.setColor("gray");
		an3.setSpecie(s1);
		an3.setSex("F");

		Animal an4 = new Animal();
		an4.setBreed(b4);
		an4.setName("Rusty");
		an4.setColor("Red");
		an4.setImage("004.jpg");
		an4.setAge("2 months");
		an4.setSpecie(s1);
		an4.setSex("M");

		Animal an5 = new Animal();
		an5.setBreed(b5);
		an5.setName("Zako");
		an5.setColor("Light Brown");
		an5.setImage("005.jpg");
		an5.setSpecie(s1);
		an5.setSex("F");

		Animal an6 = new Animal();
		an6.setBreed(b6);
		an6.setName("Barney");
		an6.setColor("black");
		an6.setSpecie(s1);
		an6.setSex("M");
		an6.setImage("006.jpg");

		Animal an7 = new Animal();
		an7.setBreed(b7);
		an7.setName("Smelly");
		an7.setColor("Tan");
		an7.setImage("007.jpg");
		an7.setSpecie(s1);
		an7.setSex("M");
		
		Animal an8 = new Animal();
		an8.setBreed(b5);
		an8.setName("Hunter");
		an8.setColor("Tan");
		an8.setImage("Hunter.jpg");
		an8.setSpecie(s1);
		an8.setSex("M");
		an8.setIsAvailable(true);
		
		Animal an9 = new Animal();
		an9.setBreed(b5);
		an9.setName("BirdDog");
		an9.setColor("Tan");
		an9.setImage("birdDog.jpg");
		an9.setSpecie(s1);
		an9.setSex("M");
		an9.setIsAvailable(true);
		
		Animal an10 = new Animal();
		an10.setBreed(b5);
		an10.setName("Goldy");
		an10.setColor("Tan");
		an10.setImage("goldy.jpg");
		an10.setSpecie(s1);
		an10.setSex("F");
		an10.setIsAvailable(true);
		
		MedicalRecord m1 = new MedicalRecord();
		m1.setIsneutered(true);
		m1.setLastVisit(new Date());
		m1.setAnimalCondition("good");
		m1.setVetName("john");

		em.persist(m1);

		an1.setMedicalRecord(m1);
		m1.setAnimal(an1);

		Vaccinated va1 = new Vaccinated();
		va1.setIsVaccinated(true);
		va1.setMedicalRecord(m1);
		va1.setVaccinationDate(new Date());
		va1.setVaccine(v1);
		em.persist(va1);

		Vaccinated va2 = new Vaccinated();
		va2.setIsVaccinated(false);
		va2.setMedicalRecord(m1);
		va2.setVaccinationDate(new Date());
		va2.setVaccine(v2);
		em.persist(va2);

		em.persist(an2);
		em.persist(an5);
		em.persist(an6);
		em.persist(an7);

		// submissions
		Submission sub = new Submission();
		sub.setAnimal(an1);
		sub.setSubmitDate(new Date());
		sub.setSubmissionConstraint("this animal sucks");
		sub.setPerson(normalUser);
		sub.setStatus("Submitted");
		an1.setSubmission(sub);
		em.persist(an1);
		em.persist(sub);

		sub = new Submission();
		sub.setAnimal(an3);
		sub.setSubmitDate(new Date());
		sub.setSubmissionConstraint("The animal has a limp");
		sub.setPerson(normalUser2);
		sub.setStatus("Submitted");
		an3.setSubmission(sub);
		em.persist(an3);
		em.persist(sub);

		sub = new Submission();
		sub.setAnimal(an4);
		sub.setSubmitDate(new Date());
		sub.setSubmissionConstraint("doesn't like cats");
		sub.setPerson(normalUser);
		sub.setStatus("Submitted");
		an4.setSubmission(sub);
		em.persist(an4);
		em.persist(sub);

		// end submissions

		List<CareRecord> lcr = new ArrayList();

		CareRecord cr = new CareRecord();
		cr.setAnimal(an2);
		cr.setComment("seemed in good spirits");
		cr.setGaveFood(true);
		cr.setGaveWater(true);
		cr.setRecordTimeStamp(new Date());
		lcr.add(cr);
		an2.setRecords(lcr);
		em.persist(cr);
		// Contract c = new Contract();
		// c.setSubmissionID(sub.getId());
		// c.fullContractText(sub);
		// c.setDropOffDate(new Date(14-01-22));
		// em.persist(c);

		createCockatielData();

		AdoptionRequest ar = new AdoptionRequest();
		ar.setAnimalAge("young");
		ar.setBreed("poodle");
		ar.setColor("black");
		ar.setIsNeutered(true);
		ar.setSpecies("dog");
		ar.setRequester(p1);
		ar.setStatus("submitted");
		ar.setSubmitDate(new Date());
		ar.setHaveChildren(true);
		em.persist(ar);

		AdoptionRequest ar2 = new AdoptionRequest();
		ar2.setAnimalAge("medium");
		ar2.setBreed("schnauzer");
		ar2.setColor("gray");
		ar2.setIsNeutered(true);
		ar2.setSpecies("dog");
		ar2.setRequester(normalUser);
		ar2.setStatus("submitted");
		ar2.setSubmitDate(new Date());
		ar2.setHaveChildren(false);
		ar2.setRequestedAnimal(an1);
		em.persist(ar2);

		AdoptionRequest ar3 = new AdoptionRequest();
		ar3.setRequestedAnimal(an4);
		ar3.setRequester(normalUser2);
		ar.setHaveChildren(true);
		ar3.setStatus("submitted");
		ar3.setSubmitDate(new Date());
		em.persist(ar3);

		Facility f1 = new Facility();
		f1.setAddress(a1);
		f1.setFacilityName("The Dog Pound");
		f1.setCageCount(0);
		f1.setMaxCages(100);

		Facility f2 = new Facility();
		f2.setAddress(a1);
		f2.setFacilityName("Dog HEAVEN");
		f2.setCageCount(0);
		f2.setMaxCages(3);

		em.persist(f1);
		em.persist(f2);
	}

	public boolean authenticate(String username, String password) {

		Person p = getPersonByUsername(username);
		if (p == null) {
			return false;
		}

		if (p.getPassword().equals(password)) {
			return true;
		} else
			return false;
	}

	public void createBreed(Breed breed) {
		em.persist(breed);
	}

	public List<Breed> getBreeds() {
		return em.createNamedQuery("getAllBreeds").getResultList();
	}

	public UserGroup getUserGroupById(int id) {
		return (UserGroup) em.createNamedQuery("getUserGroupById")
				.setParameter("id", id).getResultList().get(0);
	}

	public Animal getAnimalById(int id) {
		Animal a = (Animal) em.find(Animal.class, id);
		if (a != null) {
			return a;
		} else {
			throw new ValueNotFoundException("Animal does not exist");
		}
	}

	public UserGroup getUserGroupByName(String name) {
		// TODO Auto-generated method stub
		return (UserGroup) em.createNamedQuery("getUserGroupByName")
				.setParameter("name", name).getResultList().get(0);
	}

	public Person createPerson(Person person) {

		UserGroup ug = getUserGroupByName(person.getGroup().getName());
		person.setGroup(ug);

		em.persist(person);
		return person;
	}

	public Person getPersonByUsername(String username) {
		List results = em.createNamedQuery("getPersonByUsername")
				.setParameter("username", username).getResultList();
		if (results.size() == 1) {
			return (Person) results.get(0);
		} else {
			return null;
		}
	}

	public List<Breed> getPersons() {
		return em.createNamedQuery("getAllPersons").getResultList();
	}

	public void createAddress(Address address) {
		em.persist(address);
	}

	public List<Breed> getAddresses() {
		return em.createNamedQuery("getAllAddresses").getResultList();
	}

	public void createSpecies(Species species) {
		em.persist(species);
	}

	public List<Species> getSpecies() {
		return em.createNamedQuery("getAllSpiciess").getResultList();
	}

	public void createMedicalRecord(MedicalRecord medicalRecord) {
		em.persist(medicalRecord);
	}

	public List<Animal> getAvailableAnimalsForAdoption(int noOfRecords, int page) {
		Query query = em
				.createQuery("SELECT a FROM Animal a WHERE a.submission != NULL ORDER BY a.id ASC");
		query.setMaxResults(noOfRecords);
		query.setFirstResult(page * noOfRecords);
		List<Animal> animal = query.getResultList();
		return animal;
	}
	public List<Animal> getAdoptableAnimals() {
		Query query = em
				.createQuery("SELECT a FROM Animal a WHERE a.getSubmission().getStatus() == 'available' ORDER BY a.id ASC");
		List<Animal> animal = query.getResultList();
		return animal;
	}
	public Breed getBreedByName(String name) {
		List results = em.createNamedQuery("getBreedByName")
				.setParameter("name", name).getResultList();
		if (results.size() == 1) {
			return (Breed) results.get(0);
		} else {
			return null;
		}
	}

	public Species getSpeciesByName(String name) {
		List results = em.createNamedQuery("getSpeciesByName")
				.setParameter("name", name).getResultList();
		if (results.size() == 1) {
			return (Species) results.get(0);
		} else {
			return null;
		}
	}

	public List<Vaccine> getVaccinesForSpecie(String name) {
		// TODO Auto-generated method stub
		if (name == null) {
			throw new ValueNotFoundException("Specie name must be provided");
		} else {
			Species sp = (Species) em.createNamedQuery("getSpeciesByName")
					.setParameter("name", name).getResultList().get(0);

			List<Vaccine> list = (List<Vaccine>) em
					.createNamedQuery("getVaccinesForSpecie")
					.setParameter("id", sp.getId()).getResultList();

			return list;
		}
	}

	public List<Breed> getBreedsForSpecie(String name) {
		// TODO Auto-generated method stub
		if (name == null) {
			throw new ValueNotFoundException("Specie name must be provided");
		} else {
			Species sp = (Species) em.createNamedQuery("getSpeciesByName")
					.setParameter("name", name).getResultList().get(0);

			List<Breed> list = (List<Breed>) em
					.createNamedQuery("getBreedsForSpecie")
					.setParameter("id", sp.getId()).getResultList();

			return list;
		}
	}

	public Submission createSubmission(Submission submission) {
		// TODO Auto-generated method stub
		Animal a = createAnimal(submission.getAnimal());
		em.persist(a);
		Person p = em.find(Person.class, submission.getPerson().getId());
		submission.setPerson(p);
		submission.setAnimal(a);
		em.persist(submission);
		a.setSubmission(submission);

		return submission;
	}

	public List<Submission> getAvailableSubmissionsByStatus(String status,
			int offset, int page) {
		// TODO Auto-generated method stub
		Query query = em
				.createQuery("SELECT s FROM Submission s WHERE s.status = :status ORDER BY s.id");
		query.setMaxResults(offset);
		query.setParameter("status", status);
		query.setFirstResult(page * offset);
		List<Submission> subs = query.getResultList();
		return subs;
	}

	public List<Submission> getSubmissionsByUserId(int userId) {
		// TODO Auto-generated method stub
		Query query = em
				.createQuery("SELECT s FROM Submission s WHERE s.person.id ="
						+ String.valueOf(userId));
		List<Submission> subs = query.getResultList();
		return subs;
	}

	public List<Submission> getAllSubmission() {
		// TODO Auto-generated method stub
		List<Submission> list = (List<Submission>) em.createNamedQuery(
				"getAllSubmissions").getResultList();
		return list;
	}

	public Submission getSubmissionById(int id) {
		// TODO Auto-generated method stub
		Submission sub = em.find(Submission.class, id);
		if (sub == null) {
			throw new ValueNotFoundException("Submission with id: " + id
					+ " does not exist.");
		} else {
			return sub;
		}
	}

	public Submission rejectSubmissionbyId(int id) {
		// TODO Auto-generated method stub
		Submission sub = em.find(Submission.class, id);
		sub.setStatus("Rejected");
		em.persist(sub);

		if (sub == null) {
			throw new ValueNotFoundException("Submission with id: " + id
					+ " does not exist.");
		} else {
			return sub;
		}
	}

	public Submission approveSubmissionbyId(int id) {
		// TODO Auto-generated method stub
		Submission sub = em.find(Submission.class, id);
		sub.setStatus("Approved");
		em.persist(sub);

		if (sub == null) {
			throw new ValueNotFoundException("Submission with id: " + id
					+ " does not exist.");
		} else {
			return sub;
		}
	}

	/**
	 * @param contract
	 * @return
	 */
	public Contract createContract(Contract contract) {
		Submission s = getSubmissionById(contract.getSubmission().getId());
		contract.setSubmission(s);
		em.persist(contract);
		return contract;
	}

	/*
	 * UserGroup ug = getUserGroupByName(person.getGroup().getName());
	 * person.setGroup(ug);
	 * 
	 * em.persist(person); return person;
	 */

	// john 4-16-2014
	public List<CareRecord> createCareRecord(int id, CareRecord careRecord) {
		Animal a = em.find(Animal.class, id);

		careRecord.setAnimal(a);

		List<CareRecord> nl = a.getRecords();
		nl.add(careRecord);
		a.setRecords(nl);

		em.persist(careRecord);
		return a.getRecords();

		// Animal a = (Animal)em.find(Animal.class,
		// careRecord.getAnimal().getId());
		// careRecord.setAnimal(a);
		// em.persist(careRecord);
		// return careRecord;
	}

	// john 4-16-2014{
	public CareRecord getCareRecordById(int recordId) {
		List results = em.createNamedQuery("getCareRecordById")
				.setParameter("Id", recordId).getResultList();
		if (results.size() == 1) {
			return (CareRecord) results.get(0);
		} else {
			return null;
		}
	}

	public List<CareRecord> getCareRecordByAnimalID(int _animalID) {
		List results = em.createNamedQuery("getCareRecordByAnimalID")
				.setParameter("animalid", _animalID).getResultList();
		if (results.size() >= 1) {
			return results;
		} else {
			return null;
		}
	}

	public List<CareRecord> getCareRecordsForAnimalById(int animalID) {
		// TODO Auto-generated method stub
		Animal a = em.find(Animal.class, animalID);
		//
		// if(a == null) {
		// throw new ValueNotFoundException("Animal does not exist.");
		// }
		return a.getRecords();
	}

	public List<Contract> getAllContracts() {
		Query query = em.createQuery("SELECT c FROM Contract c");
		return query.getResultList();
	}

	public AdoptionRequest createAdoptionRequest(AdoptionRequest adoptionRequest) {

		Person p = em
				.find(Person.class, adoptionRequest.getRequester().getId());
		adoptionRequest.setRequester(p);

		Animal tmp = adoptionRequest.getRequestedAnimal();
		if (tmp != null) {
			Integer animalId = tmp.getId();

			if (animalId != null) {
				Animal a = em.find(Animal.class, adoptionRequest
						.getRequestedAnimal().getId());
				adoptionRequest.setRequestedAnimal(a);
			}
		}

		em.persist(adoptionRequest);

		return adoptionRequest;

	}

	public AdoptionRequest getAdoptionRequestById(int id) {
		AdoptionRequest ar = em.find(AdoptionRequest.class, id);
		if (ar != null) {
			return ar;
		} else {
			throw new ValueNotFoundException("AdoptionRequest does not exist.");
		}
	}

	public List<AdoptionRequest> getAllAdoptionRequests() {
		Query query = em.createQuery("SELECT ar FROM AdoptionRequest ar");
		return query.getResultList();
	}

	/**
	 * @param updateAdoptionRequest
	 * @return
	 */
	public AdoptionRequest updateAdoptionRequest(AdoptionRequest adoptReq) {
		AdoptionRequest ar = em.find(AdoptionRequest.class, adoptReq.getId());
		if (adoptReq == null && adoptReq.getRequestedAnimal() == null) {
			throw new ValueNotFoundException("AdoptionRequest doesn't exist");
		}
		return em.merge(adoptReq);
	}

	public List<AdoptionRequest> getUserAdoptionRequests(int userId) {
		Query query = em
				.createQuery("SELECT a FROM AdoptionRequest a WHERE a.requester.id ="
						+ String.valueOf(userId));
		List<AdoptionRequest> ars = query.getResultList();
		return ars;
	}

	public boolean createdRequestForAnimal(Person requester,
			Animal requestedAnimal) {
		boolean result = false;
		Query query = em
				.createQuery("SELECT a FROM AdoptionRequest a WHERE a.requester.id ="
						+ requester.getId()
						+ " AND a.requestedAnimal.id="
						+ requestedAnimal.getId());
		result = !query.getResultList().isEmpty();
		return result;
	}

	public List<Facility> getFacilities() {
		Query query = em.createQuery("SELECT ar FROM Facility ar");
		return (List<Facility>) query.getResultList();
	}

}
