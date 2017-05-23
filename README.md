# Congress-Information-Search-Phase-3

ABOUT:

* A Congress Information Search tool to look up information about the members of Congress, the committees of the House and Senate, and their daily work concerning bills and amendments

* Utilizes the Sunlight Congress API https://sunlightlabs.github.io/congress/
------------------------------------------------------------------

LIBRARIES USED:

* Apache Commons Lang:
https://commons.apache.org/proper/commons-lang/

* OkHttp:
http://square.github.io/okhttp/

* Picasso:
http://square.github.io/picasso/

-------------------------------------------------------------------

IMPLEMENTATION/CODE RESOURCES/TUTORIALS USED:

(A high-level description of the resource(s)/tutorial(s), followed by their link(s),
followed by a list of file(s) where the code was implemented)

* TabHost
http://www.viralandroid.com/2015/09/simple-android-tabhost-and-tabwidget-example.html
[ Used in: BillsFragment.java, CommitteesFragment.java, LegislatorsFragment.java, FavoritesFragment.java, fragment_bills.xml, fragment_committees.xml, fragment_legislators.xml, fragment_favorites.xml ]

* Filtering in custom array adapters
http://www.survivingwithandroid.com/2012/10/android-listview-custom-filter-and.html
http://stackoverflow.com/questions/14365847/how-to-implement-getfilter-with-custom-adapter-that-extends-baseadapter
http://stackoverflow.com/questions/15194835/filtering-custom-adapter-indexoutofboundsexception
http://www.survivingwithandroid.com/2012/10/android-listview-custom-filter-and.html
http://stackoverflow.com/questions/14365847/how-to-implement-getfilter-with-custom-adapter-that-extends-baseadapter
[ Used in: CommitteesFragment.java, CommitteeItemAdapter.java, BillsFragment.java, BillItempAdapter.java, LegislatorsFragment.java, LegislatorItemAdapter.java ]

* Chained comparator for multi-field sorting
http://www.codejava.net/java-core/collections/sorting-a-list-by-multiple-attributes-example
[ Used in: LegislatorsFragment.java]

* ListView with Alphabetical Side Index
http://androidopentutorials.com/android-listview-with-alphabetical-side-index/
[ Used in: LegislatorsFragment.java, FavoritesFragment.java ]

* Horizontal progress bar with percentage
http://stackoverflow.com/questions/18410984/android-displaying-text-in-center-of-progress-bar
[ Used in: LegislatorDetail.java ]

* Two-column table layout
http://stackoverflow.com/questions/22383932/android-table-column-width-50-50
[ Used in: activity_bill_detail.xml, activity_committee_detail.xml, activity_legislator_detail.xml ]

* Gradient background for list view item
http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/
[ Used in: list_item_bg.xml, list_view_custom.xml ]

* Creating Fragment class & XML
https://www.lynda.com/Android-tutorials/Create-fragment-class-layout/487934/531375-4.html?org=usc.edu
[ Used in: CommitteesFragment.java, BillsFragment.java, LegislatorsFragment.java ]

* Managing fragments (i.e. adding, replacing, hiding, showing fragments & handling the backstack)
https://www.lynda.com/Android-tutorials/Explore-FragmentTransaction-class/487934/531377-4.html?org=usc.edu
https://www.lynda.com/Android-tutorials/Add-fragment-Java/487934/531378-4.html?org=usc.edu
https://www.lynda.com/Android-tutorials/Remove-fragment-Java/487934/531379-4.html?org=usc.edu
[ Used in: MainActivity.java ]

* Parsing JSON data and modeling JSON data as Java objects
https://www.lynda.com/Android-tutorials/Modeling-web-service-data-entities-plain-old-Java-objects-POJOs/163757/179774-4.html?org=usc.edu
https://www.lynda.com/Android-tutorials/Parsing-JSON-web-service-responses/163757/179776-4.html?org=usc.edu
https://www.lynda.com/Android-tutorials/Model-data-POJO-classes/486757/555446-4.html?org=usc.edu
[ Used in: Bill.java, BillsFragment.java, Committee.java, CommitteesFragment.java, Legislator.java, LegislatorsFragment.java ]

* Sorting a collection of complex objects
https://www.lynda.com/Android-tutorials/Manage-data-Java-collections/486757/555447-4.html?org=usc.edu
[ Used in: CommitteesFragment.java, FavoritesFragment.java, LegislatorsItemAdapter.java, LegislatorsFragment.java ]

* Creating a custom list view display & custom array adapter:
https://www.lynda.com/Android-tutorials/Customize-ListView-item-display/486757/555449-4.html?org=usc.edu
https://www.lynda.com/Android-tutorials/Create-custom-array-adapter/486757/555450-4.html?org=usc.edu
[ Used in: BillItemAdapter.java, BillItemAdapterFavorite.java, BillsFragment.java,
CommitteeItemAdapter.java, CommitteeItemAdapterFavorite.java, CommitteesFragment.java,
LegislatorItemAdapter.java, LegislatorItemAdapterFavorite.java, LegislatorsFragment.java,
FavoritesFragment.java ]

* Allowing network access for app:
https://www.lynda.com/Android-tutorials/Setting-permissions-checking-network-connection/163757/179771-4.html?org=usc.edu
[ Used in: AndroidManifest.xml ]

* AsyncTask
https://www.lynda.com/Android-tutorials/Defining-background-tasks-AsyncTask/163757/179767-4.html?org=usc.edu
[ Used in: BillsFragment.java, CommitteesFragment.java, LegislatorsFragment.java ]

* Using implicit intents (for opening Facebook/Twitter/External Website/E-mail)
https://www.lynda.com/Android-tutorials/Open-other-apps-implicit-intents/442863/456790-4.html?org=usc.edu
[ Used in: LegislatorDetail.java ]

* Creating and opening detail pages using intents
https://www.lynda.com/Android-tutorials/Create-open-detail-activity/442863/456801-4.html?org=usc.edu
https://www.lynda.com/Android-tutorials/Pass-data-detail-activity/442863/456802-4.html?org=usc.edu
[ Used in: BillDetail.java, BillsFragment.java, CommitteeDetail.java, CommitteesFragment.java, LegislatorDetail.java, LegislatorsFragment.java FavoritesFragment.java ]

--------------------------------------------------------------------

GENERAL RESOURCES/TUTORIALS USED:

* Lynda.com, "Android App Development Essential Training"
https://www.lynda.com/Android-tutorials/Android-App-Development-Essential-Training/442863-2.html

* Lynda.com, "Android App Development Essentials: Local Data Storage"
  https://www.lynda.com/Android-tutorials/Android-App-Development-Essentials-Local-Data-Storage/486757-2.html

* Lynda.com, "Building Flexible Android Apps with the Fragments API"
https://www.lynda.com/Android-tutorials/Building-Flexible-Android-Apps-Fragments-API-Revision/487934-2.html

* Lynda.com, "Building Adaptive Android Apps with Fragments"
https://www.lynda.com/Android-tutorials/Building-Adaptive-Android-Apps-Fragments/164465-2.html

* Lynda.com, "Connecting Android Apps to RESTful Web Services"
https://www.lynda.com/Android-tutorials/Connecting-Android-Apps-RESTful-Web-Services/163757-2.html
