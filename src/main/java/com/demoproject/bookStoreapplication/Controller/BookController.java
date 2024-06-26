package com.demoproject.bookStoreapplication.Controller;

import com.demoproject.bookStoreapplication.DTO.BookDTO;
import com.demoproject.bookStoreapplication.Service.BookService;
import com.demoproject.bookStoreapplication.Service.UserServiceProvider;
import com.demoproject.bookStoreapplication.databaseClasses.Book;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import org.apache.coyote.Response;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class BookController {
    BookService bookService;
    UserServiceProvider userServiceProvider;

    public BookController(BookService bookService, UserServiceProvider userServiceProvider) {
        this.bookService = bookService;
        this.userServiceProvider = userServiceProvider;
    }

    @InitBinder
    public void initbinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String home(Model themodel) {
        Calendar calendar = new GregorianCalendar();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        themodel.addAttribute("year", year);
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model themodel) {
        List<Book> booklist = bookService.bookList();
        themodel.addAttribute("booklist", booklist);
        themodel.addAttribute("error", "Book is not available at this is moment!!!!!!!!");
        return "dashboard";
    }

    @GetMapping("/mybooklist")
    public String mybooklist(Model themodel, Authentication authentication) {
        String name = authentication.getName();
        Register register = userServiceProvider.findUser(name);
        List<Book> booklist = register.getBooks();
        Map<Book, Integer> numberofBooks = new HashMap<>();
        for (Book tempbook : booklist) {
            numberofBooks.put(tempbook, numberofBooks.getOrDefault(tempbook, 0) + 1);
        }
        double totalcost = 0;
        for (Book key : numberofBooks.keySet()) {
            totalcost += (key.getPrice() * numberofBooks.get(key));
        }
        themodel.addAttribute("bookslist", numberofBooks);
        themodel.addAttribute("totalcost", totalcost);
        return "mybookslist";
    }

    @GetMapping("/addbook")
    public String addBook(Model themodel) {
        themodel.addAttribute("book", new BookDTO());
        themodel.addAttribute("error", "Book Already Exists!!!!!!!");
        return "bookform";
    }

    @PostMapping("/processAddingBook")
    public String addBookProcess(@ModelAttribute("book") BookDTO bookDTO, Model themodel) {
        String title = bookDTO.getTitle();
        Book resultBook = bookService.findBook(title);
        if (resultBook != null) {
            themodel.addAttribute("message", "Book Already Exist!!!!");
            return "redirect:/addbook?error";
        }
        try {
            bookService.addBook(bookDTO);
            themodel.addAttribute("book", bookDTO);
        } catch (Exception e) {
            themodel.addAttribute("message", "Internal Server Error");
            return "redirect:/addbook";
        }
        return "addedbookconfirmationpage";
    }

    @PostMapping("/addbookaccordingtouser")
    public String addBookAccordingtouser(@RequestParam("bookid") int theid, Model themodel, Authentication authentication, Response response) {
        String username = authentication.getName();
        Book book = bookService.findBookById(theid);
        Register user = userServiceProvider.findUser(username);
        List<Book> booklist = user.getBooks();
        if (booklist == null) {
            booklist = new ArrayList<>();
        }
        if (book.getAvailableQuantity() <= 0) {
            int resultid = book.getId();
            return "redirect:/dashboard?error";
        }
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        booklist.add(book);
        user.setBooks(booklist);
        userServiceProvider.addUpdatedUser(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/removeBook")
    public String removebook(@RequestParam("bookid") int theid, Authentication authentication) {
        String username = authentication.getName();
        Register register = userServiceProvider.findUser(username);
        Book book = bookService.findBookById(theid);
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        List<Book> booklist = register.getBooks();
        booklist.remove(book);
        register.setBooks(booklist);
        userServiceProvider.addUpdatedUser(register);
        return "redirect:/mybooklist";
    }

    // performing the deletion of the book from the list
    @PostMapping("/deletingbookfromdashboard")
    public String deleteBook(@RequestParam("bookid") int theid) {
        Book book = bookService.findBookById(theid);
        for (Register register : book.getRegisters()) {
            register.setBooks(null);
        }
        bookService.removeBook(book);
        return "redirect:/dashboard";
    }
}
