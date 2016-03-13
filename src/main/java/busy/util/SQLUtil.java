package busy.util;

public class SQLUtil {

	// Default value
	public static final String DEFAULT = "DEFAULT";

	// Database table names
	public static final String TABLE_COUNTRY = "country";
	public static final String TABLE_CITY = "city";
	public static final String TABLE_ADDRESS = "address";
	public static final String TABLE_USER = "person";
	public static final String TABLE_REGISTRY = "verification";
	public static final String TABLE_CATEGORY = "category";
	public static final String TABLE_COMPANY = "company";
	public static final String TABLE_BRANCH = "branch";
	public static final String TABLE_ROLE = "role";
	public static final String TABLE_NOTIFICATION = "notification";

	// Table fields names
	public static final String ID = "id";

	public static final String NAME = "name";
	public static final String CODE = "code";

	public static final String COUNTRYID = "country_id";

	public static final String ADDR1 = "address1";
	public static final String ADDR2 = "address2";
	public static final String ZIPCODE = "zip_code";
	public static final String CITYID = "city_id";

	public static final String FIRSTNAME = "first_name";
	public static final String LASTNAME = "last_name";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String NIF = "nif";
	public static final String PHONE = "phone";
	public static final String ACTIVE = "active";
	public static final String ADMIN = "admin_role";
	public static final String ADDRID = "address_id";

	public static final String USERID = "person_id";
	public static final String TOKEN = "confirmation_key";

	public static final String TRADE_NAME = "trade_name";
	public static final String BUSINESS_NAME = "business_name";
	public static final String CIF = "cif";
	public static final String CATEGORYID = "category_id";

	public static final String COMPANYID = "company_id";
	public static final String HEADQUARTER = "main";

	public static final String BRANCHID = "branch_id";
	public static final String IS_MANAGER = "is_manager";
	public static final String ACTIVITY = "activity";

	public static final String NOTIFICATION_TYPE = "notif_type";
	public static final String MESSAGE = "message";
	public static final String IS_READ = "read";
	public static final String CREATE_DATE = "create_date";

	// Table field alias
	public static final String ALIAS_COUNTRY_ID = "countryId";
	public static final String ALIAS_CITY_ID = "cityId";
	public static final String ALIAS_ADDR_ID = "addrId";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_CATEGORY_ID = "categoryId";
	public static final String ALIAS_COMPANY_ID = "companyId";
	public static final String ALIAS_BRANCH_ID = "branchId";
	public static final String ALIAS_ROLE_ID = "roleId";

	public static final String ALIAS_COUNTRY_NAME = "countryName";
	public static final String ALIAS_CITY_NAME = "cityName";
	public static final String ALIAS_CATEGORY_NAME = "categoryName";

	public static final String ALIAS_USER_EMAIL = "userEmail";
	public static final String ALIAS_COMPANY_EMAIL = "companyEmail";

	public static final String ADDRESS_SELECT_QUERY = "SELECT " + TABLE_ADDRESS + "." + ID + " AS " + ALIAS_ADDR_ID
			+ "," + ADDR1 + "," + ADDR2 + "," + ZIPCODE + ", cityJoin.* FROM " + TABLE_ADDRESS + " LEFT JOIN ( SELECT "
			+ TABLE_CITY + "." + ID + " AS " + ALIAS_CITY_ID + "," + TABLE_CITY + "." + NAME + " AS " + ALIAS_CITY_NAME
			+ "," + TABLE_COUNTRY + "." + ID + " AS " + ALIAS_COUNTRY_ID + "," + TABLE_COUNTRY + "." + NAME + " AS "
			+ ALIAS_COUNTRY_NAME + "," + CODE + " FROM " + TABLE_CITY + " LEFT JOIN " + TABLE_COUNTRY + " ON "
			+ TABLE_CITY + "." + COUNTRYID + "=" + TABLE_COUNTRY + "." + ID + ") AS cityJoin " + "ON " + TABLE_ADDRESS
			+ "." + CITYID + "= cityJoin." + ALIAS_CITY_ID;

	public static final String USER_SELECT_QUERY = "SELECT " + TABLE_USER + "." + ID + " AS " + ALIAS_USER_ID + ","
			+ FIRSTNAME + "," + LASTNAME + "," + EMAIL + "," + PASSWORD + "," + NIF + "," + PHONE + "," + ACTIVE + ","
			+ ADMIN + ", addressJoin.* FROM " + TABLE_USER + " LEFT JOIN (" + ADDRESS_SELECT_QUERY
			+ ") AS addressJoin ON " + TABLE_USER + "." + ADDRID + "= addressJoin." + ALIAS_ADDR_ID;

	public static final String CATEGORY_SELECT_QUERY = "SELECT " + TABLE_CATEGORY + "." + ID + " AS "
			+ ALIAS_CATEGORY_ID + "," + NAME + " AS " + ALIAS_CATEGORY_NAME + " FROM " + TABLE_CATEGORY;

	public static final String COMPANY_SELECT_QUERY = "SELECT " + TABLE_COMPANY + "." + ID + " AS " + ALIAS_COMPANY_ID
			+ "," + TRADE_NAME + "," + BUSINESS_NAME + "," + CIF + "," + CREATE_DATE + "," + ACTIVE + "," + EMAIL + " AS "
			+ ALIAS_COMPANY_EMAIL + ", categoryJoin.* FROM " + TABLE_COMPANY + " LEFT JOIN (" + CATEGORY_SELECT_QUERY
			+ ") AS categoryJoin ON " + TABLE_COMPANY + "." + CATEGORYID + "= categoryJoin." + ALIAS_CATEGORY_ID;

	public static final String BRANCH_SELECT_QUERY = "SELECT " + TABLE_BRANCH + "." + ID + " AS " + ALIAS_BRANCH_ID
			+ "," + HEADQUARTER + "," + PHONE + ", addressJoin.*, companyJoin.* FROM " + TABLE_BRANCH + " LEFT JOIN ("
			+ ADDRESS_SELECT_QUERY + ") AS addressJoin ON " + TABLE_BRANCH + "." + ADDRID + "= addressJoin."
			+ ALIAS_ADDR_ID + " LEFT JOIN (" + COMPANY_SELECT_QUERY + ") AS companyJoin ON " + TABLE_BRANCH + "."
			+ COMPANYID + "= companyJoin." + ALIAS_COMPANY_ID;

	public static final String ROLE_SELECT_QUERY = "SELECT " + TABLE_ROLE + "." + ID + " AS " + ALIAS_ROLE_ID + ","
			+ IS_MANAGER + "," + ACTIVITY + ", branchJoin.*, userJoin.* FROM " + TABLE_ROLE + " LEFT JOIN (" + BRANCH_SELECT_QUERY
			+ ") AS branchJoin ON " + TABLE_ROLE + "." + BRANCHID + "= branchJoin." + ALIAS_BRANCH_ID + " LEFT JOIN ("
			+ USER_SELECT_QUERY + ") AS userJoin ON " + TABLE_ROLE + "." + USERID + "=userJoin." + ALIAS_USER_ID;
}
