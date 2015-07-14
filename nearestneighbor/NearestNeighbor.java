package com.nearestneighbor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NearestNeighbor {

	public static void main(String[] args) {
		NearestNeighbor nn = new NearestNeighbor();
		HashMap<Integer, ArrayList> map = (HashMap<Integer, ArrayList>) nn.readCrossValidationInput();
		nn.printHashmap(map);
		char[][] dataset = nn.readDataSet();
		System.out.println("-- DataSet --");
		nn.printDataSet(dataset);
		HashMap<Integer,ArrayList> dataTable = nn.buildDataTable(dataset);
		System.out.println("-- Data Table --");
		nn.printHashmap(dataTable);
	}

	public HashMap buildDataTable(char[][] matrix) {
		HashMap<Integer, ArrayList<Object>> dataTable = new HashMap<>();
		int exampleNo = 0;
		int row = matrix.length;
		int column = matrix[0].length;

		for (int i = row - 1; i >= 0; i--) {
			for (int j = 0; j < column; j++) {
				ArrayList<Object> temp = new ArrayList<>();
				switch(matrix[i][j]){
				case '.':
					break;
				case '+':
					System.out.println("Read + : "+i+" , "+j);
					temp.add(i);
					temp.add(j);
					temp.add(matrix[i][j]);
					dataTable.put(exampleNo++, temp);
					break;
				case '-':
					System.out.println("Read - : "+i+" , "+j);
					temp.add(i);
					temp.add(j);
					temp.add(matrix[i][j]);
					dataTable.put(exampleNo++, temp);
					break;
				}
			}
		}
		return dataTable;
	}

	public Map readCrossValidationInput() {
		// Read file info
		BufferedReader fileReader;
		HashMap<Integer, ArrayList<Integer>> inputData = new HashMap<>();
		;
		int permutationCount = 0;
		try {
			fileReader = new BufferedReader(new FileReader("CrossValidationData"));
			String line = "";
			while ((line = fileReader.readLine()) != null) {
				String[] input = line.split(" ");
				if (input.length == 3) {
					int fold = Integer.parseInt(input[0]);
					int noOfExamples = Integer.parseInt(input[1]);
					int noOfPermutations = Integer.parseInt(input[2]);
				} else {
					ArrayList<Integer> temp = new ArrayList<>();
					for (String data : input) {
						temp.add(Integer.parseInt(data));
					}
					inputData.put(permutationCount++, temp);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found!!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputData;
	}

	public char[][] readDataSet() {
		char[][] matrix = null;
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader("Dataset"));
			String line = "";

			int rowCount = 0;
			while ((line = fileReader.readLine()) != null) {
				String[] data = line.split(" ");
				if (data.length == 2) {
					int row = Integer.parseInt(data[0]);
					int column = Integer.parseInt(data[1]);
					matrix = new char[row][column];
				} else {
					String[] datasetValues = line.split(" ");
					for (int i = 0; i < datasetValues.length; i++) {
						matrix[rowCount][i] = datasetValues[i].charAt(0);
					}
					++rowCount;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matrix;

	}

	public void printHashmap(HashMap<Integer, ArrayList> map) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " , " + pair.getValue());
		}
	}

	public void printDataSet(char[][] dataset) {
		for (int i = 0; i < dataset.length; i++) {
			for (int j = 0; j < dataset[0].length; j++) {
				System.out.print(dataset[i][j] + " ");
			}
			System.out.println();
		}
	}
}
