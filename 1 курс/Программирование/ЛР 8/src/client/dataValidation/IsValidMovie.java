package client.dataValidation;

import server.dataStorage.Movie;

/**
 * Класс, содержащий методы для проверки валидности объекта {@link Movie}.
 * Этот класс проверяет все поля объекта на валидность согласно заданным условиям.
 */
public class IsValidMovie {
	
	/**
     * Проверяет валидность объекта {@link Movie}.
     * Все поля объекта проверяются на null и на соответствие ограничениям (например, значения должны быть больше 0).
     * 
     * @param movie объект фильма, который нужно проверить.
     * @return {@code true}, если фильм валиден, иначе {@code false}.
     */
	public static boolean isValidMovie(Movie movie) {
		if(movie == null) return false;
		
		if(movie.getId() <= 0) 	return false;
		
		if(movie.getName() == null)  return false;
		
		if(movie.getCreationDate() == null) return false;
		
		if(movie.getCoordinates() == null) 	return false;
		
		if(movie.getCoordinates() != null && movie.getCoordinates().getX()<-754)  return false;
		
		if(movie.getOscarsCount()<=0) 	return false;
		
		if(movie.getTotalBoxOffice()<=0) return false;
		
		if(movie.getUsaBoxOffice()<=0 || movie.getUsaBoxOffice() == null) 	return false;
		
		if(movie.getOperator()!=null) {
			try {
				if (movie.getOperator().getName() == null)	return false;
				
				if (movie.getOperator().getHeight() <=0)  return false;
				
			}
			catch(NullPointerException e) {	
				return false;
			}
			
		}
		
		if(movie.getOperator()!=null&&movie.getOperator().getLocation()!=null) {
			try {
				if(movie.getOperator().getLocation().getX()==null) 	return false;

				if(movie.getOperator().getLocation().getY()==null) 	return false;

				if(movie.getOperator().getLocation().getName()==null)	return false;

			}
			catch(NullPointerException e) {
				return false;
			}
		}
		
		return true;
	}		
		
}		
