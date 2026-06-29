public class Program
{
    public static void Main(string[] args)
    {
        using PlaylistDbContext dbContext = new PlaylistDbContext();
        Playlist playlist = new Playlist(dbContext);

        while (true)
        {
            showMenu();
            string choice = Console.ReadLine();

            switch (choice)
            {
                case "1":
                    searchComposition(playlist);
                    break;
                case "2":
                    showAllCompositions(playlist);
                    break;
                case "3":
                    addComposition(playlist);
                    break;
                case "4":
                    removeComposition(playlist);
                    break;
                case "5":
                    Console.WriteLine("Выход из программы.");
                    return;
                default:
                    Console.WriteLine("Неверная команда. Попробуйте еще раз.");
                    break;
            }
        }
    }

    static void showMenu()
    {
        Console.WriteLine("Выберите команду:");
        Console.WriteLine("1. Поиск композиции");
        Console.WriteLine("2. Показать все композиции");
        Console.WriteLine("3. Добавить композицию");
        Console.WriteLine("4. Удалить композицию");
        Console.WriteLine("5. Выход");
    }

    static void searchComposition(Playlist playlist)
    {
        Console.WriteLine("Введите текст для поиска (автор или название): ");
        string s = Console.ReadLine();

        if (string.IsNullOrWhiteSpace(s))
        {
            Console.WriteLine("Некорректный запрос");
            return;
        }

        List<Composition> results = playlist.search(s);
        displayCompositions(results, "Результаты поиска");
    }

    static void showAllCompositions(Playlist playlist)
    {
        List<Composition> allCompositions = playlist.getAllCompositions();
        displayCompositions(allCompositions, "Список композиций");
    }

    static void addComposition(Playlist playlist)
    {
        Console.Write("Введите номер: ");
        if (!int.TryParse(Console.ReadLine(), out int number))
        {
            Console.WriteLine("Некорректный ввод");
            return;
        }

        Console.Write("Введите автора: ");
        string author = Console.ReadLine();

        Console.Write("Введите название: ");
        string title = Console.ReadLine();

        Console.Write("Введите длительность (формат MM:SS): ");
        string duration = Console.ReadLine();

        if (string.IsNullOrWhiteSpace(author) || string.IsNullOrWhiteSpace(title) || string.IsNullOrWhiteSpace(duration))
        {
            Console.WriteLine("Автор, название и длительность не могут быть пустыми");
            return;
        }

        Composition newComposition = new Composition(number, author, title, duration);
        playlist.addComposition(newComposition);
    }

    static void removeComposition(Playlist playlist)
    {
        Console.Write("Введите номер композиции для удаления: ");
        if (!int.TryParse(Console.ReadLine(), out int number))
        {
            Console.WriteLine("Некорректный ввод");
            return;
        }

        playlist.removeComposition(number);
    }

    static void displayCompositions(List<Composition> compositions, string title)
    {
        if (!compositions.Any())
        {
            Console.WriteLine("Композиции не найдены.");
            return;
        }

        Console.WriteLine($"\n{title}:");
        foreach (Composition composition in compositions)
        {
            Console.WriteLine(composition.ToString());
        }
    }
}
