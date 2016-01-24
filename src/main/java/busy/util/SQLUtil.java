package busy.util;

public class SQLUtil {
	
	// Default value
	public static final String DEFAULT = "DEFAULT";

	// Database table names
	public static final String TABLE_USER = "person";
	public static final String TABLE_ADDRESS = "address";
	public static final String TABLE_CITY = "city";
	public static final String TABLE_COUNTRY = "country";
	public static final String TABLE_REGISTRY = "registry";
	
	// Table fields names
	public static final String ID = "id";
	
	public static final String FIRSTNAME = "first_name";
	public static final String LASTNAME = "last_name";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String NIF = "nif";
	public static final String PHONE = "phone";
	public static final String ACTIVE = "active";
	public static final String ADMIN = "admin_role";
	public static final String ADDRID = "address_id";
	
	public static final String ADDR1 = "address1";
	public static final String ADDR2 = "address2";
	public static final String ZIPCODE = "zip_code";
	public static final String CITYID = "city_id";
	
	public static final String NAME = "name";
	public static final String COUNTRYID = "country_id";
	
	public static final String CODE = "code";
	
	public static final String USERID = "person_id";
	public static final String TOKEN = "confirmation_key";
	
	// Table field alias
	public static final String ALIAS_USERID = "userId";
	public static final String ALIAS_ADDRID = "addrId";
	public static final String ALIAS_CITYID = "cityId";
	public static final String ALIAS_COUNTRYID = "countryId";
	
	public static final String ALIAS_CITYNAME = "cityName";
	public static final String ALIAS_COUNTRYNAME = "countryName";
	
	public static final String ADDRESS_SELECT_QUERY = "SELECT " + TABLE_ADDRESS + "." + ID + " AS "
			+ ALIAS_ADDRID + "," + ADDR1 + "," + ADDR2 + "," + ZIPCODE + ", cityJoin.* FROM " + TABLE_ADDRESS
			+ " LEFT JOIN ( SELECT " + TABLE_CITY + "." + ID + " AS " + ALIAS_CITYID + "," + TABLE_CITY + "." + NAME
			+ " AS " + ALIAS_CITYNAME + "," + TABLE_COUNTRY + "." + ID + " AS " + ALIAS_COUNTRYID + "," + TABLE_COUNTRY
			+ "." + NAME + " AS " + ALIAS_COUNTRYNAME + "," + CODE + " FROM " + TABLE_CITY + " LEFT JOIN "
			+ TABLE_COUNTRY + " ON " + TABLE_CITY + "." + COUNTRYID + "=" + TABLE_COUNTRY + "." + ID + ") AS cityJoin "
			+ "ON " + TABLE_ADDRESS + "." + CITYID + "= cityJoin." + ALIAS_CITYID;
	
	public static final String USER_SELECT_QUERY = "SELECT " + TABLE_USER + "." + ID + " AS " + ALIAS_USERID + ","
			+ FIRSTNAME + "," + LASTNAME + "," + EMAIL + "," + PASSWORD + "," + NIF + "," + PHONE + "," + ACTIVE + ","
			+ ADMIN + ", addressJoin.* FROM " + TABLE_USER + " LEFT JOIN (" + ADDRESS_SELECT_QUERY + ") AS addressJoin ON " + TABLE_USER
			+ "." + ADDRID + "= addressJoin." + ALIAS_ADDRID;
}
