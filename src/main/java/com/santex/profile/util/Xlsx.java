package com.santex.profile.util;


// Java program to write data in excel sheet using java code

import java.io.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Xlsx {

    public static  List<List<ClaveValor>>  read(String ruta, int sheetNumber, int colOfHeaders, boolean verbose)
    {
        List<List<ClaveValor>> arrRta = new ArrayList<>();
        List<String> arrHeaders = new ArrayList<>();

        try
        {
            List<Row> results = new ArrayList<Row>();
            Workbook workbook = WorkbookFactory.create(new FileInputStream(ruta));
            Sheet sheet = workbook.getSheetAt(sheetNumber);

            int contRow = 0;
            for (Row row : sheet)
            {
                int rowNumber = row.getRowNum()+1;

                List<ClaveValor> arrRow = new ArrayList<>();

                int contadorCellInRow = 0;
                for (Cell cell : row)
                {
                    Object valor = 0;

                    if(cell.getCellType() == CellType.STRING)
                    {
                        valor = cell.getStringCellValue();
                    }
                    else if(cell.getCellType() == CellType.NUMERIC)
                    {
                        valor = (int) cell.getNumericCellValue();
                    }

                    if(rowNumber == 1)
                    {
                        // 1 - SI ES LA ROW UNO AGREGO AL LISTADO DE HEADERS:
                        arrHeaders.add(String.valueOf(valor));
                    }
                    else
                    {
                        // 2 - SI ES OTRA ROW AGREGO AL LIST DE ESTA ROW:
                        String clave = arrHeaders.get(contadorCellInRow);
                        ClaveValor cv = new ClaveValor(clave, valor);
                        arrRow.add(cv);
                    }

                    if (verbose)
                    {
                        log.info(valor + "\t");
                    }

                    contadorCellInRow++;
                }

                if(contRow != 0)
                {
                    arrRta.add(arrRow);
                }

                if (verbose)
                {
                    log.info("\n");
                }

                contRow++;
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return arrRta;
    }
    public static boolean write(String filePath , String sheetName , List<String> arrHeaders , Map<String, List<String>> mapaValores)
    {
        boolean ok = false;


        try
        {
            // 0 - EXAMPLE OF MAP:
            //            Map<String, Object[]> studentData = new TreeMap<String, Object[]>();
            //            studentData.put("1",Arrays.asList("Roll No", "NAME", "Year") });

            // 1 - workbook object
            XSSFWorkbook workbook = new XSSFWorkbook();

            // 2 - spreadsheet object
            XSSFSheet spreadsheet = workbook.createSheet(sheetName);

            // 3 - creating a row object
            XSSFRow row;

            Set<String> keyid = mapaValores.keySet();

            int rowid = 0;
            int cellid = 0;

            // 4 - WRITING HEADERS
            row = spreadsheet.createRow(rowid++);
            for (String headerLoop : arrHeaders)
            {
                if(headerLoop != null)
                {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue(headerLoop.toUpperCase());
                }
            }

            // 5 - writing the data into the sheets...
            for (String key : keyid)
            {

                row = spreadsheet.createRow(rowid++);
                List<String> arrLine = mapaValores.get(key);
                cellid = 0;


                for (String colLoop : arrLine)
                {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue(colLoop);
                }
            }

            // 6 - .xlsx is the format for Excel Sheets... writing the workbook into the file...
            FileOutputStream out = new FileOutputStream(new File(filePath));


            // 7 - AUTOSIZE COLUMNS:
            for (int i = 0; i < arrHeaders.size(); i++)
            {
                spreadsheet.autoSizeColumn(i);
            }

            workbook.write(out);
            out.close();



            ok = true;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            ok = false;
        }

        return ok;
    }
}
