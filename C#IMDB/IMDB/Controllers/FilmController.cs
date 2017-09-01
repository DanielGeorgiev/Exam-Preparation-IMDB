using System.Linq;
using System.Net;
using System.Web.Mvc;
using IMDB.Models;
using System.Data.Entity;

namespace IMDB.Controllers
{
    [ValidateInput(false)]
    public class FilmController : Controller
    {
        [HttpGet]
        [Route("")]
        public ActionResult Index()
        {
            using (var db = new IMDBDbContext())
            {
                var films = db.Films.ToList();

                return View(films);
            }
        }

        [HttpGet]
        [Route("create")]
        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [Route("create")]
        [ValidateAntiForgeryToken]
        public ActionResult Create(Film film)
        {
            if (ModelState.IsValid)
            {
                using (var db = new IMDBDbContext())
                {
                    db.Films.Add(film);
                    db.SaveChanges();
                }
            }

            return RedirectToAction("Index");
        }

        [HttpGet]
        [Route("edit/{id}")]
        public ActionResult Edit(int? id)
        {
            using (var db = new IMDBDbContext())
            {
                var film = db.Films.Find(id);

                if (film != null)
                {
                    return View(film);
                }
                else
                {
                    return RedirectToAction("Index");
                }
            }
        }

        [HttpPost]
        [Route("edit/{id}")]
        [ValidateAntiForgeryToken]
        public ActionResult EditConfirm(int? id, Film filmModel)
        {
            if (ModelState.IsValid)
            {
                using (var db = new IMDBDbContext())
                {
                    var film = db.Films.Find(id);

                    if (film != null)
                    {
                        film.Director = filmModel.Director;
                        film.Genre = filmModel.Genre;
                        film.Name = filmModel.Name;
                        film.Year = filmModel.Year;

                        db.Entry(film).State = EntityState.Modified;
                        db.SaveChanges();
                    }
                }
            }

            return RedirectToAction("Index");
        }

        [HttpGet]
        [Route("delete/{id}")]
        public ActionResult Delete(int? id)
        {
            using (var db = new IMDBDbContext())
            {
                var film = db.Films.Find(id);

                if (film != null)
                {
                    return View(film);
                }
                else
                {
                    return RedirectToAction("Index");
                }
            }
        }

        [HttpPost]
        [Route("delete/{id}")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirm(int? id)
        {
            using (var db = new IMDBDbContext())
            {
                var film = db.Films.Find(id);

                if (film != null)
                {
                    db.Films.Remove(film);
                    db.SaveChanges();
                }
            }

            return RedirectToAction("Index");
        }
    }
}