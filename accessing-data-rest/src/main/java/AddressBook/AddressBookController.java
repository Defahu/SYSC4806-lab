package AddressBook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AddressBookController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @GetMapping("/createAB")
    public String createAB() {
        return "createAB";
    }

    @PostMapping("/createAB")
    public String createABDone(Model model) {
        AddressBook addressBook = new AddressBook();
        this.addressBookRepository.save(addressBook);
        model.addAttribute("AB", addressBook);
        return "createABResult";
    }

    @GetMapping("/addBuddy")
    public String AddBuddyInfo(@RequestParam long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("buddyInfo", new BuddyInfo());
        return "addBuddy";
    }

    @PostMapping("/addBuddy/{id}")
    public String personSubmit(@PathVariable long id, @ModelAttribute BuddyInfo buddyInfo, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("buddyInfo", buddyInfo);
        AddressBook addressBook = this.addressBookRepository.findById(id);
        addressBook.addBuddy(buddyInfo);
        this.addressBookRepository.save(addressBook);
        return "addBuddyResult";
    }

    @GetMapping("/removeBuddy")
    public String removeBuddyForm(Model model) {
        return "removeBuddy";
    }

    @PostMapping("/removeBuddy")
    public String removeSubmit(@RequestParam("id") String id, Model model) {
        model.addAttribute("id", id);
        addressBookRepository.deleteById(Long.valueOf(id));
        return "addBuddy";
    }

    @GetMapping("/viewAB")
    public String viewAB(@RequestParam long id, Model model) {
        AddressBook addressBook = this.addressBookRepository.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("AB", addressBook);
        return "viewAB";
    }

    @GetMapping("/addressbook/{id}")
    public String addreeBookForm(@PathVariable long id, Model model){
        AddressBook addressBook = this.addressBookRepository.findById(id);
        model.addAttribute("validId", (addressBook != null));
        model.addAttribute("addressbook", addressBook);
        return "addressbook";
    }

    @PostMapping("/addressbook/{id}")
    @ResponseBody
    public AddressBook addBuddyInfo(@PathVariable long id,
                                    @RequestBody BuddyInfo buddy) {
        AddressBook addressBook = this.addressBookRepository.findById(id);
        if (addressBook == null){
            return null;
        }
        addressBook.addBuddy(buddy);
        this.addressBookRepository.save(addressBook);
        return addressBook;
    }

    @DeleteMapping("/addressbook/{id}")
    @ResponseBody
    public AddressBook removeBuddyInfo(@PathVariable long id, @RequestBody BuddyInfo buddy){
        AddressBook addressBook = this.addressBookRepository.findById(id);
        if (addressBook == null){
            return null;
        }

        int index = 0, i = 0;
        for (BuddyInfo buddyInfo: addressBook.getBuddyList()){
            if(buddyInfo.equals(buddyInfo)) index = i;
            i++;
        }

        addressBook.getBuddyList().remove(index);
        this.addressBookRepository.save(addressBook);
        return addressBook;

    }
}
