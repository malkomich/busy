package busy.company.web;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class CompanyForm {

	// FIELDS
	
	@Length(max = 30, message = "{tradename.maxlength}")
	private String tradeName;
	
	@NotEmpty(message = "{businessname.required}")
	@Length(max = 50, message = "{businessname.maxlength}")
	private String businessName;
	
	@NotEmpty(message = "{email.required}")
	@Email(message = "{email.wrong_format}")
	@Length(max = 50, message = "{email.maxlength}")
	private String email;
	
	@Length(min = 9, max = 9, message = "{cif.length}")
	@Pattern(regexp = "[a-zA-Z][0-9]*", message = "{cif.wrong_format}")
	private String cif;
	
	@NotEmpty(message = "{country.required}")
	private String countryCode;
	
	@NotEmpty(message = "{city.required}")
	private String cityId;
	
	@Length(max = 10, message = "{zipcode.maxlength}")
	private String zipCode;
	
	@Length(min = 8, max = 12, message = "{phone.length}")
	@Pattern(regexp = "[0-9]*", message = "{phone.wrong_format}")
	private String phone;
	
	@Length(max = 35, message = "{address.maxlength}")
	private String address1;
	
	@Length(max = 35, message = "{address.maxlength}")
	private String address2;
	
	private String categoryId;

	// GETTERS AND SETTERS
	
	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {

		if(tradeName != "")
			this.tradeName = tradeName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		
		if(zipCode != "")
			this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {

		if(phone != "")
			this.phone = phone;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {

		if(address1 != "")
			this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {

		if(address2 != "")
			this.address2 = address2;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {

		if(categoryId != "" && categoryId != "0")
			this.categoryId = categoryId;
	}
	
}
