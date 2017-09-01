const Film = require('../models/Film');

function isValidString(str) {
    return str.trim() !== "";
}

module.exports = {
    index: (req, res) => {
        Film.find().then(films => {
            res.render('film/index', {'films': films});
        });
    },
    createGet: (req, res) => {
        res.render('film/create');
    },
    createPost: (req, res) => {
        let filmArgs = req.body;

        if (isValidString(filmArgs.name) && isValidString(filmArgs.genre) && isValidString(filmArgs.director) && isValidString(filmArgs.year)) {
            Film.create(filmArgs).then(film => {
                res.redirect('/');
            });
        } else {
            res.render('film/create');
        }
    },
    editGet: (req, res) => {
        let id = req.params.id;

        Film.findById(id).then(film => {
                res.render('film/edit', film);
        });
    },
    editPost: (req, res) => {
        let filmArgs = req.body;
        let id = req.params.id;

        if (isValidString(filmArgs.name) && isValidString(filmArgs.genre) && isValidString(filmArgs.director) && isValidString(filmArgs.year)) {
            Film.findByIdAndUpdate(id, filmArgs).then(film => {
                res.redirect('/');
            });
        } else {
            res.redirect('/');
        }
    },
    deleteGet: (req, res) => {
        let id = req.params.id;

        Film.findById(id).then(film => {
            if (!film) {
                res.redirect('/');
            } else {
                res.render('film/delete', film);
            }
        });
    },
    deletePost: (req, res) => {
        let id = req.params.id;

        Film.findByIdAndRemove(id).then(film => {
            res.redirect('/');
        });
    }
};