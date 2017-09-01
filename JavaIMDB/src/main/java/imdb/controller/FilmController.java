package imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import imdb.bindingModel.FilmBindingModel;
import imdb.entity.Film;
import imdb.repository.FilmRepository;

import java.util.List;

@Controller
public class FilmController {

	private final FilmRepository filmRepository;

	@Autowired
	public FilmController(FilmRepository filmRepository) {
		this.filmRepository = filmRepository;
	}

	@GetMapping("/")
	public String index(Model model) {
		List<Film> allFilms = filmRepository.findAll();
		model.addAttribute("view", "film/index");
		model.addAttribute("films", allFilms);

		return "base-layout";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("view", "film/create");
		return "base-layout";
	}

	@PostMapping("/create")
	public String createProcess(Model model, FilmBindingModel filmBindingModel) {
		Film film = new Film();

		if (!(filmBindingModel.getName().equals("")) && !(filmBindingModel.getDirector().equals("")) && !(filmBindingModel.getGenre().equals("")) && filmBindingModel.getYear() >= 1902 ){
			film.setDirector(filmBindingModel.getDirector());
			film.setGenre(filmBindingModel.getGenre());
			film.setName(filmBindingModel.getName());
			film.setYear(filmBindingModel.getYear());

			filmRepository.saveAndFlush(film);
		}

		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		Film film = filmRepository.findOne(id);

		if (film != null){
			model.addAttribute("view", "film/edit");
			model.addAttribute("film", film);

			return "base-layout";
		}

		return "redirect:/";
	}

	@PostMapping("/edit/{id}")
	public String editProcess(Model model, @PathVariable int id, FilmBindingModel filmBindingModel) {
		Film film = filmRepository.findOne(id);

		if (!(filmBindingModel.getName().equals("")) && !(filmBindingModel.getDirector().equals("")) && !(filmBindingModel.getGenre().equals("")) && filmBindingModel.getYear() >= 1902){
			film.setDirector(filmBindingModel.getDirector());
			film.setGenre(filmBindingModel.getGenre());
			film.setName(filmBindingModel.getName());
			film.setYear(filmBindingModel.getYear());

			filmRepository.saveAndFlush(film);
		}

		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable int id) {
		Film film = filmRepository.findOne(id);

		if (film != null){
			model.addAttribute("view", "film/delete");
			model.addAttribute("film", film);

			return "base-layout";
		}

		return "redirect:/";
	}

	@PostMapping("/delete/{id}")
	public String deleteProcess(@PathVariable int id) {
		Film film = filmRepository.findOne(id);

		if (film != null){
			filmRepository.delete(film);
			filmRepository.flush();
		}

		return "redirect:/";
	}
}
