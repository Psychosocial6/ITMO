package client.dataValidation;

import java.util.HashMap;
import java.util.Map;

import client.dataInput.DataInput;
import client.dataStorage.DataForMovie;


/**
 * Класс для проверки и обработки пользовательского ввода.
 * Содержит методы для сбора данных о фильме, его координатах, операторе и локации.
 * Данные сохраняются в виде карты (Map), где ключи — это названия полей, а значения — введённые данные.
 */
public class CheckInput implements InputChecker{
	DataInput dataInput = new DataInput();
	/**
     * Карта для хранения введённых данных.
     */
	 public static Map<String, Object> data = new HashMap<>();
	 

	 /**
	  * Основной метод для сбора и проверки введённых данных.
	  * Запрашивает у пользователя ввод данных для фильма, его координат, оператора и локации.
	  * Проверяет корректность введённых данных и сохраняет их в карту {@link #data}.
	  * 
	  * @return Карта с введёнными данными
	  */
	@Override 
	public Map<String, Object> checkInput() {
		
		filmName();
		
		System.out.println("введите координаты:");
		coordinateX();
		coordinateY();

		oscarsCount();
		inputTotalBoxOffice();
		inputUsaBoxOffice();
		inputMovieGenre();
		
		System.out.println("введите данные о операторе:");
		inputOperatorName();
		inputOperatorHeight();
		inputOperatorEye();
		inputOperatorHair();
		inputOperatorNationality();
		
		System.out.println("Введите данные о локации оператора:");
		inputLocationX();
		inputLocationY();
		inputLocationZ();
		inputLocationName();
		
		
		//data.forEach((key, value) -> System.out.println(key + " - " + value));
		return data;
		
	}
	
	
	
	private void filmName() {
		System.out.println("введите название фильма:");	
		while(true) {
			System.out.print("> ");
            String inputName = dataInput.input();
            if(inputName == null|| inputName.isEmpty()) {
            	System.out.println("Название не может быть пустым.");
            }
            else {
            	data.put("Name",inputName);
            	break;
            }
		}
	}
	
	
	private void coordinateX() {
		System.out.println("введите координату X:");
		while(true) {
			int x;
			System.out.print("> ");
            String inputX = dataInput.input();
            if(CheckData.isInteger(inputX)) {
            	x = Integer.parseInt(inputX);
            	if (x > -754) {
            		data.put("Coordinates_X", x);
                    break; 
                } else System.out.println("Ошибка: X должен быть больше -754. Попробуйте снова.");

            } else System.out.println("Ошибка: Введите целое число.");
		}
	}
	
	
	private void coordinateY() {
		System.out.println("введите координату Y:");
		while(true) {
			long y;
			System.out.print("> ");
            String inputY = dataInput.input();
            try {
                y = Long.parseLong(inputY);
                data.put("Coordinates_Y", y);
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите число типа long.");
            }    
		}
		
	}
	
	private void oscarsCount() {
		System.out.println("введите колличество оскаров:");
		while(true) {
			int oscarsCount;
			System.out.print("> ");
            String inputOscasrs = dataInput.input();
            if(CheckData.isInteger(inputOscasrs)) {
            	oscarsCount = Integer.parseInt(inputOscasrs);
            	if (oscarsCount > 0) {
            		data.put("OscarsCount", oscarsCount);
                    break; 
                } else System.out.println("Ошибка: значение должено быть больше 0. Попробуйте снова.");
            	
            } else System.out.println("Ошибка: Введите целое число.");

		}
	}
	
	
	private void inputTotalBoxOffice() {
		System.out.println("введите общую кассу фильма:");
		while(true) {
			double boxOffice;
			System.out.print("> ");
            String inputTotalBoxOffice = dataInput.input();
            if(CheckData.isDouble(inputTotalBoxOffice)) {
            	boxOffice = Double.parseDouble(inputTotalBoxOffice);
            	if(boxOffice>0) {
            		data.put("TotalBoxOffice", boxOffice);
            		break;
            	}else System.out.println("Ошибка: сумма не может быть отрицательной.");
            	
            }
            else System.out.println("Ошибка: Введите число.");
      
		}
	}
	
	private void inputUsaBoxOffice() {
		System.out.println("введите сборы фильма в США:");
		while(true) {
			double usaOffice;
			System.out.print("> ");
            String inputUsaBoxOffice = dataInput.input();
            if(inputUsaBoxOffice== null || inputUsaBoxOffice.isEmpty()) {
            	System.out.println("Сборы не могут быть пустыми.");
			}
            else {
            	if(CheckData.isDouble(inputUsaBoxOffice)) {
            		usaOffice = Double.parseDouble(inputUsaBoxOffice);
            		if(usaOffice>0) {
            			data.put("UsaBoxOffice", usaOffice);
            			break;
            		}else System.out.println("Ошибка: сумма не может быть отрицательной.");
            	}
            	else System.out.println("Ошибка: Введите число.");   
            }      
		}
	}
	
	private void inputMovieGenre() {
	    System.out.println("Введите жанр фильма из предложенного списка:");
	    while (true) {
	        System.out.println("Список жанров:");
	        for (int i = 0; i < DataForMovie.genreNames.length; i++) {
	            System.out.println((i + 1) + ". " + DataForMovie.genreNames[i]);
	        }

	        System.out.print("> ");
	        String inputGenre = dataInput.input().trim();

	        if (inputGenre.isEmpty()) {
	            data.put("Genre", null);
	            break;
	        }

	        if (CheckData.isInteger(inputGenre)) {
	            int index = Integer.parseInt(inputGenre) - 1;
	            if (index >= 0 && index < DataForMovie.genreNames.length) {
	                data.put("Genre", DataForMovie.genreNames[index].toUpperCase());
	                break;
	            } else {
	                System.out.println("Ошибка: выбора с таким номером нет в списке.");
	                continue;
	            }
	        }
	        String selectedGenre = null;
	        for (int i = 0; i < DataForMovie.genreNames.length; i++) {
	            if (DataForMovie.genreNames[i].equalsIgnoreCase(inputGenre)) {
	                selectedGenre = DataForMovie.genreNames[i];
	                break;
	            }
	        }

	        if (selectedGenre != null) {
	            data.put("Genre", selectedGenre.toUpperCase());
	            break;
	        } else {
	            System.out.println("Ошибка: введён некорректный жанр.");
	        }
	    }
	}
	
	private void inputOperatorName() {
		System.out.println("введите имя оператора:");
		while(true) {
			
			System.out.print("> ");
            String inputNameOp = dataInput.input();
			if(inputNameOp.trim().equals("")){
				
				System.out.println("Ошибка: У оператора должно быть имя");
			}
			else {
				data.put("Operator_Name" ,inputNameOp);
				break;
			}
			
        }
	}
	
	
	private void inputOperatorHeight() {
		System.out.println("Введите рост оператора:");
		while(true) {
			System.out.print("> ");
            String inputHeightOp = dataInput.input();
			try {
				int height = Integer.parseInt(inputHeightOp);
				if(Integer.parseInt(inputHeightOp)>=0) {
					data.put("Operator_Height", height);
					break;
				}
				else {
					System.out.println("Ошибка: Рост не может быть <= 0");
					continue;
				}
			}
			catch(IllegalArgumentException e){
				System.out.println("Ошибка: введите число");
			}
			
        }
	}
	
	private void inputOperatorEye() {
	    System.out.println("Цвет глаз оператора из списка:");

	    // Создаём массив строковых представлений цветов
	    while (true) {
	        System.out.println("Список цветов:");
	        for (int i = 0; i < DataForMovie.colorNames.length; i++) {
	            System.out.println((i + 1) + ". " + DataForMovie.colorNames[i]);
	        }
	        System.out.print("> ");
	        String inputEye = dataInput.input().trim();
	        // Проверяем, не пустой ли ввод
	        if (inputEye.isEmpty()) {
	            data.put("Operator_Eye", null);
	            break;
	        }
	        // Проверяем ввод как номер
	        if (CheckData.isInteger(inputEye)) {
	            int index = Integer.parseInt(inputEye) - 1;
	            if (index >= 0 && index < DataForMovie.colorNames.length) {
	                data.put("Operator_Eye",DataForMovie.colorNames[index].toUpperCase());
	                break;
	            } else {
	                System.out.println("Ошибка: выбора с таким номером нет в списке.");
	                continue;
	            }
	        }

	        // Проверяем ввод как название через обычный цикл
	        boolean isValid = false;
	        for (String color : DataForMovie.colorNames) {
	            if (color.equalsIgnoreCase(inputEye)) {
	                data.put("Operator_Eye",color.toUpperCase());
	                isValid = true;
	                break;
	            }
	        }

	        if (isValid) {
	            break;
	        } else {
	            System.out.println("Ошибка: введён некорректный цвет.");
	        }
	    }
	}
	
	private void inputOperatorHair() {
		System.out.println("Цвет волос оператора из списка:");
	    while (true) {
	        System.out.println("Список цветов:");
	        for (int i = 0; i < DataForMovie.colorNames.length; i++) {
	            System.out.println((i + 1) + ". " + DataForMovie.colorNames[i]);
	        }
	        System.out.print("> ");
	        String inputEye = dataInput.input().trim();
	        
	        // Проверяем, не пустой ли ввод
	        if (inputEye.isEmpty()) {
	            data.put("Operator_Hair", null);
	            break;
	        }
	        // Проверяем ввод как номер
	        if (CheckData.isInteger(inputEye)) {
	            int index = Integer.parseInt(inputEye) - 1;
	            if (index >= 0 && index < DataForMovie.colorNames.length) {
	                data.put("Operator_Hair",DataForMovie.colorNames[index].toUpperCase());
	                break;
	            } else {
	                System.out.println("Ошибка: выбора с таким номером нет в списке.");
	                continue;
	            }
	        }

	        // Проверяем ввод как название через обычный цикл
	        boolean isValid = false;
	        for (String color : DataForMovie.colorNames) {
	            if (color.equalsIgnoreCase(inputEye)) {
	                data.put("Operator_Hair",color.toUpperCase());
	                isValid = true;
	                break;
	            }
	        }

	        if (isValid) {
	            break;
	        } else {
	            System.out.println("Ошибка: введён некорректный цвет.");
	        }
	    }
	}
	
	private void inputOperatorNationality(){
		System.out.println("Введите национальность оператора из списка:");

	    while (true) {
	        System.out.println("Список национальностей:");
	        for (int i = 0; i < DataForMovie.countryNames.length; i++) {
	            System.out.println((i + 1) + ". " + DataForMovie.countryNames[i]);
	        }

	        System.out.print("> ");
	        String inputNation = dataInput.input().trim();

	        // Проверяем, не пустой ли ввод
	        if (inputNation.isEmpty()) {
	            data.put("Operator_Nation", null);
	            break;
	        }

	        // Проверяем ввод как номер
	        if (CheckData.isInteger(inputNation)) {
	            int index = Integer.parseInt(inputNation) - 1;
	            if (index >= 0 && index < DataForMovie.countryNames.length) {
	            	data.put("Operator_Nation",DataForMovie.countryNames[index].toUpperCase());
	                break;
	            } else {
	                System.out.println("Ошибка: выбора с таким номером нет в списке.");
	                continue;
	            }
	        }

	        // Проверяем ввод как название через обычный цикл
	        boolean isValid = false;
	        for (String country : DataForMovie.countryNames) {
	            if (country.equalsIgnoreCase(inputNation.toUpperCase())) {
	            	data.put("Operator_Nation",country);
	                isValid = true;
	                break;
	            }
	        }

	        if (isValid) {
	            break;
	        } else {
	            System.out.println("Ошибка: введена некорректная национальность.");
	        }
	    }
	}
	
	private void inputLocationX() {
		System.out.println("Введите координату x:");
		while(true) {
			System.out.print("> ");
            String inputX1 = dataInput.input();
            try {
                Integer.parseInt(inputX1);
                data.put("Location_X", Integer.parseInt(inputX1));
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число.");
            }
			
        }
	}
	
	private void inputLocationY() {
		System.out.println("Введите координату y:");
		while(true) {
			System.out.print("> ");
            String inputY1 = dataInput.input();
            try {
                Long.parseLong(inputY1);
                data.put("Location_Y", Long.parseLong(inputY1));
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число.");
            }
			
        }
		
	}
	
	private void inputLocationZ() {
		System.out.println("Введите координату z:");
		while(true) {
			System.out.print("> ");
            String inputZ1 = dataInput.input();
            try {
                Integer.parseInt(inputZ1);
                data.put("Location_Z", Integer.parseInt(inputZ1));
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число.");
            }
			
        }
	}
	
	private void inputLocationName() {
		System.out.println("Введите имя:");
		while(true) {
			System.out.print("> ");
            String inputName1 = dataInput.input();
            if(inputName1 == null|| inputName1.isEmpty()) {
            	System.out.println("Название не может быть пустым.");
            }
            else {
            	data.put("Location_Name",inputName1);
            	break;
            }
			
        }
	}
	
	
	
}
