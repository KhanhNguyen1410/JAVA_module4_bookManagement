package controller;

import model.Book;
import model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.BookService;
import service.CategoryService;

import java.util.Optional;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<Book> allBook(Pageable pageable){
        return bookService.findAll(pageable);
    }

    @GetMapping("/list")
    public ModelAndView showList(@RequestParam("s") Optional<String> s, @PageableDefault(8)Pageable pageable){
        Page<Book> books;
        if (s.isPresent()){
            books = bookService.findAllByNameContaining(s.get(),pageable);

        }else {
            books =  bookService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping(value = "/createNewBook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Book createSmartphone(@RequestBody Book book) {
        Category category = categoryService.findById(book.getCategory().getId());
        book.setCategory(category);
        return bookService.save(book);
    }
    @GetMapping("/create")
    public ModelAndView showListForm(){
            ModelAndView modelAndView = new ModelAndView("book/create");
            modelAndView.addObject("book", new Book());
            return modelAndView;
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Book deleteSmartphone(@PathVariable Long id){

        return bookService.remove(id);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editBook(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("book/edit");
        Book book = bookService.findById(id);
        mav.addObject("book", book);
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Book editSmartphone(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        Category category = categoryService.findById(book.getCategory().getId());
        book.setCategory(category);
        return bookService.save(book);
    }

}
