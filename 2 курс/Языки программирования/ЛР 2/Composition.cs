using System.ComponentModel.DataAnnotations;
public class Composition
{
    [Key]
    public int id { get; set; }
    public int number { get; set; }
    public string author { get; set; }
    public string title { get; set; }
    public string duration { get; set; }

    public Composition(int number, string author, string title, string duration)
    {
        this.number = number;
        this.author = author;
        this.title = title;
        this.duration = duration;
    }

    public Composition() { }
    public override string ToString()
    {
        return $"{number} - {author} - {title} - {duration}";
    }
}
