package com.demoproject.bookStoreapplication.Controller;

import com.demoproject.bookStoreapplication.DTO.BookDTO;
import com.demoproject.bookStoreapplication.Service.BookService;
import com.demoproject.bookStoreapplication.Service.UserServiceProvider;
import com.demoproject.bookStoreapplication.databaseClasses.Book;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    BookService bookService;
    UserServiceProvider userServiceProvider;

    public BookController(BookService bookService, UserServiceProvider userServiceProvider) {
        this.bookService = bookService;
        this.userServiceProvider = userServiceProvider;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model themodel) {
        List<Book> booklist = bookService.bookList();
        themodel.addAttribute("booklist", booklist);
        return "dashboard";
    }

    @GetMapping("/mybooklist")
    public String mybooklist() {
        return "mybookslist";
    }

    @GetMapping("/addbook")
    public String addBook(Model themodel) {
        themodel.addAttribute("book", new BookDTO());
        return "bookform";
    }

    @PostMapping("/processAddingBook")
    public String addBookProcess(@ModelAttribute("book") BookDTO bookDTO, Model themodel) {
        String title = bookDTO.getTitle();
        Book resultBook = bookService.findBook(title);
        if (resultBook != null) {
            themodel.addAttribute("error", "Book Already Exist");
            return "redirect:/addbook";
        }
        bookService.addBook(bookDTO);
        return "redirect:/addbook";
    }

    @GetMapping("/addbookaccordingtouser")
    public String addBookAccordingtouser(@RequestParam("bookid") int theid, Model themodel, Authentication authentication) {
        String username = authentication.getName();
        System.out.println(theid);
        Book book = bookService.findBookById(theid);
        Register user = userServiceProvider.findUser(username);
        List<Book> booklist = user.getBooks();
        if (booklist == null) {
            booklist = new ArrayList<>();
        }
        booklist.add(book);
        user.setBooks(booklist);
        userServiceProvider.addUpdatedUser(user);
        return "redirect:/dashboard";
    }
}
