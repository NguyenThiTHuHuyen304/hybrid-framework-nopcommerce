package javaBasic;

public class Topic_14_StringFormat {

	public static final String CUSTOMER_INFOR_LINK = "//div[contains(@class, 'block-account-navigation')]//a[text() = 'Customer info']";
	public static final String ADDRESS_LINK = "//div[contains(@class, 'block-account-navigation')]//a[text() = 'Addresses']";
	public static final String MY_PRODUCT_REVIEW_LINK = "//div[contains(@class, 'block-account-navigation')]//a[text() = 'My product reviews']";
	public static final String REWARD_POINT_LINK = "//div[contains(@class, 'block-account-navigation')]//a[text() = 'Reward points']";

	public static final String DYNAMIC_SIDEBAR_LINK_BY_PAGE_NAME = "//div[contains(@class, 'block-account-navigation')]//a[text() = '%s']";

	public static final String DYNAMIC_LINK_BY_PAGE_NAME = "//div[contains(@class, '%s')]//a[text() = '%s']";

	public static void main(String[] args) {

		System.out.println("Click to link 1 tham so truyen vao");
		clickToLink(CUSTOMER_INFOR_LINK);
		clickToLink(ADDRESS_LINK);
		clickToLink(MY_PRODUCT_REVIEW_LINK);
		clickToLink(REWARD_POINT_LINK);

		System.out.println("Click to link 2 tham so truyen vao");
		clickToLink(DYNAMIC_SIDEBAR_LINK_BY_PAGE_NAME, "Customer info");
		clickToLink(DYNAMIC_SIDEBAR_LINK_BY_PAGE_NAME, "Addresses");
		clickToLink(DYNAMIC_SIDEBAR_LINK_BY_PAGE_NAME, "My product reviews");
		clickToLink(DYNAMIC_SIDEBAR_LINK_BY_PAGE_NAME, "Reward points");

		System.out.println("Click to link 3 tham so truyen vao");
		clickToLink(DYNAMIC_LINK_BY_PAGE_NAME, "block-account-navigation", "Customer info");
		clickToLink(DYNAMIC_LINK_BY_PAGE_NAME, "block-account-navigation", "Addresses");
		clickToLink(DYNAMIC_LINK_BY_PAGE_NAME, "block-account-navigation", "My product reviews");
		clickToLink(DYNAMIC_LINK_BY_PAGE_NAME, "block-account-navigation", "Reward points");
	}

	// 1 tham so dong
//	public static void clickToLink(String locator) {
//		System.out.println("Click to: " + locator);
//	}
//

	// 2 tham so dong
//	public static void clickToLink(String dynamicLocator, String pageName) {
//		String locator = String.format(dynamicLocator, pageName);
//		System.out.println("Click to: " + locator);
//	}
//

	// 3 tham so dong
//	public static void clickToLink(String dynamicLocator, String areaName, String pageName) {
//		String locator = String.format(dynamicLocator, areaName, pageName);
//		System.out.println("Click to: " + locator);
//	}

	// 1 -> n tham so dong
	public static void clickToLink(String dynamicLocator, String... params) {
		String locator = String.format(dynamicLocator, (Object[]) params);
		System.out.println("Click to: " + locator);
	}
}
