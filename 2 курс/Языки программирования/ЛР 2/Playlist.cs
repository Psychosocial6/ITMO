public class Playlist
{
    private PlaylistDbContext context;
    public Playlist(PlaylistDbContext context)
    {
        this.context = context;
    }

    public void addComposition(Composition composition)
    {
        if (context.compositions.Any(c => c.number == composition.number))
        {
            Console.WriteLine("Композиция с таким номером уже существует");
            return;
        }
        context.compositions.Add(composition);
        context.SaveChanges();
        Console.WriteLine("Композиция успешно добавлена.");
    }

    public void removeComposition(int number)
    {
        var composition = context.compositions.FirstOrDefault(c => c.number == number);
        if (composition != null)
        {
            context.compositions.Remove(composition);
            context.SaveChanges();
            Console.WriteLine("Композиция удалена");
            return;
        }
        Console.WriteLine("Композиция с таким номером не найдена");
    }

    public List<Composition> search(string searchTerm)
    {
        if (string.IsNullOrWhiteSpace(searchTerm))
        {
            return new List<Composition>();
        }
        return context.compositions
            .Where(c =>
                c.author.ToLower().Contains(searchTerm.ToLower()) ||
                c.title.ToLower().Contains(searchTerm.ToLower()))
            .ToList();
    }

    public List<Composition> getAllCompositions()
    {
        return context.compositions.OrderBy(c => c.number).ToList();
    }
}