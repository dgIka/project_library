package ika.library.controllers;

import ika.library.dao.BookDAO;
import ika.library.dao.PersonDAO;
import ika.library.models.Book;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", personDAO.index());
        return "books/show";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "books/edit";
        bookDAO.update(id, book);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/personId")
    public String updatePersonId(@PathVariable("id") int id, @RequestParam(value = "personId", required = false) Integer personId ) {
        bookDAO.update(id, personId);
        return "redirect:/books/" + id;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

}
