using Microsoft.EntityFrameworkCore;
using System.IO;

public class PlaylistDbContext : DbContext
{
    public DbSet<Composition> compositions { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        string connectionString = File.ReadAllText("config.txt").Trim();
        optionsBuilder.UseNpgsql(connectionString);
    }
}