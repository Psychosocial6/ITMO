package client.dataValidation;
/**
 * Класс {@code CommandParser} предназначен для разбиения входной строки команды на отдельные слова.
 */
public class CommandParser {
	/**
     * Разбирает введенную строку команды на массив слов.
     *
     * @param input строка, содержащая команду и её аргументы
     * @return массив строк, где первый элемент - название команды, а последующие - её аргументы
     */
	public String[] parseCommandName(String input) {
		String[] words = input.split("\\s+");
		return words;
	}
}
