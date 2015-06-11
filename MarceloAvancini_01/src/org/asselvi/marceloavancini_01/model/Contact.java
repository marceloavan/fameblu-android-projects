package org.asselvi.marceloavancini_01.model;

import java.io.Serializable;

/**
 * 
 * @author marcelo
 *
 */
public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer idDevice;
	private String name;
	private String document;
	private Integer phoneHome;
	private Integer phoneMobile;
	private String email;
	private Character sex;
	
	public Contact() {
		sex = 'I';
	}
	
	public Contact(Integer id, String name, String document, Integer phoneHome,	Integer phoneMobile, String email, Character sex) {
		super();
		this.id = id;
		this.name = name;
		this.document = document;
		this.phoneHome = phoneHome;
		this.phoneMobile = phoneMobile;
		this.email = email;
		this.sex = sex;
	}



	public Integer getId() {
		return id != null ? id : 0;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdDevice() {
		return idDevice != null ? idDevice : 0;
	}
	
	public void setIdDevice(Integer idDevice) {
		this.idDevice = idDevice;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDocument() {
		return document;
	}
	
	public void setDocument(String document) {
		this.document = document;
	}

	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
		this.sex = sex;
	}

	public Integer getPhoneHome() {
		return phoneHome != null ? phoneHome : 0;
	}

	public void setPhoneHome(Integer phoneHome) {
		this.phoneHome = phoneHome;
	}

	public Integer getPhoneMobile() {
		return phoneMobile != null ? phoneMobile : 0;
	}

	public void setPhoneMobile(Integer phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	public String getEmail() {
		return email != null ? email : "N/A";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result	+ ((idDevice == null) ? 0 : idDevice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Contact other = (Contact) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		if (idDevice == null) {
			if (other.idDevice != null) {
				return false;
			}
		} else if (!idDevice.equals(other.idDevice)) {
			return false;
		}
		return true;
	}
}
