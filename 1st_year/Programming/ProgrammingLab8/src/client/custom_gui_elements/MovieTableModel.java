package client.custom_gui_elements;

import client.other.TableElement;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MovieTableModel extends AbstractTableModel {
    private ArrayList<TableElement> movies;
    private String[] columnNames = {"id", "name"};

    public MovieTableModel(ArrayList<TableElement> movies) {
        this.movies = movies;
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TableElement tableElement = movies.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tableElement.getId();
            case 1:
                return tableElement.getName();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public TableElement getMovieAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < movies.size()) {
            return movies.get(rowIndex);
        }
        return null;
    }

    public void updateMovies(ArrayList<TableElement> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        fireTableRowsUpdated(0, movies.size() - 1);
    }

    public ArrayList<TableElement> getMovies() {
        return movies;
    }

}
