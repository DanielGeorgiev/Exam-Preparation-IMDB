using System.Data.Entity;

namespace IMDB.Models
{
    public class IMDBDbContext : DbContext
    {
        public virtual DbSet<Film> Films { get; set; }

        public IMDBDbContext() : base("IMDB")
        {
        }
    }
}