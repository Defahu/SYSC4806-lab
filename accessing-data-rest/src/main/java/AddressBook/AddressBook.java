package AddressBook;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** Address book to store BuddyInfo objects. Implemented using an List data structure
 *
 *
 */
@Entity
public class AddressBook{
    @Id
    @GeneratedValue
    private long Id;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<BuddyInfo> buddyList;


    /** Constructor for AddressBook object.
     * @param Id id for AddressBook
     */
    public AddressBook(long Id) {
        this.Id = Id;
        this.buddyList =  new ArrayList<BuddyInfo>();
    }

    /** Constructor for AddressBook object.
     */
    public AddressBook() {
        this.buddyList =  new ArrayList<BuddyInfo>();
    }

    @Transient
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AddressBook otherBook = (AddressBook) obj;
        return otherBook.buddyList.equals(buddyList);
    }

    /** Adds a buddy to the address book. Does not check for duplicates and does not check
     * if buddy is null.
     * @param buddy Buddy to be added
     */
    @Transient
    public void addBuddy(BuddyInfo buddy) {
        this.buddyList.add(buddy);
    }

    /** Removes the first instance of a buddy from the address book.
     * Does not check for duplicates and does not check for non existent or null inputs.
     * @param buddy Buddy to be removed
     */
    @Transient
    public void removeBuddy(BuddyInfo buddy) {
        this.buddyList.remove(buddy);
    }

    @Override
    public String toString() {
        return "AddressBook{" +
                "buddyList=" + buddyList +
                '}';
    }

    public List<BuddyInfo> getBuddyList() {
        return buddyList;
    }

    public void setBuddyList(List<BuddyInfo> buddyList) {
        this.buddyList = buddyList;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}